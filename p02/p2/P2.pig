-- Problem 2
/*
Output the top three ranked birthMonth/birthYear that had the most players born.
I am only looking for month and year combinations. For instance, how many were
born in February, 1956, how many were born in March, 1975, and so on. Filter
out any person who has no birthMonth or no birthYear. Print out the top three
mm/yyyy combinations. You should report the information in mm/yyyy form.
However, it is ok to print out 5 instead of 05.

Hint

The birth month/birth year combination that had the 7th most people born in it is
a tie between 10/1960, 08/1978, 8/1974, and 10/1969.
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

players = LOAD 'hdfs:/user/maria_dev/pigtest/Master.csv' using PigStorage(',');

player_data = FOREACH players GENERATE $0 AS id, (int)$1 AS birthYear:int, (int)$2 AS birthMonth:int;
player_data = FILTER player_data BY birthYear>0 and birthMonth>0;
player_data_grouped = GROUP player_data BY (birthYear, birthMonth);
best_per_group = FOREACH player_data_grouped GENERATE group.birthYear as birthYear, group.birthMonth as birthMonth, COUNT(player_data) AS groupCount;

best_ranked = RANK best_per_group BY groupCount DESC DENSE;
ranked_answer = FILTER best_ranked BY rank_best_per_group<=3;
answerFinal = FOREACH ranked_answer GENERATE CONCAT((chararray)birthMonth, '/', (chararray)birthYear);
-- answerFinal = FOREACH ranked_answer GENERATE CONCAT((chararray)birthMonth, '/', (chararray)birthYear, ' - ', (chararray)groupCount);
DUMP answerFinal