package edu.rosehulman.xuez;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.interceptor.*;

public class ParseTimeInterceptor implements Interceptor {

	public ParseTimeInterceptor() {

	}

	public void initialize() {
		// no-op
	}

	/**
	 * Modifies events in-place.
	 */

	public Event intercept(Event event) {

		try {
			java.util.Date date = new java.util.Date();
			Timestamp time = new Timestamp(date.getTime());
			String parseTime = time.toString();
			String eventBody = new String(event.getBody(), "UTF-8");
			if (eventBody.charAt(0) == '#') {
				return null;
			}
			

			StringBuilder builder = new StringBuilder();
			builder.append(eventBody).append("++++").append(parseTime).append("++++");
			Event e = EventBuilder.withBody(builder.toString(), Charset.forName("UTF-8"), event.getHeaders());
			return e;
		} catch (Exception exp) {
			return null;
		}
	}

	/**
	 * Delegates to {@link #intercept(Event)} in a loop.
	 * 
	 * @param events
	 * @return
	 */

	public List<Event> intercept(List<Event> events) {
		List<Event> eventList = new ArrayList<Event>();
		for (Event event : events) {
			Event outEvent = intercept(event);
			if (outEvent != null) {
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
			return new ParseTimeInterceptor();
		}

		public void configure(Context arg0) {
			// TODO Auto-generated method stub

		}

	}

}
