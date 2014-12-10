---
layout: page
title: Framework.properties
---

Framework.properties is the fallback properties file for all the BrowserConfiguration, All these values can be overrider from command line by providing as Jvm arguments with syntax something like below.

{%highlight text%}
mvn clean install -DbrowserName=firefox -DremoteFlag=True =Dbs_Switch=false
{%endhighlight%}

**_Please note it is always good to have but purely optional_**
### Why we need 

Consider a scenario where most of the configuration are repeated like **screenShotFlag**, **driverTimeOut** and may be we want to run all our test cases in **BrowserStack** and if all the keys once configured here need not to be present in ClientEnvironment(BrowserXml) xml or Google sheet Browser Configuration at all 

There are always some default values associated with the mandatory key, By default omelet is configured to run on firefox on local system with screenshot true and driverTimeout of 30 seconds
***
**browserName** - (FireFox|Chrome|IE|HTML)  
**remoteFlag** - Whether we want to run on Remote - remote can be HUB or BrowserStack   
**remoteURL** - HUB url if at all bsSwitch is false(that is we do not want to run on BrowserStack)  
**bsSwitch** - Whether we want to run on browserStack   
**bs_userName** - Username for browser Stack  
**browserVersion** - which is required for running on Browserstack(like 25)  
**bs_key** - key for the browser stack which we can get after logging into it  
**os** - name of the os which is required for running test case on BrowserStack(like Windows).  
**osVersion** - version of Os which is required for running test case on BrowserStack(like XP).  
**driverTimeOut** - Integer value which is implicit wait for our driver  
**bs_localTesting** - Boolean - BrowserStack local testing flag for testing localhost or internal application.  
**bs_urls**- url which will configured for setting tunnel for local testing on browser stack(multiple can be added by ";" sepration.  
**mobileTest** - if we want to run test cases on Mobile browser on BrowserStack instead on Desktop  
**device** = required to configure test cases to run on BrowserStack mobile(device = ipad 3rd)  
**platform** - required to configure test cases to run on BrowserStack mobile(platform = android)  
**chromeServerPath** - if remoteFlag = false and browserName=Chrome , then location of Chrome Server  
**ieServerPath** -if remoteFlag = false and browserName=IE , then location of ieServer  
**screenShotFlag** - Boolean value to take screenshot of the failure test cases  
**highlightElementFlag** - utility to higlight clicking of element before clicking and entering text in webelement  

***