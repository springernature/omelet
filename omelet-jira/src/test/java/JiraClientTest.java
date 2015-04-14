import org.testng.Assert;
import org.testng.annotations.Test;


public class JiraClientTest {
	
	@Test
	public void raiseBug(){
		BaseJiraClient basejiraClient = new BaseJiraClient("JiraUrl","username","password");
		Assert.assertNotEquals(basejiraClient.createIssue("Summary","Description","assigne","testProject"), "");	
	}

}
