#!/bin/bash
sqoop export --connect jdbc:mysql://$1/sqooptest --username root -m 1 --table EmployeesExportData --export-dir /tmp/sqoop/Employees --input-fields-terminated-by '\t' --input-null-string 'This is a NULL String' --input-null-non-string '\\N'

# echo "2.1 done! 1111111111111111111111111111111"

sqoop export --connect jdbc:mysql://$1/sqooptest --username root -m 1 --table EmployeesExportData --export-dir /tmp/sqoop/Employees --input-fields-terminated-by '\t' --input-null-string 'This is a NULL String' --input-null-non-string '\\N' --update-key eid --update-mode allowinsert

# echo "2.2 done! 2222222222222222222222222222222"

