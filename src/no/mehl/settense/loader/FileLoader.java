package no.mehl.settense.loader;

import java.io.BufferedReader;
import java.io.IOException;

import no.mehl.settense.TenseMap;

/**
 * A generic file loading class.
 * @author aspic
 */
public abstract class FileLoader {
	
	protected String lngPath;
	/**
	 * Constructor for this abstract class.
	 * @param lngPath File path where language files are located.
	 */
	protected FileLoader(String lngPath) {
		this.lngPath = lngPath;
	}
	
	/**
	 * Reading and parse a file from {@link BufferedReader}
	 * @param reader The reader to read from.
	 * @return The concatenated {@link String}, presumptively in JSON format.
	 */
	protected String parseFile(BufferedReader reader) {
		StringBuffer buf = new StringBuffer();
		String s;
		try {
		    while ((s = reader.readLine()) != null) {
		        buf.append(s);
		    }
		    reader.close();
		} catch (IOException e) {
			return null;
		}
		return buf.toString();
	}
	/**
	 * Generic method for reading a {@link File} from the package.
	 * @param file The name of the file.
	 * @return The JSON representation of the file.
	 */
	public abstract String readInternalFile(String file);
	/**
	 * Generic method for reading a {@link File} from the external storage.
	 * @param file The name of the file.
	 * @return The JSON representation of the file.
	 */
	public abstract String readExternalFile(String file);
	/**
	 * Generic method for writing a {@link File} to the external storage.
	 * @param filename Filename of the to be written file.
	 * @param data Data to write, in a {@link String} format.
	 */
	public abstract void writeFile(String filename, TenseMap model);
	/**
	 * Generic method for creating a {@link TenseMap} model given a raw JSON {@link String}
	 * @param raw The JSON string to be parsed, using the preferred parser.
	 * @return The {@link TenseMap}.
	 */
	public abstract TenseMap fromJson(String raw);
}
