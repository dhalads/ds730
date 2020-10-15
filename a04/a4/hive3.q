-- Problem 2

-- What player had the most extra base hits during the entire 1980’s (1980 to 1989)? Note
-- that this question is not asking about any 1 specific year. It is asking about the entire 10
-- year span in the 80’s. An extra base hit is a double, triple or home run (columns 2B , 3B ,
-- HR ).

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
SELECT id, SUM(extraBaseHits) as totalExtra, RANK() OVER (ORDER BY SUM(extraBaseHits) DESC)  AS totalRank
FROM
(
SELECT id, doubles, triples, homeruns, (COALESCE(doubles, 0) + COALESCE(triples,0) + COALESCE(homeruns, 0)) as extraBaseHits
FROM
batting
WHERE year>=1980 AND year<=1989
-- AND id='youngma01'
 ) as T1
GROUP BY id
) as T2
WHERE totalRank <=1
;

