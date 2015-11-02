create database IF NOT EXISTS ${hivevar:DBName};

use ${hivevar:DBName};

CREATE TABLE IF NOT EXISTS ${hivevar:tableName}
(year int, temperature float, quality int)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;

LOAD DATA  INPATH '${hiveconf:INPUT}' overwrite INTO table ${hivevar:tableName};

select year, max(temperature) from ${hivevar:tableName} group by year;

select year, min(temperature) from ${hivevar:tableName} group by year;

select year, avg(temperature) from ${hivevar:tableName} group by year;

