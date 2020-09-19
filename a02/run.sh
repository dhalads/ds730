#!/bin/bash

DATE=$(date +"%Y%m%d%H%M%S")

hadoop_cmd="hadoop jar /usr/hdp/2.6.5.0-292/hadoop-mapreduce/hadoop-streaming.jar"
hadoop_files="-files ./a2/mapper.py,./a2/reducer.py"
hadoop_io="-input /user/maria_dev/hadoopTest/wcinput/* -output /user/maria_dev/hadoopTest/wcoutput"
hadoop_map="-mapper mapper.py -reducer reducer.py"

hdfs dfs -mkdir -p /user/maria_dev/hadoopTest
hdfs dfs -mkdir -p /user/maria_dev/hadoopTest/wcinput
hdfs dfs -rm -r /user/maria_dev/hadoopTest/wcinput/*
hdfs dfs -copyFromLocal ./a2/input.txt /user/maria_dev/hadoopTest/wcinput/
#hdfs dfs -rmdir --ignore-fail-on-non-empty  /user/maria_dev/hadoopTest/wcoutput
hdfs dfs -mv /user/maria_dev/hadoopTest/wcoutput /user/maria_dev/hadoopTest/wcouput_$DATE


$hadoop_cmd $hadoop_files $hadoop_io $hadoop_map
