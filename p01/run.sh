#!/bin/bash
problemNum="1"

label="p01p$problemNum"

DATE=$(date +"%Y%m%d%H%M%S")
testFolder="/user/maria_dev/hadoopTest_$label"

hadoop_cmd="hadoop jar /usr/hdp/2.6.5.0-292/hadoop-mapreduce/hadoop-streaming.jar"
hadoop_files="-files ./p1/mapper$problemNum.py,./p1/reducer$problemNum.py"
hadoop_io="-input $testFolder/wcinput/* -output $testFolder/wcoutput"
hadoop_map="-mapper mapper$problemNum.py -reducer reducer$problemNum.py"



echo $testFolder

hdfs dfs -mv $testFolder/wcoutput $testFolder/wcouput_$DATE


myCommand="$hadoop_cmd $hadoop_files $hadoop_io $hadoop_map"
echo $myCommand
$myCommand
