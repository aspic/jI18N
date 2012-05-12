package no.mehl.settense;

import no.mehl.settense.loader.AndroidLoader;
import no.mehl.settense.loader.FileLoader;
import no.mehl.settense.loader.LibgdxLoader;
import no.mehl.settense.loader.RegularLoader;

import android.text.AndroidCharacter;

import com.google.gson.Gson;

/**
 * A class for managing {@link Strings} for language purposes in an application.
 * Strings could be diffed from a server, and/or stored locally.
 * @author aspic
 */
public class TenseManager {
	
	private FileLoader loader;
	private Gson gson;
	
	/**
	 * A {@link TenseManager} for loading/writing {@link TenseModel}.
	 * @param loader Which {@link FileLoader} to use. For pure Android-application use {@link AndroidLoader}, for
	 * Libgdx-applications use {@link LibgdxLoader} and for generic java-applications use {@link RegularLoader}.
	 */
	public TenseManager(FileLoader loader) {
		this.loader = loader;
		gson = new Gson();
	}
	
	/**
	 * Save an updated {@link TenseMap}.
	 * @param fileName File name for the map.
	 * @param model The model to serialize.
	 */
	public void writeStrings(String fileName, TenseMap model) {
		String data = gson.toJson(model);
		this.loader.writeFile(fileName, data);
	}
	
	/**
	 * Load strings from the specified file.
	 * @param file The file to load from.
	 * @return A {@link TenseMap} containing all strings from a successfull read file, null otherwise.
	 */
	public TenseMap loadStrings(String file) {
		String s = this.loader.readFile(file);
		if(s == null) return null;
		TenseMap model = gson.fromJson(s, TenseMap.class);
		model.setFileName(file);
		return model;
	}
}
