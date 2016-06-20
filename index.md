---
layout: post-index
title: Cross Browser Testing with Ease
description: "Describe this nonsense."
tags: [Jekyll, theme, themes, responsive, blog, modern]
comments: false
image:
  feature: abstract-12.jpg
  credit: 
  creditlink:
---


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
[![Build Status](https://travis-ci.org/springernature/omelet.svg?branch=master)](https://travis-ci.org/springernature/omelet)

What is Omelet
--------------
Very simple yet powerful automation library for **Selenium WebDriver** which uses one of the most popular test runner for UI Automation- **TestNG**

Salient Features
----------------
* Multi Parallel testing
* Data driver testing using Xml or GoogleSheets
* Step level report generation 
* Support all major cloud solution providers like (Saucelabs, BrowserStack, TestingBot)
* Auto screen Shot of failed test steps
* Command Line support for CI integration
* ExpectedConditionsExtended for PageObjects Design pattern
* Auto Re-Run failed test cases
* Sauce labs plugin 
 and many more ... 

 Data
----------
* Simple Properties file
* Xml 
* And now Google Sheets as well 

Reports
-----------

Omelet uses reportNG for reporting and there has to be guice dependency for reportNG to work 
You may face issue with dependencies related to ReportNG, do read this [blog](http://solidsoft.wordpress.com/2011/01/23/better-looking-html-test-reports-for-testng-with-reportng-maven-guide/)
### Reports Path 

* If we do mvn test(or clean install ) then reports will be generated in target/surefire-reports/html/index.html

* if we run via TestNg then report can be found in test-output/html/index.html

Discussions
------------
Google Grooups: https://groups.google.com/forum/#!forum/omelet