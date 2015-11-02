--%declare INPUT 'hdfs:///tmp/PigLabTask2/testFile.txt';
--%declare OUTPUT 'hdfs:///tmp/PigLabTask2Out/';

REGISTER 'hdfs:///tmp/PigLabTask2/Upper.jar';
DEFINE upper edu.rosehulman.xuez.UpperCase();

A = LOAD '$INPUT' as (f1 : chararray);
X = FOREACH A generate FLATTEN(TOKENIZE(f1, '[., ]')) as x;
UX = FOREACH X generate upper(x) as ux;
wgroup = GROUP UX by ux;
wcount = FOREACH wgroup GENERATE group, COUNT(UX);

STORE wcount into '$OUTPUT' using PigStorage(',');

