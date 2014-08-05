wetator-maven-plugin
====================

This is a first try to implement a maven plugin for running Weator acceptance tests.

You can find the Wetator homepage at [http://www.wetator.org/](http://www.wetator.org/).

## Build Status ##
[![Build Status](https://travis-ci.org/guerda/wetator-maven-plugin.svg?branch=master)](https://travis-ci.org/guerda/wetator-maven-plugin)

## Usage ##
To use the plugin you have to add this to your maven pom.xml plugins section:

    <plugin>
	    <groupId>org.wetator</groupId>
	    <artifactId>wetator-maven-plugin</artifactId>
	    <version>1.0.2-SNAPSHOT</version>
	    <configuration>
		    <configFile>src/test/resources/wetator.config</configFile>
		    <testFileDir>src/test/resources/wetator</testFileDir>
	    </configuration>
    </plugin>

By default the plugin includes all *.wet files within the 'testFileDir' folder.

To run the tests run:

    mvn org.wetator:wetator-maven-plugin:execute

## Remarks ##
The wetator-maven-plugin is using the wetator dependency:

    <dependency>
	    <groupId>org.wetator</groupId>
	    <artifactId>wetator</artifactId>
	    <version>1.0.0</version>
    </dependency>
