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
$ make run ARGS="-h"
# ...
Usage: hellolucene [-hV] [-i=<inputFilePath>] [-o=<outputFilePath>] [-t=<title>]
Hello Lucene Sample Application
  -h, --help            Show this help message and exit.
  -i, --input=<inputFilePath>
                        Input file path
  -o, --output=<outputFilePath>
                        Output file path
  -t, --title=<title>   Title of input file. Default is input file name
  -V, --version         Print version information and exit.

$ make run ARGS="-i Makefile -o output/makefile"
# ...

$ $ ls -l output/makefile/
total 16
-rw-rw-r-- 1 ubuntu ubuntu  352 Jan  6 15:15 _0.cfe
-rw-rw-r-- 1 ubuntu ubuntu 2094 Jan  6 15:15 _0.cfs
-rw-rw-r-- 1 ubuntu ubuntu  336 Jan  6 15:15 _0.si
-rw-rw-r-- 1 ubuntu ubuntu  154 Jan  6 15:15 segments_1
-rw-rw-r-- 1 ubuntu ubuntu    0 Jan  6 15:15 write.lock
```
