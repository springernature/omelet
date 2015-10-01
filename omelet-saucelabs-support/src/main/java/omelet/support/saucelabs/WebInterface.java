package omelet.support.saucelabs;

import omelet.data.xml.MappingParserRevisit;
import omelet.driver.DriverManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class WebInterface {
    private static final Logger LOGGER = Logger.getLogger(WebInterface.class);
    String buildNumber = "";

    /**
     * This method updates the executed job on sauce labs using the rest api.
     * @param slRestData
     * @param testName
     * @param testResult
     */
    public void updateSauceLabsJob(SauceLabsRestData slRestData, String testName,
                                   Boolean testResult) {
        StringBuilder restApiCommand = new StringBuilder();
        String url = "https://saucelabs.com/rest/v1/" + slRestData.getUser() + "/jobs/" + slRestData.getJobID();
        HttpURLConnection conn = null;
        OutputStreamWriter out = null;

        LOGGER.debug("jobID: " + slRestData.getJobID());
        LOGGER.debug("testName: " + testName);
        LOGGER.debug("testResult: " + testResult);

        if (StringUtils.isNotBlank(MappingParserRevisit.getBuildNumber())) {
            buildNumber = MappingParserRevisit.getBuildNumber();
            LOGGER.debug("buildNumber: " + buildNumber);
        }

        restApiCommand.append("{");
        try {
            URL obj = new URL(url);
            conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");

            String basicAuth = "Basic "
                    + javax.xml.bind.DatatypeConverter
                    .printBase64Binary(slRestData.getUserPass().getBytes("UTF-8"));
            conn.setRequestProperty("Authorization", basicAuth);

            if (slRestData.getProjectName() != "") {
                String sessionNameData = "\"name\":\"" + slRestData.getProjectName() + "\"";
                restApiCommand.append(sessionNameData);
                restApiCommand.append(",");
            }
            if (testName != "") {
                String testNameData = "\"tags\":[\"" + testName + "\"]";
                restApiCommand.append(testNameData);
                restApiCommand.append(",");
            }
            if (buildNumber != "" && buildNumber != null) {
                String buildNumberData = "\"build\":\"" + buildNumber + "\"";
                restApiCommand.append(buildNumberData);
                restApiCommand.append(",");
            }
            if (testResult != null) {
                String testResultData = "\"passed\":" + testResult;
                restApiCommand.append(testResultData);
            }
            restApiCommand.append("}");

            LOGGER.debug(restApiCommand);
            out = new OutputStreamWriter(
                    conn.getOutputStream());
            out.write(restApiCommand.toString());
            LOGGER.info(restApiCommand);
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            try {
                out.close();
                new InputStreamReader(conn.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method stops the job after the execution.
     *
     * @param slRestData
     */
    public void stopJob(SauceLabsRestData slRestData) {
        String url = "https://saucelabs.com/rest/v1/" + slRestData.getUser() + "/jobs/" + slRestData.getJobID() + "/stop";
        HttpURLConnection conn = null;
        OutputStreamWriter out = null;

        try {
            URL obj = new URL(url);
            conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");

            String basicAuth = "Basic "
                    + javax.xml.bind.DatatypeConverter
                    .printBase64Binary(slRestData.getUserPass().getBytes("UTF-8"));
            conn.setRequestProperty("Authorization", basicAuth);

            out = new OutputStreamWriter(
                    conn.getOutputStream());
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            try {
                out.close();
                new InputStreamReader(conn.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String generateLinkForEmbedScript(String jobID, Boolean getEmbedJob) {
        StringBuilder src = new StringBuilder();
        String user = DriverManager.getBrowserConf().getuserName();
        String password = DriverManager.getBrowserConf().getKey();
        String userpass = user + ":" + password;

        src.append("https://saucelabs.com/");
        if (getEmbedJob) {
            src.append("job-embed/");
        } else {
            src.append("video-embed/");
        }
        src.append(jobID);
        src.append(".js?auth=");
        src.append(genMD5hash(userpass, jobID));
        src.toString();

        StringBuilder script = new StringBuilder();
        script.append("<script type=\"text/javascript\">function addScript"
                + jobID + "() {var s = document.createElement( 'script' );");
        script.append("s.setAttribute( 'src','" + src + "');");
        script.append("var div = document.getElementById('" + jobID + "');");
        script.append("div.appendChild( s );}</script>");

        StringBuilder inputToExecScript = new StringBuilder();
        inputToExecScript.append(script);
        inputToExecScript.append("</br>");
        inputToExecScript.append("<a onclick=\"addScript" + jobID + "()\">");
        inputToExecScript
                .append("Click here to display SauceLabs Report ::</a>");
        inputToExecScript.append("<div id=\"" + jobID + "\"></div>");
        return inputToExecScript.toString();
    }

    public String generateLinkForJob(String jobID) {
        StringBuilder sb = new StringBuilder();
        String user = DriverManager.getBrowserConf().getuserName();
        String password = DriverManager.getBrowserConf().getKey();
        String userpass = user + ":" + password;

        sb.append("<a href=");
        sb.append("\"https://saucelabs.com/");
        sb.append("jobs/");
        sb.append(jobID);
        sb.append("?auth=");
        sb.append(genMD5hash(userpass, jobID));
        sb.append("\">");
        sb.append("Link to SauceLabs Job Detail Page");
        sb.append("</a>");
        return sb.toString();
    }

    private String genMD5hash(String keyString, String message) {
        String sEncodedString = null;
        try {
            SecretKeySpec key = new SecretKeySpec(
                    (keyString).getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(key);

            byte[] bytes = mac.doFinal(message.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();

            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            sEncodedString = hash.toString();
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
        } catch (InvalidKeyException e) {
            LOGGER.error(e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e);
        }
        return sEncodedString;
    }
}
