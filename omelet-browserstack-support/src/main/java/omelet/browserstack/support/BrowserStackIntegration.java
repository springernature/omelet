package omelet.browserstack.support;

import java.util.List;

import omelet.driver.DriverManager;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class BrowserStackIntegration implements ISuiteListener {

	@Override
	public void onStart(ISuite suite) {
		setUpTunnel(DriverManager.getBrowserConf().getKey());
	}

	@Override
	public void onFinish(ISuite suite) {
		// Clean up the browser Stack created tunnels
		BrowserStackTunnel bs = BrowserStackTunnel.getInstance();
		List<String> bsKey = bs.getOpenTunnelKeys();
		for (String s : bsKey) {
			bs.terminateTunnel(s);
		}		
	}
	
	private void setUpTunnel(String AUTOMATE_KEY) {
		BrowserStackTunnel bs;
		bs = BrowserStackTunnel.getInstance();
		bs.createTunnel(AUTOMATE_KEY, null);
	}
}
