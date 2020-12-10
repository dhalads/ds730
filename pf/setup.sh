
wget http://www.uwosh.edu/faculty_staff/krohne/ds730/wap.txt

hdfs dfs -copyToLocal /user/maria_dev/final .
hdfs dfs -copyFromLocal final /user/maria_dev/

head -n100 taxi.csv > taxi_test.csv
hdfs dfs -copyFromLocal taxi_test.csv /user/zeppelin/
