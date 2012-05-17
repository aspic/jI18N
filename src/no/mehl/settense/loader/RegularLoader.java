package no.mehl.settense.loader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import no.mehl.settense.TenseMap;

/**
 * A class for loading files in a standard java environment.
 * @author aspic
 */
public class RegularLoader extends FileLoader {

	public RegularLoader(String lngPath) {
		super(lngPath);
	}

	/**
	 * Reading a file from a regular java context.
	 * @param file File name of file to load.
	 * @return The JSON as a {@link String} representation.
	 */
	@Override
	public String readInternalFile(String file) {
		try {
			return parseFile(new BufferedReader(new FileReader(lngPath + "/" + file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Save file to file system.
	 * @param fileName Name of file to save.
	 * @param data The data to save, preferable in a JSON format.
	 */
	@Override
	public void writeFile(String filename, String data) {
		try {
			File dir = new File(getExternalPath());
			File file = new File(dir, filename);
			if(!dir.exists()) {
				/**
				 * TODO: Do some more checking.
				 */
				dir.mkdirs();
				file.createNewFile();
			}
		    BufferedWriter out = new BufferedWriter(new FileWriter(file));
		    out.write(data);
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String readExternalFile(String file) {
		/**
		 * TODO: Actually do some stuff.
		 */
		return null;
	}
	
	private String getExternalPath() {
		/**
		 * TODO: Not windows friendly, also bit of a hack.
		 */
		return System.getProperty( "user.home" ) + "/." + new TenseMap().getClass().getPackage().getName() + "/" + lngPath;
	}

}
