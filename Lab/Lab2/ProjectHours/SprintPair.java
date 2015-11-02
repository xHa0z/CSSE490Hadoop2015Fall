package edu.rosehulman.xuez;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.*;

public class SprintPair implements WritableComparable<SprintPair>{
	
	private Text firstName;
	private Text lastName;
	private IntWritable sprint;
	
	public SprintPair(){
		set(new Text(), new Text(), new IntWritable());
	}
	
	public SprintPair(String firstName, String lastName, int sprint) {
		set(new Text(firstName), new Text(lastName), new IntWritable(sprint));
	}
	
	public SprintPair(Text first, Text last, IntWritable sprint){
		set(first, last, sprint);
	}
	
	public void set(Text first, Text last, IntWritable sprintNum) {
		firstName = first;
		lastName = last;
		sprint = sprintNum;
	}
	
	
	public Text getFirst(){
		return firstName;
	}
	
	public Text getLast(){
		return lastName;
	}
	
	public IntWritable getSprint(){
		return sprint;
	}
	
	public void readFields(DataInput in) throws IOException{
		firstName.readFields(in);
		lastName.readFields(in);	
		sprint.readFields(in);
	}
	
	public void write(DataOutput out) throws IOException{
		firstName.write(out);
		lastName.write(out);
		sprint.write(out);
	}
	
	public int hashCode(){
		return firstName.hashCode() * 313 + lastName.hashCode() * 163 + sprint.hashCode();
	}
	
	public boolean equals(Object o){
		if(o instanceof SprintPair){
			SprintPair tp = (SprintPair) o;
			return firstName.equals(tp.firstName) && lastName.equals(tp.lastName) && sprint.equals(tp.sprint);
		}
		return false;
	}
	
	@Override
	public String toString(){
		return firstName + "\t" + lastName + "\t" + sprint;
	}
	
	public int compareTo(SprintPair tp){
		int cmp = firstName.compareTo(tp.firstName);
		if(cmp != 0){
			return cmp;
		}
		int cmp2 = lastName.compareTo(tp.lastName);
		if(cmp2 != 0){
			return cmp2;
		}
		return sprint.compareTo(tp.sprint);
	}

	

}
