---
layout: page
title: Configurations
---

**_Omelet configuration are very intutive_**

For a Test to work we need to tell omelet which **BrowserConfiguration** , what **TestData** for a **_Test Method_**.

That is all in all we need minimum 3 different files.

* Mapping
* TestData
* BrowserConfiguration

which can be either different Xmls or Different Google Worksheets.



 
# What is Mapping
-------------

Mapping irrespective of data source will hold fileName/SheetName for the BrowserConfiguration and Test Data for a particular test.

With Mapping one can be as specific as they want and as generic as they want with simple hierarchy of Package-->Class-->Method.

Omelet at the very first looks for **"TestMethod"** for TestData, BrowserConfiguration, Run strategy , if any of the key value not provided then it looks for the **"Class"** and then in turn looks for the **"Package"** before throwing any exception.

**_lets take an small example_**

If we know all the TestMethods in a single class[A] will be using same test data then we can simply add TestData for the Class[A] in Mapping and all the methods inside this will use the same TestData and however at the same time if we want every test method to use different browserConfiguration , we can do that as well. Sweet:)
As depending on the DataSource Mapping is of BelowType.

##_Mapping_
* [Xml Mapping]()
* [Google Sheet Mapping]()

# What is BrowserConfiguration
-----------------------

BrowserConfiguration are key value pairs to configure Browser for our Tests. There are multiple ways to configure Browsers and there are many configuration which can be done (some mandatory some not) depending on your requirement
Omelet hierarchy or ways where we can add/OverWrite browser Configuration is CommandLine-->Xml/Sheet-->FrameworkProperties-->DefaultConfigs
All the keys for configuration can be checked in Framework.properties. 
##_BrowserConfiguration_
* [Xml BrowserConfiguration]()
* [GoogleSheet BrowserConfiguration]({{%site.url}}/Documentation/Data)


There is a FallBack Mechanism for Browser Configuration called as Framework.properties. 

Want to know why we should use Framework Properties then check [here]({{%site.url%}}/Documentation/Configuration/FrameworkProperties)

# What is TestData
--------------

TestData are again key value pairs which can be configured for different or same environment multiple times, depending on the datasource selected , and we can filter TestEnvironmnet from command line by providing value to the Jvm argumnets "-Denv-type=".


##_TestData_
* [Xml TestData]()
* [GoogleSheet TestData]({{%site.url}}/Documentation/Data)

