package edu.rosehulman.xuez;


import java.io.IOException;
import java.util.Iterator;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;

public class HitRatio extends EvalFunc<Double> {
	@Override
	public Double exec(Tuple input) throws IOException {
		try {
			double total = 0;
			double hitCount = 0;
			double errorCount = 0;
			DataBag bag = (DataBag) input.get(0);
			Iterator it = bag.iterator();
			while (it.hasNext()) {
				Tuple t = (Tuple) it.next();
				// Don't count nulls or empty tuples
				if (t != null && t.size() > 0 && t.get(0) != null) {
					total++;
					if (t.get(0).equals("Hit"))
						hitCount++;
					if (t.get(0).equals("Error"))
						errorCount++;
				}
			}
			return (hitCount / total);
		} catch (Exception e) {
			throw new IOException("Something bad happened!", e);
		}
	}
}


