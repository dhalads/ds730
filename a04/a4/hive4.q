-- Problem 4

-- Of the right-handed batters who were born in October and died in 2011, which one had the
-- most hits in his career? The column with the heading of H is the hits column. Do not
-- consider switch hitters to be right-handed batters.

DROP TABLE IF EXISTS batting;

CREATE EXTERNAL TABLE IF NOT EXISTS batting(id STRING, year INT,
team STRING, league STRING, games INT, ab INT, runs INT, hits
INT, doubles INT, triples INT, homeruns INT, rbi INT, sb INT, cs
INT, walks INT, strikeouts INT, ibb INT, hbp INT, sh INT, sf
INT, gidp INT) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
LOCATION '/user/maria_dev/hivetest/batting'
tblproperties ("skip.header.line.count"="1");

DROP TABLE IF EXISTS master;

CREATE EXTERNAL TABLE IF NOT EXISTS master(id STRING, byear INT,
bmonth INT, bday INT, bcountry STRING, bstate STRING, bcity
STRING, dyear INT, dmonth INT, dday INT, dcountry STRING, dstate
STRING, dcity STRING, fname STRING, lname STRING, name STRING,
weight INT, height INT, bats STRING, throws STRING, debut
STRING, finalgame STRING, retro STRING, bbref STRING) ROW FORMAT
DELIMITED FIELDS TERMINATED BY ',' LOCATION
'/user/maria_dev/hivetest/master'
tblproperties ("skip.header.line.count"="1");

SELECT id
FROM 
(
SELECT master.id AS id, SUM(COALESCE(hits,0)) as totalHits, RANK() OVER (ORDER BY SUM(COALESCE(hits,0)) DESC)  AS rankHits
FROM 
master
JOIN batting ON master.id=batting.id
WHERE
bats='R' AND bmonth=10 AND dyear=2011
GROUP BY master.id ) AS T1
WHERE rankHits <=1
;

