-- Of the right-handed batters (column bats , denoted with an R) who were born in
-- October and died in 2011, which one had the most hits in his career? The
-- column with the heading of H is the hits column. Do not consider switch hitters
-- to be right-handed batters.

-- Batting.csv
/*
playerID,yearID,teamID,lgID,G,AB,R,H,2B,3B,HR,RBI,SB,CS,BB,SO,IBB,HBP,SH,SF,GIDP
abercda01,1871,TRO,NA,1,4,0,0,0,0,0,0,0,0,0,0,,,,,
addybo01,1871,RC1,NA,25,118,30,32,6,0,0,13,8,1,4,0,,,,,
allisar01,1871,CL1,NA,29,137,28,40,4,5,0,19,3,1,2,5,,,,,
*/

-- Master.csv
/*
playerID,birthYear,birthMonth,birthDay,birthCountry,birthState,birthCity,deathYear,deathMonth,deathDay,deathCountry,deathState,deathCity,nameFirst,nameLast,nameGiven,weight,height,bats,throws,debut,finalGame,retroID,bbrefID
aardsda01,1981,12,27,USA,CO,Denver,,,,,,,David,Aardsma,David Allan,205,75,R,R,4/6/2004,9/28/2013,aardd001,aardsda01
aaronha01,1934,2,5,USA,AL,Mobile,,,,,,,Hank,Aaron,Henry Louis,180,72,R,R,4/13/1954,10/3/1976,aaroh101,aaronha01
aaronto01,1939,8,5,USA,AL,Mobile,1984,8,16,USA,GA,Atlanta,Tommie,Aaron,Tommie Lee,190,75,R,R,4/10/1962,9/26/1971,aarot101,aaronto01
aasedo01,1954,9,8,USA,CA,Orange,,,,,,,Don,Aase,Donald William,190,75,R,R,7/26/1977,10/3/1990,aased001,aasedo01
*/


batters = LOAD 'hdfs:/user/maria_dev/pigtest/Batting.csv' using PigStorage(',');
players = LOAD 'hdfs:/user/maria_dev/pigtest/Master.csv' using PigStorage(',');

realbatters = FILTER batters BY $1>0;
batter_data = FOREACH realbatters GENERATE $0 AS id, (int)$7 AS hits:int;
player_data = FOREACH players GENERATE $0 AS id, (int)$2 AS birthMonth:int, (int)$7 AS deathYear:int, $18 as bats;
player_filter = FILTER player_data BY birthMonth==10 and deathYear==2011 and bats=='R';
joined_data = JOIN batter_data BY (id), player_filter BY (id);
joined_clean = FOREACH joined_data GENERATE $0 AS id, (int)$1 AS hits:int;

grouped_data = FOREACH(GROUP joined_clean BY (id)) GENERATE group AS id, SUM(joined_clean.hits) AS totalHits;

answer = ORDER grouped_data BY totalHits DESC;

answer1 = LIMIT answer 1;

answerFilter = FILTER answer BY totalHits==answer1.totalHits;
answerFinal = FOREACH answerFilter GENERATE id as id;
DUMP answerFinal

-- Followup review of answer
/*
F_answer = FILTER joined_clean BY id=='careyan01';
DUMP F_answer
*/


