package edu.rosehulman.xuez;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class TextPairMapper extends Mapper<LongWritable, Text, TextPair, IntWritable>{
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		String[] token = value.toString().split(",");
		String firstName = token[0];
		String lastName = token[1];
		
		TextPair textpair = new TextPair(firstName, lastName);
		context.write(textpair, new IntWritable(1));
		
	}

}
