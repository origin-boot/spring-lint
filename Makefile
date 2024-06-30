init:
	mvn archetype:generate -DgroupId=com.origin.lint \
		-DartifactId=spring-lint \
		-DarchetypeArtifactId=maven-archetype-quickstart \
		-DinteractiveMode=false

clean:
	mvn clean install

run:
	mvn exec:java -Dexec.mainClass="com.origin.lint.App" -Dexec.args="."

package:
	mvn package
