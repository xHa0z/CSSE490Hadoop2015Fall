REGISTER 'hdfs:///tmp/PigLabTask3/Quality.jar';
DEFINE checkQuality edu.rosehulman.xuez.Quality();

records = LOAD '$INPUT' using PigStorage('\t') AS (year:chararray, temperature:int, quality:int);
frecords = FILTER records by temperature!=9999 and checkQuality(quality);
grecords = GROUP frecords by year;
temp = FOREACH grecords GENERATE group, MIN(frecords.temperature) as MinTemp;
STORE temp into '$OUTPUT' using PigStorage(',');
