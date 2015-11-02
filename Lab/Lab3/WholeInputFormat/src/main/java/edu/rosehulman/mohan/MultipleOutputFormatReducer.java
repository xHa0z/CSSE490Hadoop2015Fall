package edu.rosehulman.mohan;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class MultipleOutputFormatReducer extends Reducer<Text, Text, NullWritable, Text> {
    
private MultipleOutputs<NullWritable, Text> multipleOutputs;

@Override
protected void setup(Context context) throws IOException, InterruptedException {
	multipleOutputs = new MultipleOutputs<NullWritable, Text>(context);
}

@Override
protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
	for (Text value : values) {
		multipleOutputs.write(NullWritable.get(), value, key.toString());
	}
}

@Override
protected void cleanup(Context context) throws IOException, InterruptedException {
	multipleOutputs.close();
}

}