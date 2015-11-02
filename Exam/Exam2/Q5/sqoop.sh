sqoop export --connect jdbc:mysql://$1/exam --username root --table examData --hcatalog-database exam --hcatalog-table examData  --hive-partition-key username --hive-partition-value $2
