-- Problem 8

-- Output the birthMonth/birthState combination(s) that produced the worst players.
-- The worst players are defined by the lowest of:
-- (number of hits (H) / number of at bats (AB))
-- To ensure 1 player who barely played does not skew the data, make sure that:
-- a. at least 5 people came from the same state and were born in the same
-- month and
-- b. the sum of the at-bats for all of the players from the same month/state
-- exceeds 100.
-- For this problem, the year does not matter. A player born in December, 1970 in
-- Michigan and a player born in December, 1982 in Michigan are in the same
-- group because they were both born in December and were born in Michigan. A
-- birthState is any non-empty value in the birthState column. In terms of condition
-- a., you should count a player as one of your 5 players even if the player has no
-- at-bats and/or no hits. You should ignore all players who do not have a
-- birthMonth or who do not have a birthState.

-- Hint

-- The birthMonth/birthState combination that produced the 5th worst players is
-- 2/Colorado.
