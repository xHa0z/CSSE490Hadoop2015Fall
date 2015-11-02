package edu.rosehulman.xuez;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.*;


public class TextPair implements WritableComparable<TextPair>{

	private Text firstName;
	private Text lastName;
	
	public TextPair(){
		set(new Text(), new Text());
	}
	
	public TextPair(String firstName, String lastName) {
		set(new Text(firstName), new Text(lastName));
	}
	
	public TextPair(Text first, Text last){
		set(first, last);
		
	}
	
	public void set(Text first, Text last) {
		firstName = first;
		lastName = last;
	}
	
	
	public Text getFirst(){
		return firstName;
	}
	
	public Text getLast(){
		return lastName;
	}
	
	public void readFields(DataInput in) throws IOException{
		firstName.readFields(in);
		lastName.readFields(in);		
	}
	
	public void write(DataOutput out) throws IOException{
		firstName.write(out);
		lastName.write(out);
	}
	
	public int hashCode(){
		return firstName.hashCode() * 163 + lastName.hashCode();
	}
	
	public boolean equals(Object o){
		if(o instanceof TextPair){
			TextPair tp = (TextPair) o;
			return firstName.equals(tp.firstName) && lastName.equals(tp.lastName);
		}
		return false;
	}
	
	@Override
	public String toString(){
		return firstName + "\t" + lastName;
	}
	
	public int compareTo(TextPair tp){
		int cmp = firstName.compareTo(tp.firstName);
		if(cmp != 0){
			return cmp;
		}
		return lastName.compareTo(tp.lastName);
	}

	


	

}
