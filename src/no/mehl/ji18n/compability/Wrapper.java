package no.mehl.ji18n.compability;

import java.io.File;

import android.content.Context;

/** A wrapper class for backward compability. This wrapper won't be invoked by Dalvik, hence
 * the compiler won't know that {@link Context#getExternalFilesDir(String)} does not exist.*/
public class Wrapper {
	/**
	 * Simple wrapper method.
	 * @param ctx The Android {@link Context}.
	 * @return A new {@link File} handle.
	 */
	public static File getExternalFilesDir(Context ctx) {
//		return ctx.getExternalFilesDir(null);
		return null;
	}
}
