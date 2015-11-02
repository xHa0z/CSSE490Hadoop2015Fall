REGISTER 'hdfs:///tmp/Exams/xuezUDF.jar';
DEFINE name edu.rosehulman.xuez.Name();
DEFINE letter edu.rosehulman.xuez.Grade();

gradeori = LOAD '$gradepath' using PigStorage(',') AS (fname:chararray, lname:chararray, course:chararray,grade:float);

coursename = LOAD '$coursepath' using PigStorage(',') AS (cnum:chararray,cname:chararray);


gradefilter = FILTER gradeori BY grade<=90;

gradeudf = FOREACH gradefilter GENERATE name(fname,lname) as fullname, course as course, letter(grade) as lettergrade;

result = JOIN gradeudf by course, coursename by cnum;

resultformat = FOREACH result GENERATE gradeudf::fullname, gradeudf::course, coursename::cname, gradeudf::lettergrade;

resultorder = ORDER resultformat BY course ASC;

STORE resultorder INTO '$outpath/$username' USING PigStorage('\t');
