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

public class Name extends EvalFunc<String>{

	@Override
	public String exec(Tuple input) throws IOException {
		if(input == null || input.size()==0){
			return null;
		}
		try{
			String fname = input.get(0).toString();
			String lname = input.get(1).toString();
			String name = fname + " " + lname;
			return name;
			
		}
		catch(Exception e){
			throw new IOException("Caught exception processing input row ", e);
		}
		
	}

}
