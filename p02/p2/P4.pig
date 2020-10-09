-- Problem 4
/*
Which team(s) , after 1950, had the most errors (E) in any 1 season. A season is
denoted by the year.

Hint

The team with the 3rd most errors in any season after 1950 was LAA in 1961
*/

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

-- Fielding.csv
/*
playerID,yearID,teamID,lgID,POS,G,GS,InnOuts,PO,A,E,DP,PB,WP,SB,CS,ZR
abercda01,1871,TRO,NA,SS,1,,,1,3,2,0,,,,,
addybo01,1871,RC1,NA,2B,22,,,67,72,42,5,,,,,
addybo01,1871,RC1,NA,SS,3,,,8,14,7,0,,,,,
*/

fielding = LOAD 'hdfs:/user/maria_dev/pigtest/Fielding.csv' using PigStorage(',');

fielding_data = FOREACH fielding GENERATE (int)$1 AS yearID:int, $2 AS teamID, (int)$10 AS errors:int;
fielding_data = FILTER fielding_data BY yearID > 1950;
fielding_data_grouped = GROUP fielding_data BY (yearID, teamID);
data_per_group = FOREACH fielding_data_grouped GENERATE group.yearID as yearID, group.teamID as teamID, SUM(fielding_data.errors) AS totalErrors;

data_ranked = RANK data_per_group BY totalErrors DESC DENSE;
ranked_answer = FILTER data_ranked BY rank_data_per_group==1;
answerFinal = FOREACH ranked_answer GENERATE teamID ;

DUMP answerFinal