package no.mehl.settense.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;

/**
 * A {@link FileLoader} for Android. Will load from the /assets/path.
 * @author aspic
 */
public class AndroidLoader extends FileLoader {
	private Context ctx;
	
	public AndroidLoader(String lngPath, Context ctx) {
		super(lngPath);
		this.ctx = ctx;
	}

	
	/**
	 * Reading a file from an Android context.
	 * @param file
	 * @return The JSON as a {@link String}.
	 */
	@Override
	public String readFile(String file) {
		try {
			return parseFile(new BufferedReader(new InputStreamReader(ctx.getAssets().open(lngPath + "/" + file))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public void writeFile(String fileName, String data) {
		// TODO Auto-generated method stub
	}
	
}
