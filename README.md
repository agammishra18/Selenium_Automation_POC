## Selenium Base Framework:
This framework is developed in order to provide the features of selenium webdriver to the user with simplicity. Framework is organised and comprehensive.
It follows the page object model and is compatible with java language. 

The structure is divided as follow:

1. Library package - This package contains the important classes like App library, Test base and Configuration.

2. PageObject package - This package consists of the page object classes i.e for each web page there will be one class.

3. Test package - This package will contain all the test classes.



## Installation

Before setting up the workspace we must first download the git for your respective Operating System if you don't already have it.

Execute this `git --version` in your respective Bash/Command Prompt to validate the existence of git on the local system.

https://docs.google.com/document/d/1zawm7Dkn8Gw1zhXyS8l1IJYSavow6UAJOlnmTbybYaU/edit?usp=sharing



## How to run TestNG scripts:

**IDE Configuration**

**Eclipse**

Download and install Eclipse from [here](https://www.eclipse.org/downloads/).

Eclipse plugins:

    1. TestNG
    2. Git(Installed by default in eclipse version 4.21.0 and after).


* TestNG Installation

    1. Java 1.7+ is required for running the TestNG for Eclipse plugin.
    2. Eclipse 4.2 and above is required. Eclipse 3.x is NOT supported any more.
    3. You can use either the Eclipse Marketplace or the update site.
    * Eclipse Marketplace: Go to the TestNG page on the Eclipse Marketplace and drag the icon called "Install" onto your workspace.
    * Update site: Select Help / Install New Software Enter the update site URL in "Work with:" field: https://testng.org/testng-eclipse-update-site. Make sure the check box next to the URL is checked and click Next. Eclipse will then guide you through the process.


## Import Project into Eclipse

Since this is a maven project we need to use Import as maven.

* On the top menu bar go to File --> Import.
* Then select **Existing maven Project**.
* And choose the project location and click Finish.

The eclipse will then take some time to build the project.

Now we are ready to make changes, edit the code and execute the existing scripts.

## Debug and Run Tests

* To run test in eclipse go to test.java  right click and select Run As --> TestNG Test.
* To debug test in eclipse right click on test.java and select Debug As --> TestNG Test.

## Run TestNG.xml 

* To run test in eclipse go to  testng.xml right click and select Run As --> TestNG Suite.


