package edu.rosehulman.xuez;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class AverageDriver {
	
	public static void main(String[] args) throws Exception {
		if(args.length != 2){
			System.err.println("Usage: MaxTemperature <input path> <output path>");
			System.exit(-1);
		}
		
		Job job = new Job();
		job.setJarByClass(AverageDriver.class);
		job.setJobName("Agerage grade");
		System.out.println("start");
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setMapperClass(AverageMapper.class);
		job.setReducerClass(AverageReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}

}




//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.conf.Configured;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.FloatWritable;
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.hadoop.util.Tool;
//import org.apache.hadoop.util.ToolRunner;
//
//
//
//public class AverageDriver extends Configured implements Tool{
//	
//	public int run(String[] args) throws Exception {
//		// The configuration can be accessed by using the getConf() method associated with
//		//the configurable interface which we implemented by extending the Configured class.
//		Configuration conf = getConf();
//		Job job = Job.getInstance(conf, "Custom Comparator");
//	     
//	    job.setJarByClass(AverageDriver.class);
//	    
//	    FileInputFormat.addInputPath(job, new Path(args[0]));
//	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
//	    
//	    job.setMapOutputKeyClass(TextPair.class);
//	    job.setMapOutputValueClass(FloatWritable.class);
//	     
//	    job.setOutputKeyClass(TextPair.class);
//	    job.setOutputValueClass(FloatWritable.class);
//	     
//	    job.setMapperClass(AverageMapper.class);
//	    job.setReducerClass(AverageReducer.class);
//	     
//	    job.waitForCompletion(true);	     
//	    return 0;
//	}
//	
//	public static void main(String[] args) throws Exception{		
//		
//		//Rather than calling the tool's run method directly, we call the Toolrunner.run static method
//		//which creates a Configuration object and then automatically calls the run method associated with 
//		//Tool. It also uses the GenericConfigurationParser to pick up any special options specified using 
//		//the command line.
//		int exitCode = ToolRunner.run(new AverageDriver(), args);
//		System.exit(exitCode);
//	}
//
//}
