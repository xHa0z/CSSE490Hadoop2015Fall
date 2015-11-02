package edu.rosehulman.xuez;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class CustomInputFormatMapper extends
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
		context.write(filenameKey, value);
	}
}
