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

  public class UpperCase extends EvalFunc<String>
  {
	  
	  
//	  @Override
//		public List<FuncSpec> getArgToFuncMapping() throws FrontendException{
//			// We are specifying the schema that pig should expect as input.
//			// You create a list, with each element in the list specifying the schema for a tuple. 
//			List<FuncSpec> funcSpecs = new ArrayList<FuncSpec>();
//			// I am creating a new functional specification to add to my list. Each functional specification takes in
//			// its constructor the name of the class and a schema definition. Since we are specifying a field, I am
//			//constructing a field schema of type Integer and since Pig doesn't care about the name, I have it as null
//			funcSpecs.add(new FuncSpec(this.getClass().getName(), new Schema(new Schema.FieldSchema(null, DataType.INTEGER))));
//			return funcSpecs;
//		}
//	  
	  
    public String exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0)
            return null;
        try{
            String str = (String)input.get(0);
           return str.toUpperCase();
        }catch(Exception e){
            throw new IOException("Caught exception processing input row ", e);
        }
    }
  }