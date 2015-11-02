package edu.rosehulman.xuez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import com.google.gson.FieldNamingStrategy;


public class Writer {
	
	public static void main(String[] args) throws IOException{
		String inputPath = args[0];
		String outputPath = args[1];
		Configuration conf = new Configuration();
		
		FileSystem fs_in = FileSystem.get(URI.create(inputPath), conf);
		FileSystem fs_out = FileSystem.get(URI.create(outputPath), conf);
		
		Path inputpath = new Path(inputPath);
		Path outputpath = new Path(outputPath);
		FileStatus[] status = fs_in.listStatus(inputpath);
		

		IntWritable key = new IntWritable();
		Text value = new Text();
		
		SequenceFile.Writer writer = null;
		
		try {
			writer = SequenceFile.createWriter(fs_out, conf, outputpath, key.getClass(), value.getClass());
			
			String line = "";
			int j = 0;
			for (int i = 0; i < status.length; i++) {
				BufferedReader br=new BufferedReader(new InputStreamReader(fs_in.open(status[i].getPath())));
				line = br.readLine();
				while (line != null) {
					key.set(100 - j);
					value.set(line);
					System.out.printf("[%s]\t%s\t%s\n", writer.getLength(), key, value);
					writer.append(key, value);
					line = br.readLine();
					j++;
				}
			}
		} finally {
			IOUtils.closeStream(writer);
		}
	}

	
}
