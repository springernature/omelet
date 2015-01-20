---
layout: page
title: Framework.properties
---

Framework.properties is the fallback properties file for all the BrowserConfiguration, All these values can be overriden from command line by providing as Jvm arguments with syntax something like below.

{%highlight text%}
mvn clean install -DbrowserName=firefox -DremoteFlag=True =Dbs_Switch=false
{%endhighlight%}

**_Please note it is always good to have but purely optional_**
### Why we need 

Consider a scenario where most of the configuration are repeated like **screenShotFlag**, **driverTimeOut** and may be we want to run all our test cases in **BrowserStack** and if all the keys once configured here need not to be present in ClientEnvironment(BrowserXml) xml or Google sheet Browser Configuration at all 

There are always some default values associated with the mandatory key, By default omelet is configured to run on firefox on local system with screenshot true and driverTimeout of 30 seconds
***
**browsername** - (FireFox|Chrome|IE|HTML)  
**remoteflag** - Whether we want to run on Remote - remote can be HUB or BrowserStack   
**host** - HUB host like (127.0.0.1) or cloud host like "hub.browserstack.com"   
**username** - Username for Cloud   
**key** - key for cloud
**drivertimeOut** - Integer value which is implicit wait for our driver    
**mobileTest** - if we want to run test cases on Mobile browser on BrowserStack instead on Desktop  
**chromeserverpath** - if remoteflag = false and browsername=Chrome , then location of Chrome Server  
**ieserverpath** -if remoteflag = false and browsername=IE , then location of ieServer  
**screenshotflag** - Boolean value to take screenshot of the failure test cases  
**highlightelementflag** - utility to higlight clicking of element before clicking and entering text in webelement  

***