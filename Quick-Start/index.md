---
layout: page
title: Quick Start
---
# 5 min Tutorial 
------------

Omelet framework involves some static files to be present before actually start using it.
In order to start with omelet , one of the pre-requisite is maven should be installed(which can be checked by running mvn -version)

Once we are sure maven is insall simply run the below command in you shell

{% highlight text %}
mvn archetype:generate -DgroupId=com.yourGroupId -DartifactId=my-app -DarchetypeArtifactId=omelet-archetype-simple -DarchetypeGroupId=com.springer
{% endhighlight %}

**Do remember to change -DartifactId and -DgroupId which would be your project name and group name respectively.**

## What will happen

Sample project is created for you which will be having a test case for simple selenium tests using XML as data provider. 
You can simple CD into your folder and then run command 
{%highlight text %}
mvn clean install
{%endhighlight%} 

## Then What ?

Few windows will open for firefox and you have run your very first test case with omelet. Do remember to check the reports in sure-fire/html/index.html and click on the tests in order to check detailed step level report

## Now ?

Now as you have your skeletone ready with few folders , either you can start automating your application or try the same test with different option like running test case on BrowserStack which can be done by 

**BrowserStack**
{%highlight text %}
mvn clean install -DremoteFlag=true -Dbs_Switch=true -Dbs_key=yourBrowserStackKey -Dbs_UserName=yourBSName
{%endhighlight%} 

or you can run on your test cases on grid using below command

**Grid**

{%highlight text %}
mvn clean install -DremoteFlag=true -DremoteUrl=https://yourHubURL
{%endhighlight%} 