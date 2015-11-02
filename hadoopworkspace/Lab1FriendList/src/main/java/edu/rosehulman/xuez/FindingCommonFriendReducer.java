package edu.rosehulman.xuez;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.google.common.collect.Iterables;

public class FindingCommonFriendReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
		throws IOException, InterruptedException {
		
		
		Text firstText = Iterables.get(values, 0);
		String firstString = firstText.toString();
		String resultValue = "";
		for (int i=0;i<firstString.length();i++){
			char c = firstString.charAt(i);
			boolean result = true;
			for (Text value: values){
				String val = value.toString();
				if (val.indexOf(c)<0){
					result = false;
				}
			}
			if(result){
				resultValue += c;
			}
		}
		
		context.write(key, new Text(resultValue));

	}
}
