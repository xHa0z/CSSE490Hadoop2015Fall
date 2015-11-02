package edu.rosehulman.xuez;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class JoinWorkMapper extends Mapper<LongWritable, Text, IntPair, Text> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] tokens = value.toString().split(",");
		String name = tokens[0] + " " + tokens[1];
		context.write(new IntPair(tokens[2], "1"), new Text(name));
	}
}
