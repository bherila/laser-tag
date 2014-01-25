package detector;

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
		{{{B, W, B, W, B},
		  {W, B, B, B, W},
		  {B, B, W, B, B},
		  {W, B, B, B, W},
		  {B, W, B, W, B}},
	     {{B, W, B, W, B},
		  {W, W, B, W, W},
		  {B, W, W, W, B},
		  {W, B, W, B, W},
	      {B, W, B, W, B}},		  
		};
	private final static int[] SCALES = new int[98];
	
	static {
		for (int i = 3; i <= 100; ++i)
			SCALES[i-3] = i;
	}

	@Override
	public int detect(int[] red, int[] green, int[] blue) {
		int dim = (int) Math.sqrt(red.length);
		boolean img[][] = new boolean[dim][dim];
		
		for (int i = 0; i < dim; ++i)
			for (int j = 0; j < dim; ++j)
				img[i][j] = toBw(red[i*dim+j], green[i*dim+j], blue[i*dim+j]);
		
		int bestQuality = 1;
		
		for (int t = 0; t < TEMPLATES.length; ++t) {
			boolean[][] template = TEMPLATES[t];

			for (int scale : SCALES) {
				for (int sx = 0; sx < dim-template.length*scale-1; sx += 2) {
					for (int sy = 0; sy < dim-template.length*scale-1; sy += 2) {
							
						boolean stillGood = true;
						int quality = 0;
						
						for (int i = 0; stillGood && i < template.length; ++i){
							for (int j = 0; stillGood && j < template[i].length; ++j) {
								boolean templatePx = template[i][j],
											gridPx = img[sx + i*scale][sy + j*scale];
								
								stillGood = (templatePx == gridPx);
								++quality;
							}
						}
						if (quality > bestQuality) bestQuality = quality;
						if (stillGood) return t+1;
					}
				}
			}
		}
		
		Log.d("ELI", "Quality: " + bestQuality);

		return 0;
	}
	
	private boolean toBw(int r, int g, int b) {
		return (0.2989*r + 0.5870*g + 0.1140*b) > 128;
	}
}
