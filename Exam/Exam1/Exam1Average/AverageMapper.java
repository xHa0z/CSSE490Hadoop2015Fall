package edu.rosehulman.xuez;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;


public class AverageMapper extends Mapper<LongWritable, Text, Text, FloatWritable>{

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		System.out.println("enter map");
		String[] token = value.toString().split("\t");
		//System.out.println("1");
		String courseID = token[1];
		System.out.println(courseID);
		
		
		String courseName = token[2];
		System.out.println(courseName);
		
		
		float	courseGrade = Float.parseFloat(token[3]);
		System.out.println(courseGrade);
		//System.out.println("map");
		
		
		//TextPair textpair = new TextPair(courseID, courseName);
		//System.out.println(textpair);
		//context.write(textpair, new FloatWritable(courseGrade));
		System.out.println(courseID+"\t"+courseName);
		context.write(new Text(courseID+"\t"+courseName), new FloatWritable(courseGrade));

		System.out.println(context);
	}

}
