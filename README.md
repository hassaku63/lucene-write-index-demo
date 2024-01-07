# README

Apache Lucene sample project that writing text file to Lucene index.

## Prerequisites

```bash
$ java -version
openjdk version "17.0.9" 2023-10-17
# ...

$ mvn -version
Apache Maven 3.9.6 (bc0240f3c744dd6b6ec2920b3cd08dcc295161ae)
# ...

$ make install # mvn clean install
# ...
```

## Usage

```bash
$ make run-rebuild ARGS="-h"
# ...
Usage: main [-hV] [COMMAND]
Hello Lucene Sample Application
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  write  Lucene Sample write application


# $ make run ARGS="-i Makefile -o output/makefile"
$ make run-write-sample 
java -jar target/hello-lucene-1.0-SNAPSHOT-jar-with-dependencies.jar write -i Makefile -o output/Makefile


$ ls -la output/Makefile/
total 48
drwxrwxr-x 2 ubuntu ubuntu 4096 Jan  7 09:24 .
drwxrwxr-x 3 ubuntu ubuntu 4096 Jan  7 09:11 ..
-rw-rw-r-- 1 ubuntu ubuntu  352 Jan  7 09:11 _0.cfe
-rw-rw-r-- 1 ubuntu ubuntu 2681 Jan  7 09:11 _0.cfs
-rw-rw-r-- 1 ubuntu ubuntu  336 Jan  7 09:11 _0.si
-rw-rw-r-- 1 ubuntu ubuntu  352 Jan  7 09:22 _1.cfe
-rw-rw-r-- 1 ubuntu ubuntu 2681 Jan  7 09:22 _1.cfs
-rw-rw-r-- 1 ubuntu ubuntu  336 Jan  7 09:22 _1.si
-rw-rw-r-- 1 ubuntu ubuntu  352 Jan  7 09:24 _2.cfe
-rw-rw-r-- 1 ubuntu ubuntu 2681 Jan  7 09:24 _2.cfs
-rw-rw-r-- 1 ubuntu ubuntu  336 Jan  7 09:24 _2.si
-rw-rw-r-- 1 ubuntu ubuntu  318 Jan  7 09:24 segments_3
-rw-rw-r-- 1 ubuntu ubuntu    0 Jan  7 09:11 write.lock
```
