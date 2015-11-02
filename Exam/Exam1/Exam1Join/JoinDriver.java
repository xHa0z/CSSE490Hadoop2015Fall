package edu.rosehulman.xuez;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.*;

// vv JoinRecordWithStationName
public class JoinDriver extends Configured implements Tool {
  
  public static class KeyPartitioner extends Partitioner<TextPair, Text> {
    @Override
    public int getPartition(TextPair key, Text value, int numPartitions) {
      return (key.getFirst().hashCode() & Integer.MAX_VALUE) % numPartitions;
    }
  }
  
  public int run(String[] args) throws Exception {
	Job job = Job.getInstance(getConf(), "Custom Grades and Courses Join");
    job.setJarByClass(getClass());
    
    Path gradeInputPath = new Path(args[0]);
    Path coursesInputPath = new Path(args[1]);
    Path outputPath = new Path(args[2]);
    
    MultipleInputs.addInputPath(job, gradeInputPath,
        TextInputFormat.class, JoinGradesMapper.class);
    MultipleInputs.addInputPath(job, coursesInputPath,
        TextInputFormat.class, JoinCoursesMapper.class);
    FileOutputFormat.setOutputPath(job, outputPath);
    
    job.setPartitionerClass(KeyPartitioner.class);
   job.setGroupingComparatorClass(FirstComparator.class);
    
    job.setMapOutputKeyClass(TextPair.class);
    
    job.setReducerClass(JoinReducer.class);

    job.setOutputKeyClass(Text.class);
    
    return job.waitForCompletion(true) ? 0 : 1;
  }
  
  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new JoinDriver(), args);
    System.exit(exitCode);
  }
}