package edu.rosehulman.xuez;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;


public class SprintPairReducer extends Reducer<SprintPair, FloatWritable, SprintPair, FloatWritable> {
	
	public void reduce(SprintPair key,Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException{
		float sum = 0;
		for(FloatWritable value : values){
			sum = sum + value.get();
		}
		context.write(key, new FloatWritable(sum));
	
	}

}
