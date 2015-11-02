Create TABLE StaticStudents
(
sid int,
sname string
)
Partitioned by (cno string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS TEXTFILE

Create TABLE StaticStudentsORC
(
sid int,
sname string
)
Partitioned by (cno string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS orc

Create TABLE DynamicstudentsORC
(
sid int,
sname string
)
Partitioned by (cno string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS orc

//Loading to a normal table stored as orc using static partitioning
LOAD DATA INPATH '/tmp/hiveInput/students33.txt' INTO Table StaticStudents Partition(cno = 'csse33')
LOAD DATA INPATH '/tmp/hiveInput/students333.txt' INTO Table StaticStudents Partition(cno = 'csse333')

//getting a list of partitions
show partitions staticstudents;

//To Delete a partition
alter table staticstudents drop partition (cno='csse33');


//Manual loading of static partitions
hadoop fs -mkdir /apps/hive/warehouse/hiveclass.db/staticstudents/cno=csse371
hadoop fs -mkdir /apps/hive/warehouse/hiveclass.db/staticstudents/cno=csse490

hadoop fs -put students371.txt /apps/hive/warehouse/hiveclass.db/staticstudents/cno=csse371
hadoop fs -put students490.txt /apps/hive/warehouse/hiveclass.db/staticstudents/cno=csse490

//Let us do a select * and check the number of partitions
Select * from staticstudents;
show partitions staticstudents;

//Even though the directories are there, hive doesn't recognize the data and the partitions, becuase they are not part of hte metastore
alter table staticstudents add partition (cno='csse371');

//how do you get hive to recognize all the directories that might be there
msck repair table staticstudents

//Other ways to load data into a static table stored manually.
//Let us drop one of the partitions
alter table staticstudents drop partition (cno='csse33');

//We insert from the students table in hiveclass db, selecting the rows that need to be inserted. Note that we are only getting the 2 columns in the select statement
//The third column is automatically added as the cno partition
insert into table staticstudents partition(cno='csse33') select sid, sname from hiveclass.students where cno='CSSE33'


//Inserting into statically partitioned ORC table. Have to specify each partition explicitly
 insert into table staticstudentsorc partition(cno='csse33') select sid,sname from staticstudents where cno='csse33'
 insert into table staticstudentsorc partition(cno='csse333') select sid,sname from staticstudents where cno='csse333'
 insert into table staticstudentsorc partition(cno='csse371') select sid,sname from staticstudents where cno='csse371'
 insert into table staticstudentsorc partition(cno='csse490') select sid,sname from staticstudents where cno='csse490'

//Verify the number of partitions and contents
show partitions staticstudentsorc;
select * from staticstudentsorc

//Using dynamic partition
Set hive.exec.dynamic.partition.mode=nonstrict;
insert into table dynamicstudentsorc partition(cno) select sid,sname,cno from staticstudents;


//Verify the number of partitions and contents
show partitions dynamicstudentsorc;
select * from dynamicstudentsorc


//User Defined Functions
DROP FUNCTION IF EXISTS udfLower;
DROP FUNCTION IF EXISTS udfLength;
CREATE FUNCTION udfLower AS 'edu.rosehulman.mohan.ToLower' USING JAR 'hdfs:///tmp/input/myHiveUDF.jar';
CREATE FUNCTION udfLength AS 'edu.rosehulman.mohan.GetLength' USING JAR 'hdfs:///tmp/input/myHiveUDF.jar';
select udfLower(sname), udfLower(cno), udfLength(cno) from staticstudentsorc;   
