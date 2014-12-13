---
layout: page
title: Google Sheet Usage
---
# How to use Xmls as Data
------------

Xmls are one of the DataSource in which we can store all the test data and Different BrowserConfiguration.

##Why Xmls
With xml one can have complex mapping for a testmethod with different data and different browsers.
Google Sheets test data involves network call and hence are bound to take some time intially while catching the data , but as Xml as sitting right next to code ,so they are pretty fast 

Mapping.xml

Mapping.xml is the mapping sheet which maps your test method with testData and BrowserConfiguration. A single test case can be run with n number of different data and n number of Browser configuration.

##What if I have 100 test methods ? Do i need to provide configuration for all 100 methods ?
No dont be scared , mapping xml follows a clear hierarchy of package --> class -->method , which mean if I know all the test classes and inturn testMethod of package will be using BrowserConfiguration:x.xml and TestData:data.xml then i just need to provide these configuration at the package level , test class and method will inherit from the package 

However if you want to have more control over classes and method then feel free to add configuration at class/Method level 

Omelet basically follows method -->Class -->package hierarchy and looks till package level before throwing any excpetion 

##What is BrowserConfiguration 

Nothing but Client Environments and we can have multiple ClientEnvironment inside the ClientEnvironments or Different ClientEnvironment for different purpose , like one for Mobile other for Desktop something like 

Sample XMl is below 

All the Framework.properties configuration can be configured inside ClientEnvironment , however if you have some static values like driverTimeout , consider adding them in Framework.properties file 

Omelet Follows Hierarchy for clientEnvironment as CommandLine-->ClientXmls-->FrameworkProperties-->DefaultConfigs

##What is TestData xml

TestData xml basically compliments Pageobject design pattern and <Data> is the tag which actually seprate different data , we can have as many Data objects in <Data> tag and those will be delieverd to your testMethod in IProperty interface and can be accessed using PageName_TagName , we have an extra mandate attributes to Data tag which helps in filtering of your data for different environments 

Sample Test Data is below


Now?
All in all Xml is the first preference to use omelet for me , but feel free to explore google sheets as well as those are having there own advantages
