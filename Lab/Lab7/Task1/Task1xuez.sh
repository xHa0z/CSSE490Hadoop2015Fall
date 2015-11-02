#!/bin/bash
sqoop import --connect jdbc:mysql://$1/sqooptest --username root --table Employees --split-by eid --target-dir /tmp/sqoopOutput

#echo "1.1 done! 1111111111111111111111111111111111"

sqoop import --connect jdbc:mysql://$1/sqooptest --username root --table Employees -m 1 --target-dir /tmp/sqoopMapOutput

#echo "1.2 done! 222222222222222222222222222222222"

sqoop import --connect jdbc:mysql://$1/sqooptest --username root -m 2 --table Employees --split-by eid --target-dir /tmp/sqoopSeqOutput --as-sequencefile

#echo "1.3 done! 333333333333333333333333333333333"

sqoop import --connect jdbc:mysql://$1/sqooptest --username root -m 2 --table Employees --split-by eid --warehouse-dir /tmp/sqoop

#echo "1.4 done! 444444444444444444444444444444444"

sqoop import --connect jdbc:mysql://$1/sqooptest --username root --delete-target-dir -m 2 --table Employees --split-by eid --warehouse-dir /tmp/sqoop --fields-terminated-by '\t' --null-string 'This is a NULL String' --null-non-string '\\N'

#echo "1.5 done! 555555555555555555555555555555555"

hive -e 'CREATE database IF NOT EXISTS sqooptest'

#echo "1.6 done! 666666666666666666666666666666666"

sqoop import --connect jdbc:mysql://$1/sqooptest --username root -m 2 --table Employees --split-by eid --hive-import --create-hive-table --hive-table sqooptest.Employees

#echo "1.7 done! 777777777777777777777777777777777"

sqoop import --connect jdbc:mysql://$1/sqooptest --username root -m 2 --table Employees --split-by eid --hive-import --create-hive-table --hive-table sqooptest.Employees --fields-terminated-by '\t' --null-string 'This is a NULL String' --null-non-string '\\N'

#echo "1.8 done! 8888888888888888888888888888888888"

sqoop import-all-tables --connect jdbc:mysql://$1/sqooptest --username root -m 1 --warehouse-dir /tmp/sqoopAll/

#echo "1.9 done! 999999999999999999999999999999999999"




