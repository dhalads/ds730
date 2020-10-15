-- Problem 6

-- A player who hits well and doesn’t commit a lot of errors is obviously a player you
-- want on your team. Output the playerID’s of the top 3 ranked players from 2005
-- through 2009 (including 2005 and 2009) who maximized the following criterion:
-- (number of hits (H) / number of at bats (AB)) – (number of errors (E) / number of games
-- (G))
-- The above equation might be skewed by a player who only had 3 at bats but got
-- two hits. To account for that, only consider players who had at least 40 at bats
-- and played in at least 20 games over that entire 5 year span . You should note
-- that both files contain a “number of games” column. The 20 game minimum that
-- you are using is from the Fielding file. For this problem, be sure to ignore rows
-- in the Fielding file that are in the file for informational purposes only. An
-- informational row contains no data in the 7th-17th columns (start counting at
-- column 1). In other words, if all of the 7th, 8th, 9th, … 16th and 17th columns are
-- empty, the row is informational and should be ignored.

-- Hint

-- The player who had the 4th best “score” for the equation is Joe Mauer
-- (mauerjo01). His value for the equation was .299850… One can manually verify
-- this score by checking his stats:
-- ((144+181+119+176+191)/(489+521+406+536+523)) -
-- ((5+4+1+3+3)/(116+120+91+139+109))

