package detector;

import java.util.regex.Pattern;

import android.util.Log;

public class SimpleDetector implements IDetector {
	
	/*
	 8x10
	 K W K W K
	 W	 W	 W
	 K   K   K
	 W K W K W
	 */
	private final static boolean B = false, W = true;
	private final static boolean TEMPLATES[][][] =
		{{{B, W, B, B},
		  {B, B, B, W},
		  {W, B, B, B},
		  {B, B, W, B}},
	     {{B, W, B, B},
		  {B, W, W, W},
		  {W, W, W, B},
		  {B, B, W, B}},		  
		};
	private final static int[] SCALES = new int[25];
	
	static {
		for (int i = 0; i < 25; ++i)
			SCALES[i] = 1 + 4*i;
	}

	@Override
	public int detect(int[] red, int[] green, int[] blue) {
		int dim = (int) Math.sqrt(red.length);
		boolean img[][] = new boolean[dim][dim];
		
		for (int i = 0; i < dim; ++i)
			for (int j = 0; j < dim; ++j)
				img[i][j] = toBw(red[i*dim+j], green[i*dim+j], blue[i*dim+j]);
		
		for (int scale : SCALES) {
			for (int startX = 0; startX < dim-3*scale-1; startX++) {
				for (int startY = 0; startY < dim-3*scale-1; startY++) {
					for (int t = 0; t < TEMPLATES.length; ++t) {
						boolean[][] template = TEMPLATES[t];
						boolean stillGood = true;
						for (int i = 0; stillGood && i < template.length; ++i){
							for (int j = 0; stillGood && j < template[i].length; ++j) {
								boolean templatePx = template[i][j];
								boolean gridPx = img[startX + i*scale][startY + j*scale];
								stillGood = (templatePx == gridPx);
							}
						}
						if (stillGood) return t+1;
					}
				}
			}
		}

		return 0;
	}
	
	private boolean toBw(int r, int g, int b) {
		return (0.2989*r + 0.5870*g + 0.1140*b) < 128;
	}
}
