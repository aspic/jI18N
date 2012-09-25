package no.mehl.ji18n;

import java.util.HashMap;
import java.util.Iterator;

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
		this(loader, null, null);
	}
	
	/**
	 * This constructor actually load files using the specified {@link FileLoader}.
	 * @param loader The {@link FileLoader} to use.
	 * @param filenames Provide a list with file names to load.
	 * @param prefferedLocale A locale we want to set right away.
	 */
	public Manager(FileLoader loader, String[] filenames, String prefferedLocale) {
		this.loader = loader;
		this.mapCache = new HashMap<String, Map>();
		
		if(filenames != null) {
			for (int i = 0; i < filenames.length; i++) {
				loadInternalStrings(filenames[i]);
			}
			if(prefferedLocale != null) {
				setActiveMap(prefferedLocale);
			}
		}
		
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
			mapCache.put(cached.getLocale(), cached);
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
	public Map loadExternalStrings(String locale) {
		Map cached = mapCache.get(locale);
		if(cached == null) {
			cached = parseToMap(this.loader.readExternalFile(locale));
			mapCache.put(locale, cached);
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
	
	/**
	 * Sets the active {@link Map} based on the locale, given that the {@link Map} exists.
	 * @param locale The locale (specified in the language files).
	 */
	public Map setActiveMap(String locale) {
		if(mapCache.get(locale) != null) {
			activeMap = mapCache.get(locale);
			return activeMap;
		}
		return null;
	}
	
	public void setActiveMap(Map map) {
		activeMap = map;
	}
	
	public Map getMap() {
		return activeMap;
	}
	
	public String[] getLoadedMaps() {
		String[] locales = new String[mapCache.size()];
		mapCache.keySet().toArray(locales);
		return locales;
	}
	
}
