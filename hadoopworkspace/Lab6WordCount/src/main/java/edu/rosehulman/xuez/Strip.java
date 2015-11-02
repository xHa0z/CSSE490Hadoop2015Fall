package edu.rosehulman.xuez;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class Strip extends UDF {
	
	
	
	public Text evaluate(Text str){
		if(str == null){
			return null;
		}

		String resultstr = str.toString();
		
		String resultstr1 = resultstr.replaceAll(",", "");
		String resultstr2 = resultstr1.replaceAll("[(]", "");
		String resultstr3 = resultstr2.replaceAll("[)]", "");
		String resultstr4 = resultstr3.replaceAll("[.]", "");
		//resultstr = resultstr.replaceAll("'", "");
		return new Text(resultstr4);
	}


}