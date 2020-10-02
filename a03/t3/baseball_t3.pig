REGISTER 'hdfs:/user/maria_dev/pigtest/baseball.py' USING jython AS myfuncs
batters = LOAD 'hdfs:/user/maria_dev/pigtest/Batting.csv' using PigStorage(',');
realbatters = FILTER batters BY $1>0;
data = FOREACH realbatters GENERATE $0 AS id, (int)$1 AS year:int, (int)$8 AS doubles:int, (int)$9 AS triples:int, (int)$10 AS hr:int;
filtereddata = FILTER data BY year==2011;
answer = FOREACH filtereddata GENERATE myfuncs.getVal(doubles, triples, hr), id;
DUMP answer;
DESCRIBE answer;
best_player = ORDER answer BY value DESC;
top_ten = LIMIT best_player 10;
DUMP top_ten;