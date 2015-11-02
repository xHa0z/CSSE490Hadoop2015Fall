records = LOAD 'tempInput.txt' using PigStorage('\t') AS (year:charray, temperature:int, quality:int);
frecords = FILTER records by temperature != 9999 and (quality == 0 OR quality == 1 OR quality == 4 OR quality == 5 OR quality == 9);
grecords = GROUP frecords by year;
temp FOREACH grecords GENERATE group, MIN(frecords.temperature) as MinTemp;
dump
STORE temp into '/tmp/PigTask1MinOut' using PigStorage(','); 
