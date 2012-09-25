package no.mehl.ji18n.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import no.mehl.ji18n.Map;
import no.mehl.ji18n.compability.Wrapper;

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
	private Gson json;
	
	public AndroidLoader(String languageFolder, Context ctx) {
		super(languageFolder);
		this.ctx = ctx;
		json = new Gson();
	}

	
	/**
	 * Reading a file from an Android context.
	 * @param file
	 * @return The JSON as a {@link String}.
	 */
	@Override
	public String readInternalFile(String file) {
		try {
			return parseFile(new BufferedReader(new InputStreamReader(ctx.getAssets().open(folder + "/" + file))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String readExternalFile(String file) {
		try {
			return parseFile(new BufferedReader(new FileReader(getFileHandle(file))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void writeFile(String filename, Map model) {
		String jsonString = json.toJson(model);
		
		File file = getFileHandle(filename);
		/**
		 * Lets create the file.
		 * TODO: Do some proper error handling.
		 */
		try {
			write(file, jsonString);
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
	private File getFileHandle(String filename) {
		/**
		 * Handling external storage differently on API <= 7
		 */
		File dir;
		/**
		 * Fetches the parent file handle.
		 */
		if(android.os.Build.VERSION.SDK_INT <= 7) {
			Log.d(LOG, "API <= 7");
			dir = new File(Environment.getExternalStorageDirectory(), "/data/" + ctx.getApplicationInfo().packageName + "/" +folder);
		} else {
			Log.d(LOG, "API > 7");
			dir = Wrapper.getExternalFilesDir(ctx);
		}
		
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

	@Override
	public Map fromJson(String raw) {
		return json.fromJson(raw, Map.class);
	}
}
