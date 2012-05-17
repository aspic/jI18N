package no.mehl.settense.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * A {@link FileLoader} for Libgdx. Will load from the {@link Gdx.files.internal} interface.
 * @author aspic
 */
public class LibgdxLoader extends FileLoader {
	
	private FileHandle handle;

	public LibgdxLoader(String lngPath) {
		super(lngPath);
	}

	@Override
	public String readInternalFile(String file) {
		handle = Gdx.files.internal(lngPath + "/" + file);
		return handle.readString();
	}

	@Override
	public void writeFile(String filename, String data) {
		FileHandle handle = Gdx.files.external(lngPath + "/" + filename);
		handle.writeString(data, false);
	}

	@Override
	public String readExternalFile(String file) {
		handle = Gdx.files.external(lngPath + "/" + file);
		return handle.readString();
	}

}
