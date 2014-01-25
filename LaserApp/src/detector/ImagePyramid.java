package detector;

import java.util.*;

public class ImagePyramid {

	private final double THRESHOLD = 0.5;
	
	// private fields contain scaled images
	// ultimately scale does not matter; just hit/non-hit
	private List<double[]> _scales;
	
	// package private
	ImagePyramid(double[] data, int height, int width){
		
		// TODO: Find way of making scales (bicubic scaling)
	}
	
	// match matches it with a template
	// wrap template of be flexible?
	// yup, template factory returns template
	
	// package private again
	int match(Template temp){
		
		// unwrap content of temp
		// perform convolution with each scale
		// for-each scale
		double[] pattern = temp.getPattern();
		
		// for-each scale
		for(double[] scale : _scales){
			
			// perform matching (normalized cross-correlation)			
			// compute max on the run (use classifier?)
			// threshold
			
			// perform the pattern matching
			
			// then match each line
			
		}
		
		
		return 0;
	}
	
	
}
