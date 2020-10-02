#!/bin/bash

#aws s3 ls s3://halama1668/a03/t4/

# hdfs://user/maria_dev/pigtest/Master.csv

#aws s3 cp Master.csv s3://halama1668/a03/t4/input/

hdfs dfs -copyFromLocal orders.tmp $testFolder/wcinput/orders.csv

hdfs dfs -copyToLocal /user/maria_dev/pigtest/Batting.csv .



eval $(grep aws_access_key_id ~/.aws/credentials)
eval $(grep aws_secret_access_key ~/.aws/credentials)

s3_keys="-Dfs.s3a.access.key=$aws_access_key_id -Dfs.s3a.secret.key=$aws_secret_access_key"

hadoop distcp $s3_keya hdfs://user/maria_dev/pigtest/Master.csv s3a://halama1668/a03/t4/