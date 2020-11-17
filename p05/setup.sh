
wget http://www.uwosh.edu/faculty_staff/krohne/ds730/wap.txt
hdfs dfs -copyToLocal /user/zeppelin/taxi.csv .
hdfs dfs -copyFromLocal taxi.csv /user/zeppelin/

head -n100 taxi.csv > taxi_test.csv
hdfs dfs -copyFromLocal taxi_test.csv /user/zeppelin/
