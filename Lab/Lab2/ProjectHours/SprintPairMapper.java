package edu.rosehulman.xuez;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;


public class SprintPairMapper extends Mapper<LongWritable, Text, SprintPair, FloatWritable>{
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		String[] token = value.toString().split(",");
		String firstName = token[0];
		String lastName = token[1];
		int	sprintNum = Integer.parseInt(token[2]);
		float hours = Float.parseFloat(token[3]);
		
		SprintPair textpair = new SprintPair(firstName, lastName, sprintNum);
		context.write(textpair, new FloatWritable(hours));
		
	}

}
