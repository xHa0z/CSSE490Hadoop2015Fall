package edu.rosehulman.xuez;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class JoinSprintMapper extends Mapper<LongWritable, Text, IntPair, Text> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] tokens = value.toString().split(",");
		context.write(new IntPair(tokens[0], "0"), new Text(tokens[1]));
	}
}
