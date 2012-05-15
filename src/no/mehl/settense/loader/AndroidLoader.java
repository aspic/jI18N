package no.mehl.settense.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import no.mehl.settense.compability.Wrapper;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * A {@link FileLoader} for Android. Will load from the /assets/path.
 * @author aspic
 */
public class AndroidLoader extends FileLoader {
	private Context ctx;
	private static final String LOG = "FileLoader";
	
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
	public void writeFile(String filename, String data) {
		File file = null;
		/**
		 * Handling external storage differently on API <= 7
		 */
		if(android.os.Build.VERSION.SDK_INT <= 7) {
			Log.d(LOG, "API <= 7");
			file = createFile(filename);
		} else {
			Log.d(LOG, "API > 7");
			file = new File(Wrapper.getExternalFilesDir(ctx), filename);
		}
		
		/**
		 * Lets create the file.
		 * TODO: Do some proper error handling.
		 */
		try {
			write(file, data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write data to the specified {@link File} handle.
	 * @param file The handle to write to.
	 * @param data Data to write, in the form of a {@link String}.
	 * @throws FileNotFoundException
	 */
	private void write(File file, String data) throws FileNotFoundException {
		FileOutputStream os = new FileOutputStream(file);
		try {
			os.write(data.getBytes());
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the file and the directory hierarchy.
	 * @param filename Name of the{@link File} to create.
	 * @return The newly created {@link File}, or null of there was an error.
	 */
	private File createFile(String filename) {
		File dir = new File(Environment.getExternalStorageDirectory(), "/data/" + ctx.getApplicationInfo().packageName + "/" +lngPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File f = new File(dir, filename);
		try {
			if(!f.exists()) {
				f.createNewFile();
			}
			return f;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
