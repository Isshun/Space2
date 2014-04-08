package org.bluebox.space2.model;

public class NameGenerator {

	public static final int GREC = 0;
	public static final int KLINGON = 1;
	
	private static final String[] sGrec = {"alpha", "beta", "gamma", "delta", "epsilon", "zeta", "eta", "theta", "iota", "kappa", "lambda", "mu", "nu", "xi", "omicron", "pi", "rau", "sigma", "tau", "upsilon", "phi", "khi", "psi", "omega", "digamma", "san", "koppa", "sampi", "stigma", "heta", "sho", "pente", "deka", "hekaton", "khilioi", "murioi"};

	public static String generate (int lang, int i) {
		switch (lang) {
		case KLINGON: return generateKlingon(i);
		default: return generateGrec(i);
		}
	}

	private static String generateGrec (int i) {
		return sGrec[Math.min(i, sGrec.length - 1)];
	}

	private static String generateKlingon (int i) {
		String[] u = {"pagh", "wa'", "cha'", "wej", "loS", "vagh", "jav", "Soch", "chorg", "Hut"};
		String[] d = {"", "wa'maH", "cha'maH", "wejmaH", "loSmaH", "vaghmaH", "javmaH", "SochmaH", "chorgmaH", "HutmaH"};
		
		String unit = u[i % 10];
		if (i % 10 == 0 && i != 0) {
			unit = "";
		}
		String dec = d[i / 10];
		String sep = i % 10 > 0 && i / 10 > 0 ? " " : "";
		
		return dec + sep + unit;
	}

}
