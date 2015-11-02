package edu.rosehulman.xuez;

import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.interceptor.*;

public class TextInterceptor implements Interceptor {

	private String fileName;

	public TextInterceptor() {

	}

	public void initialize() {
		// no-op
	}

	public Event intercept(Event event) {

		try {
			String hostName = InetAddress.getLocalHost().getHostName();
			String eventBody = new String(event.getBody(), "UTF-8");
			if (eventBody.charAt(0) == '#') {
				return null;
			}
			if (eventBody.contains("No commands sent from the Server")) {
				return null;
			}

			// Use String builder to write the new Event.
			StringBuilder builder = new StringBuilder();
			builder.append(hostName).append(":").append(eventBody);
			Event e = EventBuilder.withBody(builder.toString(), Charset.forName("UTF-8"), event.getHeaders());
			return e;
		} catch (Exception exp) {
			return null;
		}
	}

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
			return new TextInterceptor();
		}

		public void configure(Context arg0) {
			// TODO Auto-generated method stub

		}

	}

}
