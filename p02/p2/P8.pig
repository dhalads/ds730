-- Problem 8
/*
Output the birthMonth/birthState combination(s) that produced the worst players
(only the top ranked one(s) should be output, i.e. with a rank of 1). The worst
players are defined by the lowest of:
(number of hits (H) / number of at bats (AB))
To ensure a small number of people who hardly played don’t skew the data,
make sure that:
a. at least 10 people came from the same state and were born in the same
month and
b. the sum of the at-bats for all of the players from the same
birthMonth/birthState exceeds 1500.
For this problem, the year does not matter. A player born in December, 1970 in
Michigan and a player born in December, 1982 in Michigan are in the same
group because they were both born in December and were born in Michigan. A
birthState is any non-empty value in the birthState column. In terms of condition
a., you should count a player as one of your 10 players even if the player has no
at-bats and/or no hits. You should ignore all players who do not have a
birthMonth or who do not have a birthState.

Hint

The birthMonth/birthState combination that produced the 4th worst players is
7/LA.

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