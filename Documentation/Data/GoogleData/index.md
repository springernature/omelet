---
layout: page
title: Google Sheet Usage
---
# How to use Google Sheets as Data
------------

##Why Google Sheets over Excel
Excel sheets are brilliant way to maintain your data but from my experience,r every single data change we have to open excel make some changes and then again push it to git or any scm, thats anyways no much of work


###**what if?**
* someone ask us to change a data quickly and check if the test case is working fine.
* We have to change the browser or want to run same test case for different browsers.
* Some Business oriented person like BA wants to look at data and change them , are you gonna do copy paste of the chages and push? 

All these **"whatifs"** encouraged us to add Google sheet as Data 

Now if we have googleSheets with us then we can easily configure data at run time, even BA need not to depend on QA to make some change + you have access to your data from anywhere from any devicde and google sheets gives the same functionality and clarity which excel provides.

##How to Use GoogleSheets with Omelet
----- 

Easy- create Google account and add sheet :)

Either you can follow sample GoogleSheet over [here](https://docs.google.com/spreadsheets/d/14sD0Z6OR0pMogx32KrBXCwpxRVvMPfG2Othf0HHnd5w/edit#gid=2060834450) and run below command which is self explantory 

{%highlight text%}
mvn clean install -DgoogleUserName=yourGmailID -DgooglePassword=YourGmailPasswd -DgoogleSheetName=yourGoogleSHeetName
{%endhighlight%}

 or continue reading.

##Mapping Sheet
Most important data for omelet is Mapping which actually tells omelet which all browsers , which all data and which all environment to run 
Create a sheet with name Mapping and add following coloum to sheet

* MethodName - Name of the method/Package/Class
* TestData - Name of the Sheet which will be holding the test data
* BrowserSheet - Name of the Browser sheet which will be holding the Client Environment/Browser Configuration
* Run Strategy - Run strategy which is Full/Optimal 

## BrowserSheet

Create a one or multiple browser Sheet and name it as per your convienence. Add any or all the browserConfiguration keys to the coloum and your are done .

Please not single browser Sheet can have mutliple rows of browsers which will tell omelet to run mapped test case on multiple browser 
After you have given the name , do remember to add the browser sheet name in the  Mapping sheet coloum : BrowserSheet

More over different Browser sheet can also be provided to a test method in the Mapping with ";" seprated like BrowserSheet1;BrowserSheet2

## Test Data 

Test Data is designed to support your complete testing on different environments and complementing page object design pattern 

TestData sheet should have minimum two coloum with name as Key|Value and data can be seprated by key as"Environment"



| Key      |      value    |
|----------|:-------------:|
|**Enrionment**|  **live** |
| testKey1 | value1|
| testKey2 | value2|
|**Enrionment**|  **Stage** |
| testKey1 | valueStage1|
| testKey2 | valueStage2|
| testKey3 | valueStage3|
| testKey4 | valueStage4|
|**Enrionment**|  **live** |
| testKey1 | value1|
| testKey2 | value2|
| testKey3 | value3|
| testKey4 | value4| 

## What next 

Now all the data has been configured what we need to do is to run our command and see the magic , we can filter the test data by simply addind -Denv-type , if this argument is not present then test cases will run on all the environment 

{%highlight text%}
mvn clean install -DgoogleUserName=yourGmailID -DgooglePassword=YourGmailPasswd -DgoogleSheetName=yourGoogleSHeetName -Denv-type=Stage
{%endhighlight%}