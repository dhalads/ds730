-- Problem 4

-- Output the team(s) that had the most errors in 2001.

-- Hint

-- The teams with the 6th most errors in 2001 was Oakland (OAK) and Baltimore
-- (BAL).


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

DROP TABLE IF EXISTS fielding;

-- playerID,yearID,teamID,lgID,POS,G,GS,InnOuts,PO,A,E,DP,PB,WP,SB,CS,ZR
-- abercda01,1871,TRO,NA,SS,1,,,1,3,2,0,,,,,
-- addybo01,1871,RC1,NA,2B,22,,,67,72,42,5,,,,,

CREATE EXTERNAL TABLE IF NOT EXISTS fielding(id STRING, year INT,
team STRING, league STRING, position STRING, games INT, GS
INT, InnOuts INT, PO INT, assists INT, errors INT, doublePlays INT, passedBall
INT, wildPitches INT, SB INT, CS INT, ZR INT) ROW FORMAT
DELIMITED FIELDS TERMINATED BY ',' LOCATION
'/user/maria_dev/hivetest/fielding'
tblproperties ("skip.header.line.count"="1");

SELECT team
-- , numSum, rankNum
FROM
(SELECT team, sum(errors) as numSum, RANK() OVER (ORDER BY sum(errors) DESC) AS rankNum
FROM 
fielding
WHERE team IS NOT NULL AND errors IS NOT NULL AND year=2001
GROUP BY team) as T1
WHERE T1.rankNum <=1
;