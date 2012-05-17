package no.mehl.settense;

import no.mehl.settense.loader.AndroidLoader;
import no.mehl.settense.loader.FileLoader;
import no.mehl.settense.loader.LibgdxLoader;
import no.mehl.settense.loader.RegularLoader;

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
	 * Load strings from the specified file, within the package.
	 * <ul>
	 * 	<li>android: assets/path/file</li>
	 *  <li>libgdx: path/file</li>
	 *  <li>regular: path/file</li>
	 * </ul>
	 * @param file The file to load from.
	 * @return A {@link TenseMap} containing all strings from a successful read file, null otherwise.
	 */
	public TenseMap loadInternalStrings(String file) {
		return parseToMap(this.loader.readInternalFile(file));
	}
	/**
	 * Load strings from the external storage
	 * <ul>
	 * 	<li>android: external/data/package-name/path/file</li>
	 *  <li>libgdx: ~/path/file</li>
	 *  <li>regular: ~/path/file</li>
	 * </ul>
	 * @param file Name of the {@link File} to load.
	 * @return A generated {@link TenseMap containing all strings}.
	 */
	public TenseMap loadExternalStrings(String file) {
		return parseToMap(this.loader.readExternalFile(file));
	}
	/**
	 * Simple method for actually doing the parsing of the file.
	 * @param raw The {@link String} to parse into JSON.
	 * @return {@link TenseMap} representing the JSON.
	 */
	private TenseMap parseToMap(String raw) {
		if(raw == null) return null;
		TenseMap model = gson.fromJson(raw, TenseMap.class);
		return model;
	}
}
