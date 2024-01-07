ARGS=

.PHONY: install
install:
	@mvn clean install

.PHONY: clean
clean:
	@mvn clean

.PHONY: compile
compile:
	@mvn compile

.PHONY: package
package:
	@mvn package

.PHONY: build
build:
	@mvn compile && mvn package

.PHONY: run-rebuild
run-rebuild: build
	@java -jar target/hello-lucene-1.0-SNAPSHOT-jar-with-dependencies.jar $(ARGS)

.PHONY: run
run:
	@java -jar target/hello-lucene-1.0-SNAPSHOT-jar-with-dependencies.jar $(ARGS)

.PHONY: run-write
run-write:
	java -jar target/hello-lucene-1.0-SNAPSHOT-jar-with-dependencies.jar write $(ARGS)

.PHONY: run-write-sample
run-write-sample:
	java -jar target/hello-lucene-1.0-SNAPSHOT-jar-with-dependencies.jar write -i Makefile -o output/Makefile

# execute post-build if you have GraalVM installed and will generate native binary
.PHONY: native-image
native-image:
	@native-image -jar target/hello-lucene-1.0-SNAPSHOT-jar-with-dependencies.jar