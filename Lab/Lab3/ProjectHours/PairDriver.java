package edu.rosehulman.xuez;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.*;

public class PairDriver extends Configured implements Tool {

	public static class KeyPartitioner extends Partitioner<IntPair, Text> {
		@Override
		public int getPartition(IntPair key, Text value, int numPartitions) {
			return (key.getSprintNumber().hashCode() & Integer.MAX_VALUE)
					% numPartitions;
		}
	}

	public int run(String[] args) throws Exception {
		Job job = new Job(getConf(), "Join weather records with station names");
		job.setJarByClass(getClass());

		Path workDetailsPath = new Path(args[0]);
		Path sprintDetailsPath = new Path(args[1]);
		Path outputPath = new Path(args[2]);

		MultipleInputs.addInputPath(job, workDetailsPath,
				TextInputFormat.class, JoinWorkMapper.class);
		MultipleInputs.addInputPath(job, sprintDetailsPath,
				TextInputFormat.class, JoinSprintMapper.class);
		FileOutputFormat.setOutputPath(job, outputPath);

		job.setPartitionerClass(KeyPartitioner.class);
		job.setGroupingComparatorClass(FirstComparator.class);

		job.setMapOutputKeyClass(IntPair.class);

		job.setReducerClass(JoinReducer.class);

		job.setOutputKeyClass(IntWritable.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new PairDriver(), args);
		System.exit(exitCode);
	}
}
