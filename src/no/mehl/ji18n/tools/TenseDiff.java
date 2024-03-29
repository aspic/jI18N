package no.mehl.ji18n.tools;


import java.util.Iterator;
import java.util.Scanner;

import no.mehl.ji18n.Manager;
import no.mehl.ji18n.Map;
import no.mehl.ji18n.loader.RegularLoader;

public class TenseDiff extends Thread implements Runnable {
	
	private Scanner scanner;
	private Manager tense;
	
	private Map from;
	private Map to;
	private Iterator<String> it;
	
	private int diffCount;
	
	/**
	 * 
	 * @param file1
	 * @param file2
	 */
	public TenseDiff(String file1, String file2) {
		this.scanner = new Scanner(System.in);
		tense = new Manager(new RegularLoader("lng"));
		from = tense.loadInternalStrings(file1);
		to = tense.loadInternalStrings(file2);
		
		it = from.getKeyIterator();
		
		if(from == null) return;
		if(to == null) {
			to = new Map(file2);
			System.out.println("File did not exist. Creating new one (press <return> when finished).\nLocale: ");
			to.setLocale(scanner.nextLine());
		}
		
		/**
		 * Run shit
		 */
		System.out.println("Will prompt for each missing key. Press <return> when finished entering value.");
		this.start();
	}
	
	@Override
	public void run() {
		if(it.hasNext()) {
			String key = it.next();
			if(!to.keyExists(key)) {
				System.out.println("No value for key <"+key+">. " + from.getLocale() + " equivalent: \"" + from.getString(key) + "\"");
				System.out.print("Insert value: ");
				to.setKeyStringPair(key, scanner.nextLine());
				diffCount++;
			}
			/**
			 * Next
			 */
			run();
		} else {
			/**
			 * Check whether we need to write language to storage.
			 */
			if(to.isDirty()) {
				System.out.println("Diffed " + diffCount + " value(s). Writing to file: " + to.getFileName());
				tense.writeStrings(to.getFileName(), to);
			} else {
				System.out.println("All keys existed, did not save to file.");
			}
			System.exit(0);
		}
		
	}

}
