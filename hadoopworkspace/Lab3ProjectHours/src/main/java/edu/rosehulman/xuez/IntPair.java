package edu.rosehulman.xuez;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.*;

public class IntPair implements WritableComparable<IntPair> {

	private IntWritable sprintNumber;
	private IntWritable order;

	public IntPair() {
		set(new IntWritable(), new IntWritable());
	}

	public IntPair(String first, String last) {
		set(new IntWritable(Integer.parseInt(first)),
				new IntWritable(Integer.parseInt(last)));
	}

	public void set(IntWritable first, IntWritable last) {
		sprintNumber = first;
		order = last;
	}

	public IntWritable getSprintNumber() {
		return sprintNumber;
	}

	public IntWritable getOrder() {
		return order;
	}

	public void readFields(DataInput in) throws IOException {
		sprintNumber.readFields(in);
		order.readFields(in);
	}

	public void write(DataOutput out) throws IOException {
		sprintNumber.write(out);
		order.write(out);
	}

	public int hashCode() {
		return sprintNumber.hashCode() * 163 + order.hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof IntPair) {
			IntPair tp = (IntPair) o;
			return sprintNumber.equals(tp.sprintNumber)
					&& order.equals(tp.order);
		}
		return false;
	}

	@Override
	public String toString() {
		return sprintNumber + "\t" + order;
	}

	public int compareTo(IntPair tp) {
		int cmp = sprintNumber.compareTo(tp.sprintNumber);
		if (cmp != 0) {
			return cmp;
		}
		return order.compareTo(tp.order);
	}

}
