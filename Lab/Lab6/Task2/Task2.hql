ADD JAR ${hiveconf:JarPath};

DROP FUNCTION IF EXISTS udfUpper;
DROP FUNCTION IF EXISTS udfStrip;

CREATE TEMPORARY FUNCTION Upper AS 'edu.rosehulman.xuez.Upper';
CREATE TEMPORARY FUNCTION Strip AS 'edu.rosehulman.xuez.Strip';

CREATE DATABASE IF NOT EXISTS ${hivevar:DBName};

USE ${hivevar:DBName};

CREATE TABLE IF NOT EXISTS ${hiveconf:tableName1} 
(
	line string
);

LOAD DATA INPATH '${hiveconf:INPUT}' OVERWRITE INTO TABLE ${hiveconf:tableName1};

CREATE TABLE IF NOT EXISTS ${hiveconf:tableName2}
(
	word string,
	count int
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;

INSERT OVERWRITE TABLE ${hiveconf:tableName2}
SELECT word, count(1) AS count 
FROM (SELECT EXPLODE(SPLIT(Upper(Strip(line)),' '))
AS word FROM ${hiveconf:tableName1}) words
GROUP BY word
ORDER BY count DESC, word ASC;

SELECT * FROM ${hiveconf:tableName2};
