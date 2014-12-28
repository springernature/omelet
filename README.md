
	**********************************************
	**********************************************
	                      .__          __
	  ____   _____   ____ |  |   _____/  |_
	/  _  \ /     \_/ __ \|  | _/ __ \   __\
	( <_>  )  Y Y  \  ___/|  |_\  ___/|  |
	\____ /|__|_|  /\___  >____/\___  >__|
	        \/     \/          \/
	**********************************************
	           LET'S COOK SOME TESTS!
	**********************************************
 

omelet
======
[![Build Status](https://travis-ci.org/springer-opensource/omelet.svg?branch=master)](https://travis-ci.org/springer-opensource/omelet)

What is Omelet
--------------
Very simple yet powerful automation library for **Selenium WebDriver** which uses one of the most popular test runner for UI Automation- **TestNG**


Salient Features
----------------
* Multi Parallel testing
* Data driver testing
* Step level report generation 
* BrowserStack Integration/Auto tunnel
* Auto screen Shot of failed test steps
* Command Line support for CI integration
* ExpectedConditionsExtended for PageObjects Design pattern
* Auto Re-Run failed test cases
 and many more ... 

Build
------
1. mvn clean install
2. Jar will be created in target folder with name :**omelet-1.0.xxx.jar**
    


Quick Start
----------
* Simple and StraightForward use omelet archetype run below command in console/Terminal(dont forgot to change groupId and artifactId in the command:))
Simply use below archType

	**mvn archetype:generate -DgroupId=com.yourGroupId -DartifactId=my-app 			  -DarchetypeArtifactId=omelet-archetype-simple -DarchetypeGroupId=com.springer**
	
Cd into the created folder , and then run **mvn clean install**
	
	

* Or if you want to start manaully then follow below procedure 

 

####For mavenised project
copy omelet dependency and add to your pom.xml

        <dependency>
	      <groupId>com.springer</groupId>
          <artifactId>omelet</artifactId>
          <version>1.0.xxx</version>
        </dependency>
 

Latest version can be checked on maven central over [here](http://maven-repository.com/search?q=omelet)

And reportNG dependency

        <dependency>
			<groupId>com.google.inject</groupId>
			<artifactId>guice</artifactId>
			<version>3.0</version>
		</dependency>
		<dependency>
			<groupId>org.uncommons</groupId>
			<artifactId>reportng</artifactId>
			<version>1.1.2</version>
			<type>jar</type>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.testng</groupId>
			<artifactId>testng</artifactId>
			<version>5.7</version>
			<type>jar</type>
			<classifier>jdk15</classifier>
			<scope>compile</scope>
		</dependency>
Copy below listeners and add to you  testsuite xml of TestNG. More info over [here](http://testng.org/doc/documentation-main.html#listeners-testng-xml)



	<listeners>
		<listener class-name="com.springer.omelet.testng.support.TestInterceptor"></listener>
		<listener class-name="com.springer.omelet.driver.DriverInitialization"></listener>
		<listener class-name="com.springer.omelet.testng.support.RetryIAnnotationTransformer"></listener>
		<listener class-name="com.springer.omelet.driver.SuiteConfiguration"></listener>
		<listener class-name="org.uncommons.reportng.HTMLReporter"></listener>
	</listeners>

And you are ready to go 

if above is too much for you then  
***Best way to start with Omelet is to clone sample project which can be used as template from*** [here](https://github.com/springer-opensource/omelet-example-dataset).

Reports
-----------

Omelet uses reportNG for reporting and there has to be guice dependency for reportNG to work 
You may face issue with dependencies related to ReportNG, do read this [blog](http://solidsoft.wordpress.com/2011/01/23/better-looking-html-test-reports-for-testng-with-reportng-maven-guide/)
### Reports Path 

* If we do mvn test(or clean install ) then reports will be generated in target/surefire-reports/html/index.html

* if we run via TestNg then report can be found in test-output/html/index.html

Data
----------
* Simple Properties file
* Xml 
* And now Google Sheets as well 

Discussions
------------
Google Grooups: https://groups.google.com/forum/#!forum/omelet
Example Projects
--------------
 [omelet-example-dataset](https://github.com/springer-opensource/omelet-example-dataset)
Todo
-------
* Though everything works fine :) but more unit test cases
* Off-course more documentation 
* Improve java docs
* Naming convention 
* And some nitty-gritty stuff
