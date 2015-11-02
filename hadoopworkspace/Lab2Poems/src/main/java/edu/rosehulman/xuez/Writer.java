package edu.rosehulman.xuez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

//import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.SequenceFile.Writer;



public class Writer {
	
	public static void main(String[] args) throws IOException{
		String inputpath = args[0];
		String outputpath = args[1];
		Configuration conf = new Configuration();

		Path inputPath = new Path(inputpath);
		Path outputPath = new Path(outputpath);

		FileSystem fs_input = FileSystem.get(URI.create(inputpath), conf);

		Text outputKey = new Text();
		Text outputValue = new Text();

		FileStatus[] status = fs_input.listStatus(inputPath);

		SequenceFile.Writer writer = null;

		for (int i = 0; i < status.length; i++) {

			Path currentFile = status[i].getPath();

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						fs_input.open(status[i].getPath())));
				
				outputKey.set(currentFile.getName());

				writer = SequenceFile.createWriter(conf,
						Writer.file(outputPath),
						Writer.keyClass(outputKey.getClass()),
						Writer.valueClass(outputValue.getClass()));
				String line = br.readLine();
				while (line != null) {
					outputValue.set(line);
					writer.append(outputKey, outputValue);

				}

			} finally {
				IOUtils.closeStream(writer);
			}
		}

	}
	
}
