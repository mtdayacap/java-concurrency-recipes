package mtd.java.concurrency.multithreading;

import java.util.Collections;
import java.util.Map;

/**
 * An example of how to implement an immutable class for multi-threading design
 * Rules:
 * 	- All fields should be final and private.
 * 	- There should be not setter methods.
 *  - The class itself should be declared final in order to prevent subclasses to violate the principle
 *     of immutability.
 *  - If fields are not of a primitive type but a reference to another object:
 *  	- There should not be a getter method that exposes the reference directly to the caller.
 *  	- Don’t change the referenced objects (or at least changing these references is not visible 
 *  	   to clients of the object).   
 * */
public final class ImmutableMessage {
	private final String subject;
	private final String message;
	private final Map<String, String> headerMap;

	public ImmutableMessage(String subject, String message,
			Map<String, String> headerMap) {
		this.subject = subject;
		this.message = message;
		this.headerMap = headerMap;
	}

	public Map<String, String> getHeaderMap() {
		return Collections.unmodifiableMap(headerMap);
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}
}
