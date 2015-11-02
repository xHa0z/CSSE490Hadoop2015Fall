package edu.rosehulman.xuez;


import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class JoinGradesMapper extends Mapper<LongWritable, Text, TextPair, Text> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] tokens = value.toString().split(",");
		Text outValue = new Text(tokens[0] + "," + tokens[2]);
		context.write(new TextPair(tokens[1], "1"), outValue);
	}

}
