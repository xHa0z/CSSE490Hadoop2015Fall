USE ${hiveconf:databaseName};

LOAD DATA INPATH '${hiveconf:inpath}/${hiveconf:username}/part*' INTO TABLE ${hiveconf:tempTableName} PARTITION(username = 'xuez');

SET hive.exec.dynamic.partition.mode=nonstrict;
INSERT INTO TABLE ${hiveconf:tableName} partition(username)
SELECT name, cno, cname, letter, username FROM ${hiveconf:tempTableName};
SET hive.exec.dynamic.partition.mode=strict;

select * from ${hiveconf:tempTableName};
select * from ${hiveconf:tableName};

