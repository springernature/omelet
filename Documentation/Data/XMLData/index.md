---
layout: page
title: Google Sheet Usage
---
# How to use Xmls as Data
------------

Xmls are one of the DataSource in which we can store all the Test Data and different BrowserConfiguration and offcource very own Mapping.

## Why Xmls
With xml one can have complex mapping for a testmethod with different data and different browsers(offcourse same is not compromised in GoogleSheets).
Google Sheets test data involves network call and hence are bound to take some time depending on your internet speed atleast in the  intially phase of caching the data , but as Xml as sitting right next to code ,so they are pretty fast 

### Mapping.xml 
As the name suggests **Mapping.xml** file contains all the mapping of test cases with test data and BrowserConfiguration (ClientEnvironment) , for example
if I have to run my test case named "com.springer.test.GoogleTests.VerifySelenium" on **"FireFox, Chrome"** with certain **"TestData.xml"** then we need to map those values in Mapping.xml 
How ?
-----
There is a DTD defined but still for the sake of simplicity 
{%highlight xml%}
<Mapping>
	<Package name="com.springer.test">
		<Class name="com.springer.test.GoogleTest">
			 <MethodName name="verifySeleniumTitle" testData="data/TestData_1.xml" clientEnvironment="browsers/FireFoxLocal.xml/>
		</Class>
	</Package>
</Mapping>
{%endhighlight%}

if at all we feel that all the test cases under particular class uses the same browser and data configuration then Mapping configuration would look something like below and all the test cases under the class "GoogleTest" will take the configuration defined at class level
{%highlight xml%}
<Mapping>
    <Package name="com.springer.test">
	<Class name="com.springer.test.GoogleTest" testData="data/TestData_1.xml" clientEnvironment="browsers/FireFoxLocal.xml/>
	</Package>
</Mapping>
{%endhighlight%}

And similarly if we know all the classes under same package will be having the same configuration then we can omit class as well and simply have package as configuration , and all the test cases under the package will take the same configuration

{%highlight xml%}
<Mapping>
    <Package name="com.springer.test"  testData="data/TestData_1.xml" clientEnvironment="browsers/FireFoxLocal.xml>
	</Package>
</Mapping>
{%endhighlight%}

PS: We can have mix match of configurations as well , example being if we know Test Data file will remain same for all the test cases but BrowserConfiguration changes then we can simple add clientEnvironment at the method/Class level 

## BrowserConfiguration 

As suggested in the configuration documentation over [here]()
Nothing but Client Environments and we can have multiple ClientEnvironment inside the ClientEnvironments or Different ClientEnvironment for different purpose , like one for Mobile other for Desktop something like 

Sample XMl is below 
{%highlight xml%}
<ClientEnvironments>
        <ClientEnvironment
     	dc.platform = "XP"
        browsername="firefox"
        dc.verrsion="25"
    	remoteflag = "true"
    	host = "hub.browserstack.com"
        port = "80"
    	drivertimeOut = "10"
    	username = "testusername"
    	key = "testkey"
    	"/>
</ClientEnvironments>
{%endhighlight%}
All the Framework.properties configuration can be configured inside ClientEnvironment , however if you have some static values like drivertimeout , consider adding them in Framework.properties file 
Any desired capability which is provided by the cloud solution provider or if we want to implement on our own can be provided by appending "dc." in front of the key.



## What is TestData xml

TestData xml basically compliments Pageobject design pattern and <Data> is the tag which actually seprate different data , we can have as many Data objects in <Data> tag and those will be delieverd to your testMethod in IProperty interface and can be accessed using PageName_TagName , we have an extra mandate attributes to Data tag which helps in filtering of your data for different environments 

Sample Test Data is below


Now?
All in all Xml is the first preference to use omelet for me , but feel free to explore google sheets as well as those are having there own advantages
