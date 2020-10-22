-- Problem 8

-- Output the birthMonth/birthState combination(s) that produced the worst players.
-- The worst players are defined by the lowest of:
-- (number of hits (H) / number of at bats (AB))
-- To ensure 1 player who barely played does not skew the data, make sure that:
-- a. at least 5 people came from the same state and were born in the same
-- month and
-- b. the sum of the at-bats for all of the players from the same month/state
-- exceeds 100.
-- For this problem, the year does not matter. A player born in December, 1970 in
-- Michigan and a player born in December, 1982 in Michigan are in the same
-- group because they were both born in December and were born in Michigan. A
-- birthState is any non-empty value in the birthState column. In terms of condition
-- a., you should count a player as one of your 5 players even if the player has no
-- at-bats and/or no hits. You should ignore all players who do not have a
-- birthMonth or who do not have a birthState.

-- Hint

-- The birthMonth/birthState combination that produced the 5th worst players is
-- 2/Colorado.

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


SELECT CONCAT(bmonth, '/', bstate)
-- , score , rankNum
FROM
    (SELECT bmonth, bstate, (sumHits/sumAB) AS score, RANK() OVER (ORDER BY (sumHits/sumAB) ASC) AS rankNum
    FROM
        (SELECT master.bmonth AS bmonth, master.bstate AS bstate, SUM(hits) AS sumHits, SUM(ab) AS sumAB
        FROM 
        batting
        JOIN master ON batting.id=master.id
        JOIN (
            SELECT bmonth, bstate, COUNT(*) AS numPlayers
            FROM master
            WHERE bmonth IS NOT NULL AND bstate IS NOT NULL
            GROUP BY bmonth, bstate
            HAVING COUNT(*) >= 5
        ) AS T3 ON T3.bmonth=master.bmonth AND T3.bstate=master.bstate
        WHERE hits IS NOT NULL AND ab IS NOT NULL
        AND master.bmonth IS NOT NULL AND master.bstate IS NOT NULL
        GROUP BY master.bmonth, master.bstate
        HAVING SUM(ab) > 100 ) AS T1
    ) AS T2
WHERE rankNum<=1
;






