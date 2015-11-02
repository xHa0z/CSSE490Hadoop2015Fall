package edu.rosehulman.xuez;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class Upper extends UDF{
	
	public Text evaluate(final Text s) {
	    if (s == null) { return null; }
	    return new Text(s.toString().toUpperCase());
	  }
	
}