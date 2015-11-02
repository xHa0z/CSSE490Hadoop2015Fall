USE ${hiveconf:databaseName};

SELECT count(*) FROM rosedynamicemployees;
SELECT count(*) FROM rosedynamicemployeesmanualadd;

MSCK REPAIR TABLE rosedynamicemployeesmanualadd;

SELECT count(*) FROM rosedynamicemployeesmanualadd;