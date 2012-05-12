package no.mehl.settense.loader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
	public String readFile(String file) {
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
	public void writeFile(String fileName, String data) {
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter(lngPath + "/" + fileName));
		    out.write(data);
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
