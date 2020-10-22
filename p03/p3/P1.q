-- Problem 1

-- Output the birth city (or cities) of the player(s) who had the most at bats (AB) in
-- his career.

-- Hint

-- The birth city of the person who had the 10th most at-bats is Donora.


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
INT, InnOuts INT, assists INT, errors INT, doublePlays INT, passedBall
INT, wildPitches INT, SB INT, CS INT, ZR INT) ROW FORMAT
DELIMITED FIELDS TERMINATED BY ',' LOCATION
'/user/maria_dev/hivetest/fielding'
tblproperties ("skip.header.line.count"="1");

SELECT bcity
FROM
(
SELECT id, SUM(ab) as totalAB, RANK() OVER (ORDER BY SUM(ab) DESC)  AS abRank
FROM
batting
GROUP BY id
) as T1
JOIN master ON T1.id=master.id
WHERE T1.abRank <=1
;