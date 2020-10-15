-- Problem 2

-- In the batting file, if a player played for more than 1 team in a season, that player will have
-- his name show up in multiple tuples with the same year. For example, in 2011, Francisco
-- Rodriguez ( rodrifr03 ) played for the New York Mets and then played for the Milwaukee
-- Brewers (see tuples 95279 and 95280 ). The question you have to answer is this: what
-- player played for the most teams in any single season? A player may have played for the
-- same team twice in the same season at different times in the season. If this is the case, you
-- should count this as two different teams.

DROP TABLE IF EXISTS batting;

CREATE EXTERNAL TABLE IF NOT EXISTS batting(id STRING, year INT,
team STRING, league STRING, games INT, ab INT, runs INT, hits
INT, doubles INT, triples INT, homeruns INT, rbi INT, sb INT, cs
INT, walks INT, strikeouts INT, ibb INT, hbp INT, sh INT, sf
INT, gidp INT) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
LOCATION '/user/maria_dev/hivetest/batting';

DROP TABLE IF EXISTS master;

CREATE EXTERNAL TABLE IF NOT EXISTS master(id STRING, byear INT,
bmonth INT, bday INT, bcountry STRING, bstate STRING, bcity
STRING, dyear INT, dmonth INT, dday INT, dcountry STRING, dstate
STRING, dcity STRING, fname STRING, lname STRING, name STRING,
weight INT, height INT, bats STRING, throws STRING, debut
STRING, finalgame STRING, retro STRING, bbref STRING) ROW FORMAT
DELIMITED FIELDS TERMINATED BY ',' LOCATION
'/user/maria_dev/hivetest/master';

SELECT id
-- , year, numTeams, rankNum
FROM
(SELECT year, id, count(*) as numTeams, RANK() OVER (ORDER BY count(*) DESC) AS rankNum
FROM 
batting
GROUP BY year, id) as T1
WHERE rankNum <=1
;
