CREATE database IF NOT EXISTS ${hiveconf:databaseName};

USE ${hiveconf:databaseName};

CREATE TABLE IF NOT EXISTS ${hiveconf:tempTableName}
(name string,cno string,cname string,letter string)
PARTITIONED by (username string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;

CREATE TABLE IF NOT EXISTS ${hiveconf:tableName}
(name string, cno string, cname string, letter string)
PARTITIONED by (username string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS ORC;


