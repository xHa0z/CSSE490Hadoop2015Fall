CREATE DATABASE IF NOT EXISTS ${hiveconf:DBName};

use ${hiveconf:DBName};

CREATE TABLE IF NOT EXISTS archiveLogData(
name string, hit double, error double, year int, month int, day int, hour int)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;

LOAD DATA INPATH '${hiveconf:PigOutDir}/${hiveconf:runDate}' OVERWRITE INTO TABLE archiveLogData;

CREATE TABLE IF NOT EXISTS logData(name string, hit double, error double)
partitioned by (year int, month int, day int, hour int)
row format delimited fields terminated by '\t'
stored as ORC;

SET hive.exec.dynamic.partition.mode=nonstrict;
INSERT INTO TABLE logData partition(year, month, day, hour) SELECT name,hit,error,year,month,day,hour FROM archiveLogData WHERE (year=${hiveconf:year} AND month=${hiveconf:month} AND day=${hiveconf:day} AND hour=${hiveconf:hour});
SET hive.exec.dynamic.partition.mode=strBLE IF NOT EXISTS logData(name string, hit double, error double)
partitioned by (year int, month int, day int, hour int)
row format delimited fields terminated by '\t'
stored as ORC;

SET hive.exec.dynamic.partition.mode=nonstrict;
INSERT INTO TABLE logData partition(year, month, day, hour) SELECT name,hit,error,year,month,day,hour FROM archiveLogData WHERE (year=${hiveconf:year} AND month=${hiveconf:month} AND day=${hiveconf:day} AND hour=${hiveconf:hour});
SET hive.exec.dynamic.partition.mode=strict;


select count(*) from archiveLogData;
select count(*) from logData;

