package edu.rosehulman.mohan;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class GetLength extends UDF{
	
	public IntWritable evaluate(final Text s) {
	    if (s == null) { return null; }
	    return new IntWritable(s.toString().length());
	  }
	
}