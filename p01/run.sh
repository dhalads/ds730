#!/bin/bash

label="p01p1"

DATE=$(date +"%Y%m%d%H%M%S")
testFolder="/user/maria_dev/hadoopTest_$label"

hadoop_cmd="hadoop jar /usr/hdp/2.6.5.0-292/hadoop-mapreduce/hadoop-streaming.jar"
hadoop_files="-files ./a2/mapper.py,./a2/reducer.py"
hadoop_io="-input $testFolder/wcinput/* -output $testFolder/wcoutput"
hadoop_map="-mapper mapper.py -reducer reducer.py"



echo $testFolder

hdfs dfs -mv $testFolder/wcoutput $testFolder/wcouput_$DATE


$hadoop_cmd $hadoop_files $hadoop_io $hadoop_map
