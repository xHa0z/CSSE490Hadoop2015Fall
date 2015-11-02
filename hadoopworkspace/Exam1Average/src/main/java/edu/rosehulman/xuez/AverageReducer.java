package edu.rosehulman.xuez;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;


public class AverageReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
	
	public void reduce(Text key,Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException{
		System.out.println("123");
		float sum = 0;
		int counter = 0;
		for(FloatWritable value : values){
			sum = sum + value.get();
			counter++;
		}
		float avg = 0;
		avg = sum / counter;
		System.out.println("redue finish");
		context.write(key, new FloatWritable(avg));
	
	}

}
