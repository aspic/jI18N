package no.mehl.settense;

import java.util.HashMap;
import java.util.Iterator;

public class TenseMap {
	private String hash;
	private String locale;
	private HashMap<String, String> strings;
	private transient String fileName;
	private transient boolean dirty;
	private transient boolean debug;
	
	public TenseMap() {
		this(null);
	}
	
	public TenseMap(String fileName) {
		this.fileName = fileName;
		strings = new HashMap<String, String>();
	}
	
	public TenseMap(String hash, HashMap<String, String> strings) {
		super();
		this.hash = hash;
		this.strings = strings;
	}

	/**
	 * Prints all data currently in this instance.
	 */
	public void printStrings() {
		System.out.println("Printing strings for: " + locale + " hash(" + hash + ")");
		for (String s : strings.keySet()) {
			System.out.println(s  + " → " + strings.get(s));
		}
	}
	
	/**
	 * Check the {@link HashMap} for a corresponding match.
	 * @param key The key to fetch a result for.
	 * @return The result, in a form of a {@link String}, or the key  if
	 * no result was found.
	 */
	public String getString(String key) {
		String map = strings.get(key);
		if(map == null) {
			if(debug) return "_not found_ " + key;
			return key;
		}
		return map;
	}
	
	public boolean keyExists(String key) {
		if(getString(key).equals(key)) return false;
		return true;
	}
	
	public void setKeyStringPair(String key, String value) {
		strings.put(key, value);
		dirty = true;
	}
	
	public Iterator<String> getKeyIterator() {
		return strings.keySet().iterator();
	}
	
	public String getLocale() {
		return locale;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public boolean isDirty() {
		return this.dirty;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	/**
	 * All keys that are not found, will be denoted.
	 * @param b Whether to debug or not.
	 */
	public void setDebug(boolean b) {
		this.debug = b;
	}
}
