-- Problem 2

-- Output the top three ranked birthdates that had the most players born. I am only
-- looking for day and month combinations. For instance, how many were born on
-- February 3 rd , how many were born on March 8 th , how many were born on July
-- 20 th … print out the top three mm/dd combinations. Filter out any person who has
-- no birthMonth or no birthDay. You must output the information in mm/dd form (it
-- is ok to print out 5 instead of 05).

-- Hint

-- The birthday that had the 5th most people born on it is 10/4, or October 4th.


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

SELECT CONCAT(bmonth, '/', bday)
-- , year, numTeams, rankNum
FROM
(SELECT bmonth, bday, count(*) as numBirths, RANK() OVER (ORDER BY count(*) DESC) AS rankNum
FROM 
master
WHERE bmonth IS NOT NULL and bday IS NOT NULL
GROUP BY bmonth, bday) as T1
WHERE T1.rankNum <=3
;