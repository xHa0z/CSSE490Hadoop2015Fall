package edu.rosehulman.xuez;
import java.io.IOException;
import java.util.Iterator;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;

public class ErrorRatio extends EvalFunc<Double> {
	@Override
	public Double exec(Tuple input) throws IOException {
		try {
			double total = 0;
			double hitCount = 0;
			double errorCount = 0;
			DataBag bag = (DataBag) input.get(0);
			Iterator iter = bag.iterator();
			while (iter.hasNext()) {
				Tuple t = (Tuple) iter.next();
				if (t != null && t.size() > 0 && t.get(0) != null) {
					total++;
					if (t.get(0).equals("Hit"))
						hitCount++;
					if (t.get(0).equals("Error"))
						errorCount++;
				}
			}
			return (errorCount / total);
		} catch (Exception e) {
			throw new IOException("Something bad happened!", e);
		}
	}
}


