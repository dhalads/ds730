CREATE TABLE favnumbers(num INT) ROW FORMAT
DELIMITED FIELDS TERMINATED BY ' ' LINES TERMINATED
BY '\n';

DESCRIBE favnumbers;

LOAD DATA INPATH
'hdfs://sandbox-hdp.hortonworks.com/user/maria_dev/hivetest/numbers.txt' 
INTO TABLE favnumbers;

SELECT * FROM favnumbers;

SELECT COUNT(*) FROM favnumbers;

SELECT * FROM favnumbers WHERE num>100;

DROP TABLE favnumbers;

CREATE EXTERNAL TABLE batting(id STRING, year INT,
team STRING, league STRING, games INT, ab INT, runs
INT, hits INT, doubles INT, triples INT, homeruns
INT, rbi INT, sb INT, cs INT, walks INT, strikeouts
INT, ibb INT, hbp INT, sh INT, sf INT, gidp INT) ROW
FORMAT DELIMITED FIELDS TERMINATED BY ',' LOCATION
'/user/maria_dev/hivetest/batting';

LOAD DATA INPATH
'hdfs://sandbox-hdp.hortonworks.com/user/maria_dev/hivetest/Batting.csv'
INTO TABLE batting;

CREATE EXTERNAL TABLE split_bat(id STRING, runs INT)
PARTITIONED BY(year INT) ROW FORMAT DELIMITED FIELDS
TERMINATED BY ',' LOCATION
'/user/maria_dev/hivetest/split_bat';

SET hive.exec.dynamic.partition.mode=nonstrict;

INSERT OVERWRITE TABLE split_bat PARTITION(year)
SELECT id, runs, year FROM batting WHERE year<1950;

SELECT * FROM split_bat WHERE year=1940;

SET hive.exec.dynamic.partition.mode=nonstrict;
INSERT OVERWRITE TABLE split_bat PARTITION(year)
SELECT id, runs, year FROM batting WHERE year>=1950;

CREATE VIEW hits AS SELECT id, year, doubles,
triples FROM batting WHERE doubles >= 20 AND triples
>= 20;

SELECT * FROM hits;

SET hive.execution.engine=mr;
CREATE INDEX year_index ON TABLE batting(year) AS
'COMPACT' WITH DEFERRED REBUILD;

ALTER INDEX year_index ON batting REBUILD;

SHOW INDEX ON batting;

CREATE EXTERNAL TABLE master(id STRING, byear INT,
bmonth INT, bday INT, bcountry STRING, bstate
STRING, bcity STRING, dyear INT, dmonth INT, dday
INT, dcountry STRING, dstate STRING, dcity STRING,
fname STRING, lname STRING, name STRING, weight INT,
height INT, bats STRING, throws STRING, debut
STRING, finalgame STRING, retro STRING, bbref
STRING) ROW FORMAT DELIMITED FIELDS TERMINATED BY
',' LOCATION '/user/maria_dev/hivetest/master'
tblproperties ("skip.header.line.count"="1");

SELECT * FROM master;

SELECT b.id, b.year, b.runs FROM batting b JOIN
(SELECT year, max(runs) AS best FROM batting GROUP
BY year) o WHERE b.runs=o.best AND b.year=o.year;

SELECT n.fname, n.lname, x.year, x.runs FROM master
n JOIN (SELECT b.id as id, b.year as year, b.runs as
runs FROM batting b JOIN (SELECT year, max(runs) AS
best FROM batting GROUP BY year) o WHERE
b.runs=o.best AND b.year=o.year) x ON x.id=n.id
ORDER BY x.runs DESC;

SELECT id, weight, RANK() OVER (ORDER BY weight
DESC) FROM master;

SELECT id, weight, DENSE_RANK() OVER (ORDER BY
weight DESC) FROM master;

SELECT * FROM ( SELECT id, weight, DENSE_RANK() OVER
(ORDER BY weight DESC) AS ranked FROM master )
subquery WHERE subquery.ranked < 5;




DROP VIEW hits;
DROP INDEX <index_name> ON <table_name>