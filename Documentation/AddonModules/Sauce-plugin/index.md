---
layout: page
title: Sauce Plugin for omelet
---

Every one loves screen shot, what if we have videos as well for all the test cases and that too integrated in Omelet report.

Sauce labs provides api to update test parameters for easy differentiation of the test.
With this plugin omelet will support integration of videos directly in omelet report so that we as a QA are never in doubt.


## How to set up

Add the below listner in your testsuite.xml in listener section, **please make sure this listener is added at the top or before "Driver Initialization" listener**

`
<listener class-name="omelet.support.saucelabs.SauceLabsIntegration"></listener>
`

typical test suite listener will look like below.

{%highlight xml%}
	<listeners>
		<listener class-name="omelet.testng.support.TestInterceptor"></listener>
		<listener class-name="omelet.support.saucelabs.SauceLabsIntegration"></listener>
		<listener class-name="omelet.driver.DriverInitialization"></listener>
		<listener class-name="omelet.testng.support.RetryIAnnotationTransformer"></listener>
		<listener class-name="omelet.driver.SuiteConfiguration"></listener>
		<listener class-name="org.uncommons.reportng.HTMLReporter"></listener>
	</listeners>

{%endhighlight%}

Add omelet-sauce plugin dependency in your pom.xml.
{%highlight xml%}
	<dependency>
			<groupId>com.springer</groupId>
			<artifactId>omelet-saucelabs-support</artifactId>
			<version>1.0.3</version>
	</dependency>
{%endhighlight%}
Latest version of plugin can be checked over [here](http://search.maven.org/#search%7Cga%7C1%7Comelet)  
Thats it. 

On running your test cases on Sauce labs, you can find extra link in each test case in the omelet reports for the sauce lab details.
Similar to this after adding the above listener if we check our tests on sauce labs then they are more organized and different data is updated like test name,suite name, build number etc.








Sample Reports