import org.apache.log4j.Logger;

import net.rcarz.jiraclient.*;
public class BaseJiraClient {
	
	private String jiraUrl;
	private String userName;
	private String password;
	private BasicCredentials basicCredentials;
	private JiraClient jiraClient;
	private static final Logger LOGGER = Logger.getLogger(BaseJiraClient.class); 
	
	
	public BaseJiraClient(String url,String userName,String password){
		this.jiraUrl = url;
		this.userName = userName;
		this.password = password;
		basicCredentials = new BasicCredentials(userName, password);
		jiraClient = new JiraClient(url,basicCredentials);
		
	}


	public String createIssue(String summary, String description, String assigne,String project) {
		String jiraUrl = "";
		try {
			jiraUrl = jiraClient.createIssue(project,"Bug").field(Field.SUMMARY, summary)
			.field(Field.DESCRIPTION, description)
			.field(Field.ASSIGNEE, assigne)
			.execute().getUrl();
			LOGGER.info("Jira raised::"+jiraUrl);
		} catch (JiraException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e);
		}
		return jiraUrl;
		
	}

}
