package detector;

import java.util.regex.Pattern;

public class SimpleDetector implements IDetector {
	
	private final static char R = 'r', K = 'k', W = 'w';
	private final static char[][] TEMPLATES = {{R,K,R,K,K,R,K,R},{R,K,R,W,W,R,K,R},{R,W,R,K,K,R,W,R},{R,W,R,W,W,R,W,R}};
	private final static Pattern[][] PATTERNS = new Pattern[4][15];

	static {
		for (int i = 0; i < 4; ++i) {
			for (int j = 4; j < 19; ++j) {
				String s = "";
				for (char c : TEMPLATES[i])
					s += c + "{" + j + "," + (3*j) + "}";
				PATTERNS[i][j-4] = Pattern.compile(".*" + s + ".*");
			}
		}
	}

	@Override
	public int detect(int[] red, int[] green, int[] blue) {
		
		StringBuilder pixSeq = new StringBuilder(red.length);
		
		for (int i = 0; i < red.length; ++i)
			pixSeq.append(closest(red[i], green[i], blue[i]));
				
		for (int i = 0; i < PATTERNS.length; ++i)
			for (Pattern p : PATTERNS[i])
				if (p.matcher(pixSeq).matches())
					return i;
		
		return 0;
	}
	
	private double distance(int r1, int g1, int b1, int r2, int g2, int b2) {
		return Math.sqrt((r1-r2)*(r1-r2) + (g1-g2)*(g1-g2) + (b1-b2)*(b1-b2));
	}
	private char closest(int r, int g, int b) {
		double dr = distance(255,   0,   0, r, g, b),
			   dk = distance(  0,   0,   0, r, g, b),
			   dw = distance(255, 255, 255, r, g, b);
		
		if (dr < dk)
			if (dr < dw) 	return R;
			else 			return W;
		else if (dk < dw) 	return K;
		else 				return W;
	}

}
