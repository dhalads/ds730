
wget http://www.uwosh.edu/faculty_staff/krohne/ds730/wap.txt

hdfs dfs -copyToLocal /user/maria_dev/final .
hdfs dfs -copyFromLocal final /user/maria_dev/
hdfs dfs -copyFromLocal taxi_test.csv /user/maria_dev/

head -n100 taxi.csv > taxi_test.csv
hdfs dfs -copyFromLocal taxi_test.csv /user/zeppelin/

#for part 3

aws s3 ls --human-readable s3://nrel-pds-wtk/bangladesh/ --no-sign-request

https://registry.opendata.aws/3kricegenome/
aws s3 ls --human-readable s3://3kricegenome/ --no-sign-request

https://registry.opendata.aws/aws-covid19-lake/
aws s3 ls s3://covid19-lake/ --no-sign-request

https://registry.opendata.aws/nyc-tlc-trip-records-pds/
aws s3 ls --human-readable s3:"//nyc-tlc/trip data/" --no-sign-request
aws s3 sync .  s3:"//nyc-tlc/trip data/yellow_tripdata_2019-12.csv" --no-sign-request

aws s3 cp s3:"//nyc-tlc/trip data/yellow_tripdata_2011-06.csv" . --no-sign-request


aws s3 ls s3://nyc-tlc/ --no-sign-request


https://registry.opendata.aws/talend-covid19/
aws s3 ls --human-readable s3://covid19-harmonized-dataset/covid19tos3/nytimes_us_states/ --no-sign-request

aws s3 cp s3://covid19-harmonized-dataset/covid19tos3/nytimes_us_states/1605250203_1605255615331.csv . --no-sign-request


https://registry.opendata.aws/noaa-nexrad/
aws s3 ls s3://noaa-nexrad-level2/ --no-sign-request

Global Surface Summary of Day
https://registry.opendata.aws/noaa-gsod/
aws s3 ls s3://aws-gsod/ --no-sign-request