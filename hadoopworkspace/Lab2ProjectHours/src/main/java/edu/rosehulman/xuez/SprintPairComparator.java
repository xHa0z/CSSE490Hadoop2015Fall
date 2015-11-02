package edu.rosehulman.xuez;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableUtils;


public class SprintPairComparator extends WritableComparator{
	

	private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();
	
	public SprintPairComparator() {
	      super(SprintPair.class);
	    }

	public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {

    	try {
    		System.out.println("Inside Custom Raw Comparator");
    		int firstL1 = WritableUtils.decodeVIntSize(b1[s1]) + readVInt(b1, s1);
    		int firstL2 = WritableUtils.decodeVIntSize(b2[s2]) + readVInt(b2, s2);
    		int cmp = TEXT_COMPARATOR.compare(b1, s1, firstL1, b2, s2, firstL2);
    		if (cmp != 0) {
    			return cmp;
    		}
    		//int lastL1 = firstL1 + s1;
    		//int lastL2 = firstL2 + s2;
    		int lastL1 = WritableUtils.decodeVIntSize(b1[s1+firstL1]) + readVInt(b1, s1+firstL1);
    		int lastL2 = WritableUtils.decodeVIntSize(b2[s2+firstL2]) + readVInt(b2, s2+firstL2);
    		int cmp2 = TEXT_COMPARATOR.compare(b1, s1+firstL1,lastL1, b2, s2+firstL2, lastL2);
    		if(cmp2 != 0){
    			return cmp2;
    		}
    		return TEXT_COMPARATOR.compare(b1, s1 + firstL1 + lastL2, l1 - firstL1 - lastL1,
                            b2, s2 + firstL2 + lastL2, l2 - firstL2 - lastL2);
    	} catch (IOException e) {
    		throw new IllegalArgumentException(e);
    	}
    }

}
