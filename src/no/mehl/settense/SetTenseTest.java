package no.mehl.settense;

import com.badlogic.gdx.utils.GdxNativesLoader;

public class SetTenseTest {

	/**
	 * A simple test for setting tenses.
	 * @param args
	 */
	public static void main(String[] args) {
		GdxNativesLoader.load();
		Tense t = new Tense("en_GB");
		t.printStrings();
		
		System.out.println(t.getString("btn_ok"));
	}
}
