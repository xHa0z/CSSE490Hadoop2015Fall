package com.rose.hadoop;

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

	System.out.println("Inside Mapper");
	String line = value.toString();
	//Reading just the data we are interested in
	String year = line.substring(15,19);
	int airTemperature;

	if(line.charAt(87) =='+'){
		airTemperature = Integer.parseInt(line.substring(88,92));
	}
	else{
		airTemperature = Integer.parseInt(line.substring(87,92));
	}

	String quality = line.substring(92,93);

	if (airTemperature!=MISSING && quality.matches("[01459]")){
		//write output key value
		System.out.println(year);
		System.out.println(airTemperature);
		context.write(new Text(year), new IntWritable(airTemperature));
	}
}

}
