package omelet.jiramain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.rcarz.jiraclient.Issue;

import org.testng.asserts.IAssert;

import omelet.driver.Driver;
import omelet.testng.support.SAssert;

public class Defect {

	private String project;
	private String summary;
	private String description;
	private String assigne;
	private List<String> labels;
	private boolean raiseBug;
	private List<String> screenshotLinks = new ArrayList<String>();
	private String jiraId;
	private Issue issue;
	private boolean newIssue;
	private String outPutDirectory;

	public Defect(String outPutDirectory) {
		this.outPutDirectory = outPutDirectory;
		setDescription();
		setScreenShotLink();
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	private void setDescription() {
		StringBuilder sb = new StringBuilder();
		int stepNo = 1;
		Map<IAssert, String> assertionMap = SAssert.assertMap.get();
		sb.append("h3. Steps Performed" + JiraConstant.lineSeprator);
		for (IAssert assertionKey : assertionMap.keySet()) {
			if (assertionKey.getActual().equals(assertionKey.getExpected())) {
				System.out.println(assertionKey.getActual());
				System.out.println(assertionKey.getExpected());
				sb.append(stepNo + "." + assertionKey.getMessage() + " (/)");
			} else {
				sb.append(stepNo + "." + assertionKey.getMessage() + " (x)"
						+ " {quote} *Expected Result*:"
						+ assertionKey.getExpected()
						+ JiraConstant.lineSeprator + " *Actual Result*:"
						+ assertionKey.getActual() + "{quote}");
			}
			stepNo++;
			sb.append(JiraConstant.lineSeprator);
		}
		sb.append(JiraConstant.lineSeprator);
		sb.append(JiraConstant.lineSeprator);
		sb.append("h3. Environment");
		sb.append(" {quote}*Browser*:::" + Driver.getBrowserConf().getBrowser()
				+ JiraConstant.lineSeprator);
		sb.append("*Version*::" + Driver.getBrowserConf().getCapabilities()
				+ "{quote}");
		this.description = sb.toString();
	}

	public String getAssigne() {
		return assigne;
	}

	public void setAssigne(String assigne) {
		this.assigne = assigne;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public boolean isRaiseBug() {
		if (null != summary && !summary.isEmpty()) {
			raiseBug = true;
		}
		return raiseBug;
	}

	private void setScreenShotLink() {
		Map<IAssert, String> assertionMap = SAssert.assertMap.get();
		String screenSL;
		if (Driver.getBrowserConf().isScreenShotFlag()) {
			for (IAssert assertionKey : assertionMap.keySet()) {
				screenSL = assertionMap.get(assertionKey);
				if (null != screenSL && !screenSL.isEmpty()) {
					screenSL = screenSL.substring(screenSL.lastIndexOf("/"));
					screenshotLinks.add((outPutDirectory + screenSL).replace(
							"/", File.separator));
				}
			}
		}
	}

	public List<String> getScreenShotLink() {

		return screenshotLinks;
	}

	public String getJiraId() {
		return jiraId;
	}

	public void setJiraId(String jiraId) {
		this.jiraId = jiraId;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public boolean isNewIssue() {
		return newIssue;
	}

	public void setNewIssue(boolean newIssue) {
		this.newIssue = newIssue;
	}

	public String getOutPutDirectory() {
		return outPutDirectory;
	}

}
