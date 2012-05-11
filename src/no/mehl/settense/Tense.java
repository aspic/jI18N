package no.mehl.settense;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * A class for managing {@link Strings} for language purposes in an application.
 * Strings could be diffed from a server, and/or stored locally.
 * @author aspic
 */
public class Tense {
	
	private String path;
	private TenseMap strings;
	
	public Tense(String languagePath) {
		this.path = languagePath;
	}
	
	public TenseMap loadStrings(String file) {
		FileHandle handle = null;
		String json = null;
		TenseMap model = null;
		// We assume libgdx
		if(Gdx.files != null) {
			handle = Gdx.files.internal(path + "/" + file);
		} 
		// Load from filesystem
		else {
			handle = new FileHandle(new File(path + "/" + file));
		}
		try {
			json = handle.readString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		model = new Json().fromJson(TenseMap.class, json);
		model.setFileName(file);
		return model;
	}
	
	public void writeString(String fileName, TenseMap model) {
		String test = new Json().toJson(model);
		FileHandle h = new FileHandle(new File(path + "/" + fileName));
		h.writeString(test, false);
	}
	
	
}
