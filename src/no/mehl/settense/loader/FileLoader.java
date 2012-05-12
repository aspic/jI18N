package no.mehl.settense.loader;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * A generic file loading class.
 * @author aspic
 */
public abstract class FileLoader {
	
	protected String lngPath;
	
	protected FileLoader(String lngPath) {
		this.lngPath = lngPath;
	}
	
	
	/**
	 * Reading a file from {@link BufferedReader}
	 * @param reader The reader to read from.
	 * @return The concatenated {@link String}.
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
	public abstract String readFile(String file);
	public abstract void writeFile(String fileName, String data);
}
