package edu.rosehulman.xuez;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

//vv WholeFileRecordReader
class WholeFileRecordReader extends RecordReader<NullWritable, IntWritable> {
  
  private FileSplit fileSplit;
  private Configuration conf;
  private IntWritable value = new IntWritable();
  private boolean processed = false;

  @Override
  public void initialize(InputSplit split, TaskAttemptContext context)
      throws IOException, InterruptedException {
    this.fileSplit = (FileSplit) split;
    this.conf = context.getConfiguration();
  }
  
  @Override
  public boolean nextKeyValue() throws IOException, InterruptedException {
    if (!processed) {
      byte[] contents = new byte[(int) fileSplit.getLength()];
      Path file = fileSplit.getPath();
      FileSystem fs = file.getFileSystem(conf);
      FSDataInputStream in = null;
      try {
    	  
        in = fs.open(file);
       
        
        IOUtils.readFully(in, contents, 0, contents.length);
        
        String keyWord = conf.get("keyWord");
        byte[] keyWordByte = keyWord.getBytes(Charset.forName("UTF-8"));
        int count = 0;
        for(int i = 0; i < contents.length-keyWordByte.length+1; i++){
        	int match = 0;
        	for(int j = 0; j < keyWordByte.length; j++){
        		if(contents[i+j] == keyWordByte[j]){
        			
        			match++;
        		}//end if        		
        	}//end inner for
        	if(match == keyWordByte.length){
        		count++;
        	}
        	
        }
        value.set(count);
      } finally {
        IOUtils.closeStream(in);
      }
      processed = true;
      return true;
    }
    return false;
  }
  
  @Override
  public NullWritable getCurrentKey() throws IOException, InterruptedException {
    return NullWritable.get();
  }

  @Override
  public IntWritable getCurrentValue() throws IOException,
      InterruptedException {
    return value;
  }

  @Override
  public float getProgress() throws IOException {
    return processed ? 1.0f : 0.0f;
  }

  @Override
  public void close() throws IOException {
    // do nothing
  }
}
