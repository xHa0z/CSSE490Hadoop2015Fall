package edu.rosehulman.mohan;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.interceptor.*;


public class EmailInterceptor implements Interceptor{

	private String fileName;
	
	public EmailInterceptor(){
		
	}
	
	  public void initialize() {
	    // no-op
	  }

	  /**
	   * Modifies events in-place.
	   */
	  
	  public Event intercept(Event event) {
		
		try{
	    String eventBody = new String(event.getBody(),"UTF-8");
	    if(eventBody.charAt(0)=='#'){
	    	return null;
	    }
	    String newEventBody = eventBody.replaceAll("@.*?,",",");
	    Map<String, String> map = event.getHeaders();
	    String completeFileName = map.get("file");
	    fileName = completeFileName.split("_")[1];
	    
	    //Use String builder to write the new Event.
	    StringBuilder builder = new StringBuilder();
	    builder.append(fileName).append(",").append(newEventBody);
	    Event e = EventBuilder.withBody(builder.toString(), Charset.forName("UTF-8"));
	    return e;
		}
		catch(Exception exp){ return null;}
	  }

	  /**
	   * Delegates to {@link #intercept(Event)} in a loop.
	   * @param events
	   * @return
	   */
	  
	  public List<Event> intercept(List<Event> events) {
		  List<Event> eventList = new ArrayList<Event>();
		  for (Event event : events) {
	      Event outEvent = intercept(event);
	      if(outEvent !=null){
	    	  eventList.add(outEvent);
	      }
	    }
	    return eventList;
	  }

	  
	  public void close() {
	    // no-op
	  }

	  public static class Builder implements Interceptor.Builder {

		    
		    
		    public Interceptor build() {
		      return new EmailInterceptor();
		    }

		 
			
			public void configure(Context arg0) {
				// TODO Auto-generated method stub
				
			}

		  }
	
}
