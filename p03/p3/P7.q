-- Problem 7

-- Sum up the number of doubles and triples for each birthCity/birthState
-- combination. Output the top 5 ranked birthCity/birthState combinations that
-- produced the players who had the most doubles and triples (i.e. combine the
-- doubles and triples for all players with that city/state combination). A birthState is
-- any non-empty value in the birthState column.

-- Hint

-- The birthCity/birthState combination that produced the players with the 7th most
-- doubles and triples is Brooklyn/NY.



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


SELECT CONCAT(bcity, '/', bstate)
-- , totalDT, rankNum
FROM
(SELECT bcity, bstate, (sumDoubles + sumTriples) as totalDT, RANK() OVER (ORDER BY (sumDoubles + sumTriples) DESC) AS rankNum
FROM
(SELECT bcity, bstate, SUM(doubles) AS sumDoubles, SUM(triples) AS sumTriples
FROM 
batting
JOIN master ON batting.id=master.id
WHERE doubles IS NOT NULL AND triples IS NOT NULL
AND bcity IS NOT NULL AND bstate IS NOT NULL
GROUP BY bcity, bstate) AS T1
) AS T2
WHERE rankNum<=5
;

