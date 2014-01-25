package detector;

import java.util.*;

// where to store the patterns?
class DefaultDetector implements IDetector {

	// package private constructor to be called by DetectorFactory
	DefaultDetector(){
		// no-op; everything at default
	}
	
	@Override
	public int detect(int[] red, int[] green, int[] blue) {

		System.out.println(">>> DefaultDetector.detect");
		
		System.out.println("DefaultDetector.detect: Converting int channels to normalized double");
		double[] dred = normalizePixelArray(red);
		double[] dgreen = normalizePixelArray(green);
		double[] dblue = normalizePixelArray(blue);
		System.out.println("DefaultDetector.detect: int channels have been converted to normalized double");
			
		// System.exit(0);
		
		// start with one color
		System.out.println("DefaultDetector.detect: constructing image pyramid for red channel");
		ImagePyramid pyr = ImagePyramid.from1DQuad(dred);
		System.out.println("DefaultDetector.detect: image pyramid for red channel has been constructed");
		
		// System.exit(0);
		
		System.out.println("DefaultDetector.detect: Getting templates from template factory");
		TemplateFactory fac = new TemplateFactory();
		List<Template> patterns = fac.getPatternList();	
		int nTemplates = patterns.size();
		double[] scores = new double[nTemplates];
		System.out.println("DefaultDetector.detect: Templates from template factory have been obtained");
		
		// System.exit(0);
		
		System.out.println("DefaultDetector.detect: Performing matches");
		for(int i=0; i<nTemplates; i++){
			scores[i] = pyr.match(patterns.get(i));
		}
		System.out.println("DefaultDetector.detect: Matches have been performed");
	
		// System.exit(0);
		
		System.out.println("DefaultDetector.detect: Finding best match");
		int hitId = indexOfMax(scores);	
		System.out.println("DefaultDetector.detect: Best match has been found");
		
		// System.exit(0);
		
		System.out.println(">>> DefaultDetector.detect");
		return hitId;
	}
	
	// convert int [0,255] to double [0.0,1.0]
	private double[] normalizePixelArray(int[] input){
		int size = input.length;
		double[] output = new double[size];
		for(int i=0; i<size; i++){
			output[i] = ( (double)input[i] ) / 255.0;
			// System.out.printf("DefaultDetector.normalizePixelArray: input[i]: %d, output[i]: %f \n", input[i], output[i]);
		}
		return output;
	}
	
	private int indexOfMax(double[] data){
		double max = Double.NEGATIVE_INFINITY;
		int idx = 0;
		int size = data.length;
		for(int i=0; i<size; i++){
			if(data[i] > max){
				// update current maximum value and its index
				max = data[i];
				idx = i;
			}
		}
		return idx;
	}
	
}
