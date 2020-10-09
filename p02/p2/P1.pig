-- Problem 1
/*
Output the birth city (or cities) of the player(s) who had the most runs batted in (RBI) in his career.

Hint

The birth city of the person who had the 9th most runs batted in is Sudlersville.
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

batters = LOAD 'hdfs:/user/maria_dev/pigtest/Batting.csv' using PigStorage(',');
players = LOAD 'hdfs:/user/maria_dev/pigtest/Master.csv' using PigStorage(',');

realbatters = FILTER batters BY $1>0 and $11>0;
batter_data = FOREACH realbatters GENERATE $0 AS id, (int)$11 AS rbi:int;
player_data = FOREACH players GENERATE $0 AS id, $6 AS birthCity;
batter_data_grouped_by_id = GROUP batter_data BY id;
best_per_id = FOREACH batter_data_grouped_by_id GENERATE group as id, SUM(batter_data.rbi) AS best;

best_rbi_ranked = RANK best_per_id BY best DESC DENSE;
rbi_answer = FILTER best_rbi_ranked BY rank_best_per_id>=9;
answer = FILTER player_data BY id==rbi_answer.id;
answerFinal = FOREACH answer GENERATE birthCity;
DUMP answerFinal