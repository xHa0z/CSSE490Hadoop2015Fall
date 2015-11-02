package edu.rosehulman.xuez;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class JoinReducer extends Reducer<IntPair, Text, IntWritable, Text> {
	@Override
	protected void reduce(IntPair key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		Iterator<Text> iter = values.iterator();
	    Text sprintName = new Text(iter.next());
	    while (iter.hasNext()) {
	      Text record = iter.next();
	      Text outValue = new Text(sprintName.toString() + "\t" + record.toString());
	      context.write(key.getSprintNumber(), outValue);
	    }
	}
}
