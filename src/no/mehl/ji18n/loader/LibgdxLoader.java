package no.mehl.ji18n.loader;

import org.apache.http.util.LangUtils;

import no.mehl.ji18n.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * A {@link FileLoader} for Libgdx. Will load from the {@link Gdx.files.internal} interface.
 * @author aspic
 */
public class LibgdxLoader extends FileLoader {
	
	private FileHandle handle;
	private Json json;

	public LibgdxLoader(String languageFolder) {
		super(languageFolder);
		json = new Json();
	}

	@Override
	public String readInternalFile(String file) {
		handle = Gdx.files.internal(folder + "/" + file);
		return handle.readString();
	}

	@Override
	public void writeFile(String filename, Map model) {
		String jsonString = json.toJson(model, Map.class);
		FileHandle handle = Gdx.files.external(folder + "/" + filename);
		handle.writeString(jsonString, false);
	}

	@Override
	public String readExternalFile(String file) {
		handle = Gdx.files.external(folder + "/" + file);
		return handle.readString();
	}

	@Override
	public Map fromJson(String raw) {
		return json.fromJson(Map.class, raw);
	}
}
