package omelet.jiramain;

import java.io.File;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.Issue.SearchResult;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

import org.apache.log4j.Logger;

public class BaseJiraClient {

	private String jiraUrl;
	private String userName;
	private String password;
	private String project;
	private BasicCredentials basicCredentials;
	private JiraClient jiraClient;
	private static final String jiraStatusToIgnore = System
			.getProperty(JiraConstant.jiraStatusToIgnore);
	private static final Logger LOGGER = Logger.getLogger(BaseJiraClient.class);
	private static BaseJiraClient instance = null;

	public static BaseJiraClient getInstance() {
		if (null == instance) {
			instance = new BaseJiraClient(
					System.getProperty(JiraConstant.jiraUrl),
					System.getProperty(JiraConstant.userName),
					System.getProperty(JiraConstant.password));
		}
		return instance;
	}

	private BaseJiraClient(String url, String userName, String password) {
		this.jiraUrl = url;
		this.userName = userName;
		this.password = password;
		basicCredentials = new BasicCredentials(userName, password);
		jiraClient = new JiraClient(url, basicCredentials);

	}

	public String createIssue(Defect defect) {

		String jiraId = "";
		try {
			Issue issue = null;
			setIssueIfAlreadyPresent(defect.getSummary(), defect);
			if (defect.isNewIssue()) {
				issue = jiraClient.createIssue(defect.getProject(), "Bug")
						.field(Field.SUMMARY, defect.getSummary())
						.field(Field.DESCRIPTION, defect.getDescription())
						.execute();
				defect.setIssue(issue);
				jiraId = defect.getIssue().getKey();
				defect.setJiraId(jiraId);
				defect.setNewIssue(true);
			} else {
				defect.setNewIssue(false);
			}
		} catch (JiraException e) {
			LOGGER.error(e);
		}
		return jiraId;

	}

	public void updateIssue(Issue issue, String comment) {
		try {
			issue.update().fieldAdd(Field.COMMENT, comment).execute();
		} catch (JiraException e) {
			LOGGER.error(e);
		}
	}
	
	/*public void updateLabels(Issue issue){
		issue.update().field(Field.LABELS, value)
	}*/

	public void addScreenShot(Defect defect) {
		if (defect.isNewIssue()) {
			for (String screenShot : defect.getScreenShotLink())
				try {
					File f = new File(screenShot);
					defect.getIssue().addAttachment(f);
				} catch (JiraException e) {
					LOGGER.error(e);
				}
		}
	}

	public void setIssueIfAlreadyPresent(String summary, Defect defect)
			throws JiraException {
		if (null != jiraStatusToIgnore || jiraStatusToIgnore.isEmpty()) {
			SearchResult searchresult = jiraClient.searchIssues(JqlForIgnore
					.getJqlForIgnore(defect));
			if (searchresult.total > 0) {
				LOGGER.info("Defect already present:"+searchresult.issues.get(0).getKey());
				System.out.println(searchresult.issues.get(0).getStatus());
				defect.setIssue(searchresult.issues.get(0));
				defect.setJiraId(searchresult.issues.get(0).getKey());
				defect.setNewIssue(false);
			} else {
				LOGGER.debug("There is no issue present for the summary:"+defect.getSummary()+" and status:"+jiraStatusToIgnore+
						", hence new defect would be raised");
				defect.setNewIssue(true);
			}
		} else {
			LOGGER.info("As there is no " + JiraConstant.jiraStatusToIgnore
					+ " set, hence no defect will be raised");
			defect.setNewIssue(false);
		}
	}

	private static class JqlForIgnore {

		public static String getJqlForIgnore(Defect defect) {
			String jqp = "project=" + defect.getProject() + " AND "
					+ "summary~'" + defect.getSummary() + "'"
					+" AND "+ getStatusJql(jiraStatusToIgnore);
			LOGGER.info(jqp);
			return jqp;
		}

		/*
		 * private static String getStatus(){ if(null
		 * !=System.getProperty(JiraConstant.jiraStatusToIgnore)){ return
		 * System.getProperty(JiraConstant.jiraStatusToIgnore); }else{ throw new
		 * FrameworkException
		 * ("Please set status to ignore for Jira with Key as:"
		 * +JiraConstant.jiraStatusToIgnore); } }
		 */

		private static String getStatusJql(String status) {
			StringBuilder statusQl = new StringBuilder();
			statusQl.append("status in (");
			String prefix="";
			if (status.contains(JiraConstant.seprator)) {
				for (String stat : status.split(JiraConstant.seprator)) {
					statusQl.append(prefix);
					prefix=",";
					statusQl.append("'"+stat+"'");
				}
			} else {
				statusQl.append("'"+status+"'");
			}
			statusQl.append(")");
			return statusQl.toString();
		}

	}

}
