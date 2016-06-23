---
layout: page
title: Sauce Plugin for omelet
---

Every one loves screen shot, what if we have videos as well for all the test cases and that too integrated in Omelet report.

Sauce labs provides api to update test parameters for easy differentiation of the test.
With this plugin omelet will support integration of videos directly in omelet report so that we as a QA are never in doubt.

We have created two different plugins for SauceLabs:

* SauceLabsIntegration
* SauceConnect

## Activate the SauceLabs plugin

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

## SauceLabsIntegration

### How to set up

Add the below listner in your testsuite.xml in listener section, **please make sure this listener is added at the top or before "Driver Initialization" listener**

{%highlight xml%}
<listener class-name="omelet.support.saucelabs.SauceLabsIntegration"></listener>
{%endhighlight%}

Typical test suite listener will look like below.

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

On running your test cases on Sauce labs, you can find extra link in each test case in the omelet reports for the sauce lab details.
Similar to this after adding the above listener if we check our tests on sauce labs then they are more organized and different data is updated like test name,suite name, build number etc.








Sample Reports


## SauceConnect

### The Problem: How to execute a test to a site which is not public?

SauceLabs provides a tool which allows you to test non public sites in the SauceLabs client machines.

For more information have a look at the SauceLabs Wiki.
[Sauce Connect](https://wiki.saucelabs.com/display/DOCS/Sauce+Connect+Proxy)


### How to set up

#### testsuite preparations

Add the below listner in your testsuite.xml in listener section, **please make sure this listener is added at the top or before "Driver Initialization" listener**

{%highlight xml%}
<listener class-name="omelet.support.saucelabs.SauceConnect"></listener>
{%endhighlight%}

Typical test suite listener will look like below.

{%highlight xml%}
<listeners>
	<listener class-name="omelet.testng.support.TestInterceptor"></listener>
	<listener class-name="omelet.testng.support.RetryIAnnotationTransformer"></listener>
	<listener class-name="omelet.driver.DriverInitialization"></listener>
	<listener class-name="omelet.driver.SuiteConfiguration"></listener>
	<listener class-name="org.uncommons.reportng.HTMLReporter"></listener>
	<listener class-name="omelet.support.saucelabs.SauceConnect"></listener>
	<listener class-name="omelet.support.saucelabs.SauceLabsIntegration"></listener>
</listeners>
{%endhighlight%}

#### Sauce Connect binary

Please download and save the bin for your os from 
[Sauce Connect](https://wiki.saucelabs.com/display/DOCS/Sauce+Connect+Proxy) site and copy the bin into the following 
folder in your test project.

OSX
{%highlight xml%}
src / main / resources / osx / sc
{%endhighlight%}
Windows
{%highlight xml%}
src / main / resources / win / sc.exe
{%endhighlight%}
Linux
{%highlight xml%}
src / main / resources / linux / sc
{%endhighlight%}

#### Framework.properties

You need to configure the basic settings in the framework.properties.

{%highlight xml%}
key = <SauceLabsLoginKey>
username = <SauceLabsUserName>
host = ondemand.saucelabs.com
remoteflag = true
{%endhighlight%}