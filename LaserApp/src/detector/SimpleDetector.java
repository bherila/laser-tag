package detector;

public class SimpleDetector implements IDetector {

	private final static boolean B = false, W = true;
	private final static boolean TEMPLATES[][][] =
		{{{B, W, B, W, B},
		  {W, B, B, B, W},
		  {B, B, W, B, B},
		  {W, B, B, B, W},
		  {B, W, B, W, B}},
	     {{B, W, B, W, B},
		  {W, W, W, W, W},
		  {B, W, B, W, B},
		  {W, W, W, W, W},
	      {B, W, B, W, B}},		  
		};
	private final static int[] SCALES = new int[50];
	
	static {
		for (int i = 0; i < 50; i++)
			SCALES[i] = 2*i+1;
	}

	@Override
	public int detect(int[] red, int[] green, int[] blue) {
		int dim = (int) Math.sqrt(red.length);
		boolean img[][] = new boolean[dim][dim];
		
		for (int i = 0; i < dim; ++i)
			for (int j = 0; j < dim; ++j)
				img[i][j] = toBw(red[i*dim+j], green[i*dim+j], blue[i*dim+j]);
		
		for (int t = 0; t < TEMPLATES.length; ++t) {
			boolean[][] template = TEMPLATES[t];

			for (int scale : SCALES) {
				for (int sx = 0; sx < dim-template.length*scale; sx += 2) {
					for (int sy = 0; sy < dim-template.length*scale; sy += 2) {
							
						boolean stillGood = true;
						
						for (int i = 0; stillGood && i < template.length; ++i){
							for (int j = 0; stillGood && j < template[i].length; ++j) {
								boolean templatePx = template[i][j],
											gridPx = img[sx + i*scale][sy + j*scale];
								
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
		return (0.2989*r + 0.5870*g + 0.1140*b) > 128;
	}
}
