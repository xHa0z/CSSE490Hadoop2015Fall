//Create a target database in mysql

create database sqooptest;

//Switch to the mysql database that we just created
use sqooptest;

//Create a target table to serve as source of data import.
create table Students(sid int, sname varchar(25), cno varchar(25));

//Verify that table has been created and that it has no data
show tables;
Select * from Students;

//Insert data into the Students table.
INSERT INTO `Students` (`sid`, `sname`, `cno`)
VALUES
	(1,'A','CSSE333'),
	(2,'B','CSSE333'),
	(3,'C','CSSE333'),
	(11,'D','CSSE490'),
	(12,'E','CSSE490'),
	(13,'F','CSSE490'),
	(14,'F','CSSE490'),
	(31,'G','CSSE371'),
	(32,'H','CSSE371'),
	(33,'I','CSSSE371'),
	(34,'J','CSSE371'),
	(41,'K','CSSE497'),
	(42,'L','CSSE497'),
	(43,NULL,'CSSE498'),
	(44,'M','CSSE499'),
	(45,'N','CSSE499');

Sqoop Import
------------
//Simple import to target directory. Sqoop auto determines number of mappers
sqoop import --connect jdbc:mysql://sandbox.hortonworks.com:3306/sqooptest --username root --table Students --split-by sid --target-dir /tmp/sqoopOutput

//Deleting target dir

sqoop import --connect jdbc:mysql://sandbox.hortonworks.com:3306/sqooptest --username root --table Students --split-by sid --delete-target-dir --target-dir /tmp/sqoopOutput

//Controlling number of mappers

sqoop import --connect jdbc:mysql://sandbox.hortonworks.com:3306/sqooptest --username root --table Students -m 1 --target-dir /tmp/sqoopMapOutput

//Writing as a sequence file
sqoop import --connect jdbc:mysql://sandbox.hortonworks.com:3306/sqooptest --username root -m 1 --table Students --target-dir /tmp/sqoopSeqOutput --as-sequencefile

//Warehouse dir
sqoop import --connect jdbc:mysql://sandbox.hortonworks.com:3306/sqooptest --username root -m 1 --table Students --warehouse-dir /tmp/sqoop

//Change Delimiters & Null representation
sqoop import --connect jdbc:mysql://sandbox.hortonworks.com:3306/sqooptest --username root --delete-target-dir -m 1 --table Students --warehouse-dir /tmp/sqoop --fields-terminated-by '\t' --null-string 'NULL String' --null-non-string '\\N'

//Import-all
sqoop import-all-tables --connect jdbc:mysql://sandbox.hortonworks.com:3306/sqooptest --username root -m 1 --warehouse-dir /tmp/sqoopAll/ --fields-terminated-by ',' --null-string '\\N' --null-non-string '\\N'

//Import to Hive
sqoop import --connect jdbc:mysql://sandbox.hortonworks.com:3306/sqooptest --username root -m 1 --table Students --hive-import --create-hive-table --hive-table sqooptest.Students

Sqoop Export
------------

create table StudentsExportTest(sid int, sname varchar(25), cno varchar(25), UNIQUE KEY duplicateConstraint(sid));

//Export without updates
sqoop export --connect jdbc:mysql://sandbox.hortonworks.com:3306/sqooptest --username root -m 1 --table StudentsExportTest --export-dir /tmp/sqoop/Students --input-fields-terminated-by '\t' --input-null-string 'NULL String' --input-null-non-string '\\N'

//Export with updates
sqoop export --connect jdbc:mysql://sandbox.hortonworks.com:3306/sqooptest --username root -m 1 --table StudentsExportTest --export-dir /tmp/sqoop/Students --input-fields-terminated-by '\t' --input-null-string 'NULL String' --input-null-non-string '\\N' --update-key sid --update-mode allowinsert
