hdfs dfs -copyToLocal /user/maria_dev/pigtest/Fielding.csv .
rsync -r awshorton:./ds730_local/hivetest .
hdfs dfs -copyFromLocal hivetest /user/maria_dev/


# livy
#  https://datacadamia.com/db/spark/livy

curl -u admin -G "http://192.168.1.20:8999/"