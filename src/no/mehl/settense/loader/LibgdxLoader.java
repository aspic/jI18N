package no.mehl.settense.loader;

import no.mehl.settense.TenseMap;

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

	public LibgdxLoader(String lngPath) {
		super(lngPath);
		json = new Json();
	}

	@Override
	public String readInternalFile(String file) {
		handle = Gdx.files.internal(lngPath + "/" + file);
		return handle.readString();
	}

	@Override
	public void writeFile(String filename, TenseMap model) {
		String jsonString = json.toJson(model, TenseMap.class);
		FileHandle handle = Gdx.files.external(lngPath + "/" + filename);
		handle.writeString(jsonString, false);
	}

	@Override
	public String readExternalFile(String file) {
		handle = Gdx.files.external(lngPath + "/" + file);
		return handle.readString();
	}

	@Override
	public TenseMap fromJson(String raw) {
		return json.fromJson(TenseMap.class, raw);
	}

}
