package detector;

import java.util.*;

// where to store the patterns?

public class DefaultDetector implements IDetector {

	// package private constructor to be called by DetectorFactory
	DefaultDetector(){
		// no-op; everything at default
	}
	
	@Override
	public int detect(int[] red, int[] green, int[] blue) {

		double[] dred = normalizePixelArray(red);
		double[] dgreen = normalizePixelArray(green);
		double[] dblue = normalizePixelArray(blue);
			
		// start with one color
<<<<<<< HEAD
		ImagePyramid pyr = ImagePyramid.from1DQuad(dred);
		TemplateFactory fac = new TemplateFactory();
		List<Template> patterns = fac.getPatternList();
		int nTemplates = patterns.size();
		double[] scores = new double[nTemplates];
		
		for(int i=0; i<nTemplates; i++){
			scores[i] = pyr.match(patterns.get(i));
		}
	
		int hitId = indexOfMax(scores);		
		return hitId;
=======
		//pyr = new ImagePyramid(red);
		//fac = new TemplateFactory();
		
		// what does this return?
		// a normalized something?
		// just boolean if there was a match?
		//double best = pyramid.match(fac.getTemp("foo"));
		
		// match all with all?
		
		
		
		
		
		// TODO Auto-generated method stub
		return 0;
>>>>>>> 768320e15a1d97c0a723e382d99590aa082333d0
	}
	
	// convert int [0,255] to double [0.0,1.0]
	private double[] normalizePixelArray(int[] input){
		int size = input.length;
		double[] output = new double[size];
		for(int i=0; i<size; i++){
			output[i] = ( (double)input[i] ) / 255.0;
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
