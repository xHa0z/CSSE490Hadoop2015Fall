USE ${hiveconf:databaseName};

SELECT cno, count(*) FROM ${hiveconf:tableName} WHERE letter!="D" GROUP BY cno;
