-- In the batting file, if a player played for more than 1 team in a season, that
-- player will have his name show up in multiple tuples with the same year. For
-- example, in 2011, Francisco Rodriguez ( rodrifr03 ) played for the New York
-- Mets and then played for the Milwaukee Brewers (see tuples 95279 and 95280 ).
-- The question you have to answer is this: what player played for the most teams
-- in any single season (a season is 1 year)? A player may have played for the
-- same team twice in the same season at different times in the season. If this is
-- the case, you should count this as two different teams.

-- Batting.csv
/*
playerID,yearID,teamID,lgID,G,AB,R,H,2B,3B,HR,RBI,SB,CS,BB,SO,IBB,HBP,SH,SF,GIDP
abercda01,1871,TRO,NA,1,4,0,0,0,0,0,0,0,0,0,0,,,,,
addybo01,1871,RC1,NA,25,118,30,32,6,0,0,13,8,1,4,0,,,,,
allisar01,1871,CL1,NA,29,137,28,40,4,5,0,19,3,1,2,5,,,,,
*/

batters = LOAD 'hdfs:/user/maria_dev/pigtest/Batting.csv' using PigStorage(',');

realbatters = FILTER batters BY $1>0;
batter_data = FOREACH realbatters GENERATE $0 AS id, (int)$1 AS year:int;
grouped_data = FOREACH(GROUP batter_data BY (year, id)) GENERATE FLATTEN(group) AS (year, id), COUNT(batter_data.id) AS teamCount;

answer = ORDER grouped_data BY teamCount DESC;

answer1 = LIMIT answer 1;

answerFilter = FILTER answer BY teamCount==answer1.teamCount;
answerFinal = FOREACH answerFilter GENERATE id as id;
DUMP answerFinal

-- Followup review of answer
/*
F_batter = FOREACH realbatters GENERATE $0 AS id, (int)$1 AS year:int, $2 as teamID;
F_answer = FILTER F_batter BY id=='huelsfr01' and year==1904;
DUMP F_answer
*/