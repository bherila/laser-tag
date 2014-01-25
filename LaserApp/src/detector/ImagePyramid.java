package detector;

import java.util.*;

class ImagePyramid {

	private final double THRESHOLD = 0.5;
	
	// private fields contain scaled images
	// ultimately scale does not matter; just hit/non-hit
	private List<double[][]> _scales;
	
	// package private
	ImagePyramid(double[][] data, int height, int width){
		
		// TODO: Find way of making scales (bicubic scaling)
	}
	
	// package private again
	// returns similarity measure of best match
	double match(Template temp){
		
		// unwrap content of temp
		// perform convolution with each scale
		// for-each scale
		double[] kernel = temp.get1DPattern();
		
		double champion = 0.0;
		
		// for-each scale
		for(double[][] signal : _scales){
			
			// perform matching (normalized cross-correlation)			
			// compute max on the run (use classifier?)
			// threshold
			
			// perform the pattern matching
			
			// then match each line
			int width = signal[0].length; // row-major order
			int height = signal.length;
			int kernelHeight = 1;
			int kernelWidth = kernel.length;

			double[][] conved = Matcher.rowConv(signal, width, height, 
												kernel, kernelWidth, kernelHeight);
			
			// look for maxima
			double best = maxOf(conved);
			
			champion = (best > champion) ? best : champion;

		}
		
		
		return champion;
	}
	
	
}
