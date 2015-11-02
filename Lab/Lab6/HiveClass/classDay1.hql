//Create a database
create database hiveclass;

//Switch to a database
use hiveclass;

//List tables
show tables;

//Create Managed table with comma separated files stored as text
Create TABLE Students
(
sid int,
sname string,
cno string
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS TEXTFILE

//Create External table with comma separated files stored as text
Create EXTERNAL TABLE StudentsExternal
(
sid int,
sname string,
cno string
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION '/user/root/StudentsExternal'

//Load data into a managed table
LOAD DATA INPATH '/tmp/hiveInput/students.txt' OVERWRITE INTO TABLE Students;

//Get info about tables
Describe Students;
Describe StudentsExternal

//Delete the contents of a table, but retain its schema
Truncate Table Students;
Truncate Table StudentsExternal;

//Drop the table;
Drop table Students;
Drop table StudentsExternal;

//drop the database;
drop database hiveclass;

Queries
------
//Create database
create database hiveclass;

//Switch to hiveclass
use hiveclass

//Create Managed table with comma separated files stored as text
Create TABLE Students
(
sid int,
sname string,
cno string
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS TEXTFILE

//Create Managed table with comma separated files stored as text
Create TABLE Courses
(
cno string,
cname string
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS TEXTFILE

//Load data into a managed table
LOAD DATA INPATH '/tmp/hiveInput/students.txt' OVERWRITE INTO TABLE Students;
//Load data into a managed table
LOAD DATA INPATH '/tmp/hiveInput/courses.txt' OVERWRITE INTO TABLE Courses;

//Select all data
Select * from Students;
Select * from Courses;

//Limit the number of results
Select * from students limit 2;
//Retrieve Specific columns
Select sid, sname from Students;
Select cname from Courses;

//Retrieve matching tuples
Select sid,sname from Students where cno='CSSE333';
Select sid,sname,cno from Students where sid=1;

//order by - single reducer
select * from students order by cno;
//sort by - partial sorty by each reducer
select * from students sort by cno;

//Let us explicitly set the number of reudcer to 2
set mapreduce.job.reduces=2
//Now let us see the order by
select * from students sort by cno;
//sort within each dept by cno, set partition key as cno
select * from students distribute by cno sort by sid;
//sort within each dept by cno, set partition key as cno
select * from students distribute by cno sort by cno;
select * from students cluster by cno;


//Group by
Select cno, count(*) from Students group by cno;
Select cno, count(*) from Students group by cno having count(*) >=2;


Temperature:
------------
CREATE TABLE IF NOT EXISTS temperature
(
year int,
temperature float,
quality int
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE

LOAD DATA INPATH '/tmp/hiveInput/tempInput.txt' overwrite INTO table temperature;

select * from temperature;

select year, max(temperature) from temperature group by year;

Joins
-----
Select * from Students S join Courses C on (s.cno = c.cno)
Select * from Students S LEFT join Courses C on (s.cno = c.cno)
//same as regular join
Select * from Students S CROSS join Courses C on (s.cno = c.cno)
//cross product
Select * from Students S CROSS join Courses C 
//Semi join - return only rows from left table that match condition
Select * from students s LEFT semi join courses c on (s.cno = c.cno)
