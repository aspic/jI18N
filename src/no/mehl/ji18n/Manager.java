package no.mehl.ji18n;

import java.util.HashMap;

import no.mehl.ji18n.loader.AndroidLoader;
import no.mehl.ji18n.loader.FileLoader;
import no.mehl.ji18n.loader.LibgdxLoader;
import no.mehl.ji18n.loader.RegularLoader;

/**
 * A class for managing {@link Strings} for language purposes in an application.
 * Strings could be diffed from a server, and/or stored locally.
 * @author aspic
 */
public class Manager {
	
	private FileLoader loader;
	private HashMap<String, Map> mapCache;
	private Map activeMap;
	
	/**
	 * A {@link Manager} for loading/writing {@link TenseModel}.
	 * @param loader Which {@link FileLoader} to use. For pure Android-application use {@link AndroidLoader}, for
	 * Libgdx-applications use {@link LibgdxLoader} and for generic java-applications use {@link RegularLoader}.
	 */
	public Manager(FileLoader loader) {
		this.loader = loader;
		this.mapCache = new HashMap<String, Map>();
	}
	/**
	 * Save an updated {@link Map}.
	 * @param fileName File name for the map.
	 * @param model The model to serialize.
	 */
	public void writeStrings(String fileName, Map model) {
		this.loader.writeFile(fileName, model);
	}
	/**
	 * Load strings from the specified file, from the package. Caches if necessary.
	 * <ul>
	 * 	<li>android: assets/path/file</li>
	 *  <li>libgdx: path/file</li>
	 *  <li>regular: path/file</li>
	 * </ul>
	 * @param file The file to load from.
	 * @return A {@link Map} containing all strings from a successful read file, null otherwise.
	 */
	public Map loadInternalStrings(String file) {
		Map cached = mapCache.get(file);
		if(cached == null) {
			cached = parseToMap(this.loader.readInternalFile(file));
			mapCache.put(file, cached);
		}
		activeMap = cached;
		return cached;
	}
	/**
	 * Load strings from the external storage, caches if necessary.
	 * <ul>
	 * 	<li>android: external/data/package-name/path/file</li>
	 *  <li>libgdx: ~/path/file</li>
	 *  <li>regular: ~/path/file</li>
	 * </ul>
	 * @param file Name of the {@link File} to load.
	 * @return A generated {@link Map containing all strings}.
	 */
	public Map loadExternalStrings(String file) {
		Map cached = mapCache.get(file);
		if(cached == null) {
			cached = parseToMap(this.loader.readExternalFile(file));
			mapCache.put(file, cached);
		}
		activeMap = cached;
		return cached;
	}
	/**
	 * Simple method for actually doing the parsing of the file.
	 * @param raw The {@link String} to parse into JSON.
	 * @return {@link Map} representing the JSON.
	 */
	private Map parseToMap(String raw) {
		if(raw == null) return null;
		return loader.fromJson(raw);
	}
	
	public void setActiveMap(String key) {
		activeMap = mapCache.get(key);
	}
	
	public void setActiveMap(Map map) {
		activeMap = map;
	}
	
	public Map getMap() {
		return activeMap;
	}
}
