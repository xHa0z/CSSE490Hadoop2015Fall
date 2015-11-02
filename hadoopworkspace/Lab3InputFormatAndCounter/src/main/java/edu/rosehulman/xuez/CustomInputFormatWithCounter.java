package edu.rosehulman.xuez;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.TaskCounter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class CustomInputFormatWithCounter extends Configured implements Tool {

	enum WordCount {
		EqualToTwo, LessThanTwo, GreaterThanTwo
	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		conf.set("keyWord", args[2]);
		Job job = new Job(conf, "Custom Input Format");
		job.setJarByClass(CustomInputFormatWithCounter.class);

		WholeFileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setInputFormatClass(WholeFileInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		job.setMapperClass(CustomInputFormatMapperWithCounter.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.waitForCompletion(true);
		Counters counter = job.getCounters();
		long equalToTwo = counter.findCounter(
				CustomInputFormatWithCounter.WordCount.EqualToTwo).getValue();
		System.out.println("Number of files have two searching word: "
				+ equalToTwo);
		long greaterThanTwo = counter.findCounter(
				CustomInputFormatWithCounter.WordCount.GreaterThanTwo)
				.getValue();
		System.out
				.println("Number of files have more than two searching word: "
						+ greaterThanTwo);
		long lessThanTwo = counter.findCounter(
				CustomInputFormatWithCounter.WordCount.LessThanTwo).getValue();
		System.out
				.println("Number of files have less than two searching word: "
						+ lessThanTwo);
		long total = counter.findCounter(TaskCounter.MAP_OUTPUT_BYTES)
				.getValue();
		System.out.println("Number of total map records : " + total);
		return 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner
				.run(new CustomInputFormatWithCounter(), args);
		System.exit(exitCode);
	}
}
