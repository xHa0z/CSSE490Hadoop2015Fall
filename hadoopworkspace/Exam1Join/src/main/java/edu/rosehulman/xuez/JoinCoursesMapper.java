package edu.rosehulman.xuez;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class JoinCoursesMapper extends Mapper<LongWritable, Text, TextPair, Text> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] tokens = value.toString().split(",");
		context.write(new TextPair(tokens[0], "0"), new Text(tokens[1]));
	}

}
