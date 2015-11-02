CREATE DATABSE IF NOT EXISTS ${hivevar:DBName};
use ${hivevar:DBName};

CREATE table if not exists RoseEmployees{firstName string, lastName string, speciality string, dept string, employeeNumber string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS TEXTFILE;

LOAD DATA INPATH '${hiveconf:allEmployeesLocation}' overwrite into table RoseEmployees;

CREATE IF NOT EXISTS RoseStaticEmployees(firstName string lastName string, speciality string, employeeNumber string)
PARTITIONED by (dept string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
SOTRED AS TEXTFILE;

LOAD DATA INPATH '${hiveconf:csseEmployeesLocation}' INTO table RoseStaticEmployees
Partition(dept = 'csse');

LOAD DATA INPATH '${hiveconf:eceEmployeesLocation}' INTO table RoseStaticEmployees
Partition(dept = 'ece');

LOAD DATA INPAT '${hiveconf:adminEmployeesLocation}' INTO table RoseStaticEmployees
Partition(dept = 'admin');



CREATE TABLE IF NOT EXISTS RoseDynamicEmployees(firstName string, lastName string, speciality string, employeeNumber string)
Partitioned by (dept string)
row format delimited fields terminated by ','
stored as ORC;

set hive.exec.dynamic.partition.mode=nonstrict;
insert into table RoseDynamicEmployees partition(dept)
SELECT firstName, lastName, speciality, employeeNumber dept FROM RoseStaicEmpoyees;
set hive.exec.dynamic.partition.mode=strict;

create table if not exists RoseStaticEmployeesORC(firstName string, lastName string, speciality string)
partitioned by (dept string)
row format delimited fields terminated by ','
stored as ORC;

insert into table RoseStaticEmployeesORC partition(dept='csse') 
SELECT firstName,lastName,speciality, employeeNumber FROM RoseEmployees where dept='csse';
insert into table RoseStaticEmployeesORC partition(dept='ece')
SELECT firstName, lastName, speciality, employeeNumber FROM RoseEmployees where dept='ece';
insert into table RoseStaticEmplyeesORC partition(dept='admin')
SELECT firstName, lastName, speciality, employeeNumber FROM RoseEmployees where dept='admin';

SELECT count(*) FROM RoseEmployees;
SELECT count(*) FROM RoseStaticEmplyees;
SELECT count(*) FROM RoseDynamicEmplyees;
SELECT count(*) FROM RoseStaticEmployeesORC;

SHOW PARTITIONS RoseStaticEmployees;
SHOW PARTITIONS RoseDynamicEmplyoees;
SHOW PARTITIONS RoseStaticEmplyeessORC;


CREATE TABLE IF NOT EXISTS RoseDynamicEmployeesManualAdd(firstName string, lastName string,speciality string,employeeNumber string)
partitioned by (dept string)
row format delimited fileds terminated by ','
stored as ORC;

