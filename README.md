wetator-maven-plugin
====================

This is a first try to implement a maven plugin for running Weator acceptance tests.

You can find the Wetator homepage at [http://www.wetator.org/](http://www.wetator.org/).

## Usage ##
To use the plugin you have to add this to your maven pom.xml plugins section:

    <plugin>
	    <groupId>org.wetator</groupId>
	    <artifactId>wetator-maven-plugin</artifactId>
	    <version>1.0-SNAPSHOT</version>
	    <configuration>
		    <configFile>src/test/resources/wetator.config</configFile>
		    <testFileDir>src/test/resources/wetator</testFileDir>
	    </configuration>
    </plugin>

By default the plugin includes all *.wet files within the 'testFileDir' folder.

To run the tests run:

    mvn org.wetator:wetator-maven-plugin:execute

## Limitations ##
The wetator dependency included in the plugin is a SNAPSHOT dependency by now. Maybe there will be a release version as maven dependency available in the new future.

The used repository is

    <repositories>
	    <repository>
		    <id>sonatype-repo</id>
		    <url>https://oss.sonatype.org/content/groups/staging</url>
		    <releases>
		    	<enabled>true</enabled>
		    </releases>
		    <snapshots>
		    	<enabled>true</enabled>
		    </snapshots>
	    </repository>
    </repositories>

The current used version is 0.9.15-SNAPSHOT.