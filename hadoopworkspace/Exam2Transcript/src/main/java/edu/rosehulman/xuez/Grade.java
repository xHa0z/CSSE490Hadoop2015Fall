package edu.rosehulman.xuez;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.apache.pig.FuncSpec;

public class Grade extends EvalFunc<String>{

	@Override
	public String exec(Tuple input) throws IOException {
		
		if(input == null || input.size()==0){
			return null;
		}
		try{
			String letter = new String();
			float grade = (Float) input.get(0);
			if(grade <= 60){
				letter = "F";
			}
			else if(grade <= 70){
				letter = "D";
			}
			else if(grade <= 80){
				letter = "C";
			}
			else if(grade < 90){
				letter = "B";
			}
			else{
				letter = "A";
			}
			return letter;
		}
		catch(Exception e){
			throw new IOException("Caught exception processing input row ", e);
		}
		
	}

}
