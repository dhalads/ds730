-- What player had the most extra base hits during the entire 1980’s (1980 to
-- 1989)? Note that this question is not asking about any 1 specific year. It is
-- asking about the entire 10 year span in the 80’s. An extra base hit is a double,
-- triple or home run (columns 2B , 3B , HR ).

-- Batting.csv
/*
playerID,yearID,teamID,lgID,G,AB,R,H,2B,3B,HR,RBI,SB,CS,BB,SO,IBB,HBP,SH,SF,GIDP
abercda01,1871,TRO,NA,1,4,0,0,0,0,0,0,0,0,0,0,,,,,
addybo01,1871,RC1,NA,25,118,30,32,6,0,0,13,8,1,4,0,,,,,
allisar01,1871,CL1,NA,29,137,28,40,4,5,0,19,3,1,2,5,,,,,
*/

batters = LOAD 'hdfs:/user/maria_dev/pigtest/Batting.csv' using PigStorage(',');
realbatters = FILTER batters BY $1>0;
data = FOREACH realbatters GENERATE $0 AS id, (int)$1 AS year:int, (int)$8 AS doubles:int, (int)$9 AS triples:int, (int)$10 AS hr:int, (int)$8+(int)$9+(int)$10 as total:int;
filtereddata = FILTER data BY year>=1980 and year<=1989;
grouped_data = FOREACH(GROUP filtereddata BY (id)) GENERATE group AS id, SUM(filtereddata.total) AS totalExtra;

answer = ORDER grouped_data BY totalExtra DESC;

answer1 = LIMIT answer 1;

answerFilter = FILTER answer BY totalExtra==answer1.totalExtra;
answerFinal = FOREACH answerFilter GENERATE id as id;
DUMP answerFinal

-- Followup review of answer
/*
F_answer = FILTER filtereddata BY id=='evansdw01';
DUMP F_answer
*/