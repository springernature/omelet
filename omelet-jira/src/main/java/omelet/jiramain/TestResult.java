package omelet.jiramain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import omelet.driver.Driver;
import omelet.testng.support.SAssert;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.IAssert;

public class TestResult implements IInvokedMethodListener {

	private static final boolean raiseJira = Boolean.valueOf(System
			.getProperty(JiraConstant.jiraFlag));

	// private static BaseJiraClient jiraClient;

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		Reporter.setCurrentTestResult(testResult);
		System.out.println(testResult.getTestContext().getOutputDirectory());
		String jiraID = "";
		Defect defect = null;
		// Check if test is passed
		if (method.isTestMethod()) {
			if (!testResult.isSuccess()) {
				if (raiseJira) {
					defect = new Defect(testResult.getTestContext().getOutputDirectory());
					defect.setProject(System.getProperty(JiraConstant.project));
					defect.setAssigne(System.getProperty(JiraConstant.assigne));
					defect.setSummary(getSummary(method));
					jiraID = BaseJiraClient.getInstance().createIssue(defect);
					BaseJiraClient.getInstance().addScreenShot(defect);
					Reporter.log("JIRA ID:::" + defect.getJiraId());
				}
			}
		}

	}

	public String getSummary(IInvokedMethod method) {
		return method.getTestMethod().getDescription();
	}

	public String getDescription() {
		StringBuilder sb = new StringBuilder();
		int stepNo = 1;
		Map<IAssert, String> assertionMap = SAssert.assertMap.get();
		for (IAssert assertionKey : assertionMap.keySet()) {
			if (assertionKey.getActual().equals(assertionKey.getExpected())) {
				System.out.println(assertionKey.getActual());
				System.out.println(assertionKey.getExpected());
				sb.append(stepNo + "." + assertionKey.getMessage());
			} else {
				sb.append(stepNo + "." + assertionKey.getMessage()
						+ "=>*Expected Result*:" + assertionKey.getExpected()
						+ " *Actual Result*:" + assertionKey.getActual());
				//if screenshot flag is on

			}
			stepNo++;
			sb.append(JiraConstant.lineSeprator);
			sb.append(JiraConstant.lineSeprator);
			sb.append(JiraConstant.lineSeprator);
			sb.append("*Browser*:::" + Driver.getBrowserConf().getBrowser()
					+ JiraConstant.lineSeprator);
			sb.append("*Version*::" + Driver.getBrowserConf().getCapabilities());
		}
		return sb.toString();
	}

}
