package com.rosehulman.mohan;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


//The mapper has to extend the Mapper class and you have to provide four
//Formal parameters - The first two are the types of the input key and value
// and the second two are the types of the output key and value

public class MaxTemperatureMapper extends Mapper<LongWritable,Text,Text,IntWritable>{

private static final int MISSING = 9999;

@Override
public void map(LongWritable key, Text value, Context context) throws IOException,InterruptedException{

	String line = value.toString();
	//Reading just the data we are interested in
	String year = line.substring(15,19);
	int airTemperature;

	if(line.charAt(87) =='+'){
		context.getCounter(MaxTemperature.Temperature.POSITIVE).increment(1);
		airTemperature = Integer.parseInt(line.substring(88,92));
	}
	else{
		context.getCounter(MaxTemperature.Temperature.NEGATIVE).increment(1);
		airTemperature = Integer.parseInt(line.substring(87,92));
	}

	String quality = line.substring(92,93);

	if (airTemperature!=MISSING && quality.matches("[01459]")){
		//write output key value
		context.getCounter("TemperatureQuality", quality).increment(1);
		context.write(new Text(year), new IntWritable(airTemperature));
	}
}

}
