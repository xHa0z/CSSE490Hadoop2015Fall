package edu.rosehulman.xuez;


import java.io.IOException;

//import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FindingCommonFriendMapper 
		extends Mapper<LongWritable, Text, Text, Text>{
		
	
	
	@Override
	public void map(LongWritable Key, Text value, Context context)
		throws IOException, InterruptedException {
		
		String line = value.toString();
		
		String[] name = line.split(",");
		
		int size = name.length;
		String key = name[0];
		
		for(int i = 1; i < size; i++){
			String resultKey = "";
			String resultValue = "";
			
			if(key.compareTo(name[i])>0){//compare the first element in the line and rest of list
				resultKey = key + "," + name[i];
			}
			else{
				resultKey = name[i] + "," + key;
			}
			
			for(int j = 1; j < size; i++){
				if(j != i){
					resultValue = resultValue + "," + name[j];
				}
			}
			
			context.write(new Text(resultKey), new Text(resultValue));
		}//end outer for loop
		
		
		
		
		
	}
		
	

}
