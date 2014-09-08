omelet
======

What is Omelet
--------------
Very simple yet powerful automation library for **Selenium-WebDriver** which uses one of the most popular test runner for UI Automation- **TestNG**

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
-------
1. mvn clean install
2. Jar will be created in target folder with name :**omelet-1.0.0.jar**

Quick Start
----------
* install maven 
* clone omelet using git clone 
* go to root directory and do mvn clean install
 

####For mavenised project
copy below dependency and add to your pom.xml

        <dependency>
	      <groupId>com.springer</groupId>
          <artifactId>omelet</artifactId>
          <version>1.0.0</version>
        </dependency>
 



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

                       <dependency>
                          <groupId>com.google.inject</groupId>
                          <artifactId>guice</artifactId>
                          <version>3.0</version>
                      </dependency>


### Reports Path 

* If we do mvn test(or clean install ) then reports will be generated in target/surefire-reports/html/index.html

* if we run via TestNg then report can be found in test-output/html/index.html

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
