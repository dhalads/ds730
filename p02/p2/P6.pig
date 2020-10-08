-- Problem 6
/*
A player who hits well and doesn’t commit a lot of errors is obviously a player you
want on your team. Output the playerID’s of the top 3 ranked players from 2005
through 2009 (including 2005 and 2009) who maximized the following criterion:
(number of hits (H) / number of at bats (AB)) – (number of errors (E) / number of games
(G))
The above equation might be skewed by a player who only had 3 at bats but got
two hits. To account for that, only consider players who had at least 40 at bats
and played in at least 20 games over that entire 5 year span . You should note
that both files contain a “number of games” column. The 20 game minimum and
the games values that you are using must come from the Fielding.csv file.
For this problem, be sure to ignore rows in the Fielding.csv file that are in the file
for informational purposes only. An informational row contains no data in the
7th-17th columns (start counting at column 1). In other words, if all of the 7th, 8th,
9th, … 16th and 17th columns are empty, the row is informational and should be
ignored.

Hint

The player who had the 4th best “score” for the equation is Joe Mauer
(mauerjo01). His value for the equation was .299850… One can manually verify
this score by checking his stats:
((144+181+119+176+191)/(489+521+406+536+523)) -
((5+4+1+3+3)/(116+120+91+139+109))
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