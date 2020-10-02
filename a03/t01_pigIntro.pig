Complaints = LOAD 'hdfs:/user/maria_dev/pigtest/Complaints.csv' USING PigStorage(',') AS (date:chararray, product:chararray, subprod:chararray, issue:chararray, subissue:chararray, narrative:chararray, response:chararray, company:chararray, state:chararray, zip:chararray, submitted:chararray, senttocomp:chararray, compresponse:chararray, timelyresponse:chararray, disputed:chararray, id:int);

DESCRIBE Complaints;

Oshkosh = FILTER Complaints BY state=='WI' AND zip=='54904';

DUMP Oshkosh;

grouped = GROUP Oshkosh ALL;
total = FOREACH grouped GENERATE COUNT(Oshkosh);
DUMP total;


ContainsAnIOrE = FILTER Complaints BY (state MATCHES '.*I.*') OR (state MATCHES '.*E.*');

STORE Oshkosh INTO 'hdfs:/user/maria_dev/pigtest/wisconsin' USING PigStorage(':', '-schema');

readable = FOREACH Oshkosh GENERATE date, product, company, compresponse;

DUMP readable;

firstfilter = FOREACH Complaints GENERATE date, product, company, state, zip, submitted;

filtered = FILTER firstfilter BY state IS NOT NULL;

groupedbystate = GROUP filtered BY state;

DESCRIBE groupedbystate;

agged = FOREACH groupedbystate GENERATE group, COUNT(filtered) AS total;

sorted = ORDER agged BY total DESC;

topten = LIMIT sorted 10;

DUMP topten;

ranked = RANK sorted BY total DESC;

toptenranked = FILTER ranked BY rank_sorted < 10;

DUMP toptenranked;

wisc = FILTER agged BY group=='WI';

DUMP wisc;

webandphone = FILTER filtered BY (submitted=='Web' OR submitted=='Phone') AND state=='WI';

groupbymore = GROUP webandphone BY (state,submitted);

agged = FOREACH groupbymore GENERATE group, COUNT(webandphone) AS total;

DUMP agged;