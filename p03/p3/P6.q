-- Problem 6

-- A player who hits well and doesn’t commit a lot of errors is obviously a player you
-- want on your team. Output the playerID’s of the top 3 ranked players from 2005
-- through 2009 (including 2005 and 2009) who maximized the following criterion:
-- (number of hits (H) / number of at bats (AB)) – (number of errors (E) / number of games
-- (G))
-- The above equation might be skewed by a player who only had 3 at bats but got
-- two hits. To account for that, only consider players who had at least 40 at bats
-- and played in at least 20 games over that entire 5 year span . You should note
-- that both files contain a “number of games” column. The 20 game minimum that
-- you are using is from the Fielding file. For this problem, be sure to ignore rows
-- in the Fielding file that are in the file for informational purposes only. An
-- informational row contains no data in the 7th-17th columns (start counting at
-- column 1). In other words, if all of the 7th, 8th, 9th, … 16th and 17th columns are
-- empty, the row is informational and should be ignored.

-- Hint

-- The player who had the 4th best “score” for the equation is Joe Mauer
-- (mauerjo01). His value for the equation was .299850… One can manually verify
-- this score by checking his stats:
-- ((144+181+119+176+191)/(489+521+406+536+523)) -
-- ((5+4+1+3+3)/(116+120+91+139+109))


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


SELECT id
--, playerScore, rankNum
FROM
(SELECT T1.id AS id, ((sumHits/sumAB)-(sumErrors/sumGames)) as playerScore, RANK() OVER (ORDER BY ((sumHits/sumAB)-(sumErrors/sumGames)) DESC) AS rankNum
--, sumHits, sumAB, sumErrors, sumGames
FROM
(SELECT id, sum(hits) AS sumHits, sum(ab) AS sumAB
FROM 
batting
WHERE hits IS NOT NULL AND ab IS NOT NULL
AND year>=2005 AND year<=2009
GROUP BY id) AS T1
JOIN
(SELECT id, sum(errors) AS sumErrors, sum(games) AS sumGames
FROM 
fielding
WHERE errors IS NOT NULL AND games IS NOT NULL
AND year>=2005 AND year<=2009
GROUP BY id) AS T2 ON T1.id=T2.id
WHERE sumAB>40 AND sumGames>20 ) AS T3
WHERE rankNum<=3
;

