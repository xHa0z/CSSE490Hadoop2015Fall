package edu.rosehulman.xuez;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class JoinReducer  extends Reducer<TextPair, Text, Text, Text> {
	@Override
	protected void reduce(TextPair key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		Iterator<Text> iter = values.iterator();
	    Text courseName = new Text(iter.next());
	    while (iter.hasNext()) {
	      Text record = iter.next();
	      String[] tokens = record.toString().split(",");
	      Text outKey = new Text(tokens[0]);
	      Text outValue = new Text(key.getFirst().toString() + "\t" + courseName.toString() + "\t" + tokens[1]);
	      context.write(outKey, outValue);
	    }
	}

}
