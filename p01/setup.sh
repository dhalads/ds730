#!/bin/bash

label="p01p1"

DATE=$(date +"%Y%m%d%H%M%S")
testFolder="/user/maria_dev/hadoopTest_$label"

wget http://www.uwosh.edu/faculty_staff/krohne/ds730/orders.csv
mv orders.csv orders.tmp

echo $testFolder

hdfs dfs -mkdir -p $testFolder
hdfs dfs -mkdir -p $testFolder/wcinput
hdfs dfs -rm -r $testFolder/wcinput/*
hdfs dfs -copyFromLocal orders.tmp $testFolder/wcinput/orders.csv
