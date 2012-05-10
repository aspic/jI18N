package no.mehl.settense;

import java.io.File;
import java.util.HashMap;
import no.mehl.settense.exceptions.NoSuchLocaleException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Logger;

/**
 * A class for managing {@link Strings} for language purposes in an application.
 * Strings could be diffed from a server, and/or stored locally.
 * @author aspic
 */
public class Tense {
	
	private final String path = "lng";
	private String locale;
	private HashMap<String, String> strings;
	
	public Tense(String locale) {
		strings = new HashMap<String, String>();
		this.locale = locale;
		try {
			loadStrings();
		} catch (NoSuchLocaleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void loadStrings() throws NoSuchLocaleException {
		FileHandle h = new FileHandle(new File(path + "/" + locale));
		if(h == null) {
			throw new NoSuchLocaleException();
		}
		strings = new Json().fromJson(HashMap.class, h.readString());
	}
	
	private void writeString() {
		HashMap<String, String> lol = new HashMap<String, String>();
		lol.put("asdf", "asdfasdf");
		lol.put("asdfas", "asdfasdasasf");
		String test = new Json().toJson(lol);
		FileHandle h = new FileHandle(new File(path + "/" + locale));
		h.writeString(test, false);
	}
	
	/**
	 * Prints all data currently in this instance.
	 */
	public void printStrings() {
		System.out.println("Printing for locale: " + locale + "\n");
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
		if(map == null) return key;
		return map;
	}
}
