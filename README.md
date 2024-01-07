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

### Crate index

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

## Read from index

create "index1"

```bash
$ find . -type f -name "*.java" -print0 | xargs -0 -I {} make run ARGS="write -i {} -o output/index1"
$ find . -type f -name "*.xml" -print0 | xargs -0 -I {} make run ARGS="write -i {} -o output/index1"
$ find . -type f -name "*.md" -print0 | xargs -0 -I {} make run ARGS="write -i {} -o output/index1"
$ find . -type f -name "Makefile" -print0 | xargs -0 -I {} make run ARGS="write -i {} -o output/index1"
$ ls output/index1/
_0.cfe  _0.cfs  _0.si  _1.cfe  _1.cfs  _1.si  _2.cfe  _2.cfs  _2.si  _3.cfe  _3.cfs  _3.si  _4.cfe  _4.cfs  _4.si  _5.cfe  _5.cfs  _5.si  segments_6  write.lock

$ find . -type f -name "*.java" -print0 | xargs -0 -I {} make run ARGS="write -i {} -o output/index1"
$ ls output/index1/
_0.cfe  _0.si   _1.cfs  _2.cfe  _2.si   _3.cfs  _4.cfe  _4.si   _5.cfs  _6.cfe  _6.si   _7.cfs  _8.cfe  _8.si       write.lock
_0.cfs  _1.cfe  _1.si   _2.cfs  _3.cfe  _3.si   _4.cfs  _5.cfe  _5.si   _6.cfs  _7.cfe  _7.si   _8.cfs  segments_9

$ find . -type f -name "*.java" -print0 | xargs -0 -I {} make run ARGS="write -i {} -o output/index1"
$ ls output/index1/
_1.cfe  _1.si   _b.fdt  _b.fnm  _b.kdi  _b.nvd  _b.si              _b_Lucene90_0.dvm  _b_Lucene99_0.pos  _b_Lucene99_0.tip  _c.cfe  _c.si       write.lock
_1.cfs  _b.fdm  _b.fdx  _b.kdd  _b.kdm  _b.nvm  _b_Lucene90_0.dvd  _b_Lucene99_0.doc  _b_Lucene99_0.tim  _b_Lucene99_0.tmd  _c.cfs  segments_d
```

read from "index1"

```bash
$ make run ARGS="read -i output/index1"
# Directory metadata (Segment statistics)
Index directory path: output/index1
Index version: 40
Number of documents (excluding deleted documents): 12
Number of deleted documents: 0
Number of leaves (idnexed fileds): 3

## Leaf 0 metadata
Field 0: Name=title Value=DescribeIndexMetadata.java
Field 0: Name=file_name Value=DescribeIndexMetadata.java
Field 0: Name=content Value=package net.hassaku63.hellolucene; ...
Field 0: Name=last_modified_time Value=2024-01-07T21:27:06.223146296Z
Field 0: Name=last_modified_time_millis Value=1704662826223
Field 0: Name=size Value=6275
Leaf 0 number of documents: 10
Leaf 0 number of deleted documents: 0
FieldInfo 0: title
        Key=PerFieldPostingsFormat.format Value=Lucene99
        Key=PerFieldPostingsFormat.suffix Value=0
FieldInfo 1: title_no_store
        Key=PerFieldPostingsFormat.format Value=Lucene99
        Key=PerFieldPostingsFormat.suffix Value=0
FieldInfo 2: file_name
        Key=PerFieldPostingsFormat.format Value=Lucene99
        Key=PerFieldPostingsFormat.suffix Value=0
FieldInfo 3: content
        Key=PerFieldPostingsFormat.format Value=Lucene99
        Key=PerFieldPostingsFormat.suffix Value=0
FieldInfo 4: last_modified_time
        Key=PerFieldPostingsFormat.format Value=Lucene99
        Key=PerFieldPostingsFormat.suffix Value=0
FieldInfo 5: last_modified_time_millis
        Key=PerFieldDocValuesFormat.format Value=Lucene90
        Key=PerFieldDocValuesFormat.suffix Value=0
FieldInfo 6: size
        Key=PerFieldDocValuesFormat.format Value=Lucene90
        Key=PerFieldDocValuesFormat.suffix Value=0

## Leaf 1 metadata
Field 1: Name=title Value=DescribeIndexMetadata.java
...
```
