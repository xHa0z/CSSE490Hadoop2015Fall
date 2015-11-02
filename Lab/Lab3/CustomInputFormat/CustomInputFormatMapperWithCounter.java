package edu.rosehulman.xuez;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class CustomInputFormatMapperWithCounter extends
		Mapper<NullWritable, IntWritable, Text, IntWritable> {
	private Text filenameKey;

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		InputSplit split = context.getInputSplit();
		Path path = ((FileSplit) split).getPath();
		filenameKey = new Text(path.toString());
		
	}

	@Override
	public void map(NullWritable key, IntWritable value, Context context)
			throws IOException, InterruptedException {
		//System.out.println(value);
		if(value.get() == 2){
			context.getCounter(CustomInputFormatWithCounter.WordCount.EqualToTwo).increment(1);
			//System.out.println("==");
		}
		if(value.get() < 2){
			context.getCounter(CustomInputFormatWithCounter.WordCount.LessThanTwo).increment(1);
			//System.out.println("<=");
		}
		if(value.get() > 2){
			context.getCounter(CustomInputFormatWithCounter.WordCount.GreaterThanTwo).increment(1);
			//System.out.println(">=");
		}
		context.write(filenameKey, value);
		
	}
}
