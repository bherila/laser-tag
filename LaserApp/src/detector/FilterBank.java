package detector;

import java.util.*;

// The FilterBank contains a collection of kernels and can perform a match against a signal
// It returns a pyramid of responses (represented as a list)
public class FilterBank {

	// a few trivial filters for testing purposes only
//	private static final double[] FILTER1 = {1};
//	private static final double[] FILTER2 = {1,0,1};
//	private static final double[] FILTER3 = {1,0,1,0,1};
	
	private static final double[] BASE_PATTERN = {0,1,0,1};
	private static final int STEP = 5;
	private static final int N_SCALES = 10;
	
	private List<double[]> _filters;
	
	public FilterBank() {
		_filters = new ArrayList<double[]>();		
		// procedurally generate filters at different scales
		int baselen = BASE_PATTERN.length;
		for(int s=1; s<=N_SCALES; s++){		
			int offset = 0;
			int linewidth = s*STEP;	
			int scaleSize = 2*s*STEP*baselen;
			double[] scale = new double[scaleSize];			
			for(int i=0; i<baselen; i++){			
				for(int j=0; j<linewidth; j++){
					offset = i*linewidth+j;
					scale[offset] = BASE_PATTERN[i];
					scale[(scaleSize-offset) - 1] = BASE_PATTERN[i];
				}
			}
			_filters.add(scale);		
		}
		

	}
	
	public ResponsePyramid match(double[][] signal){
		// perform match with each level
		List<double[][]> responses = new ArrayList<double[][]>();
		for(double[] kernel : _filters){
			responses.add(conv(signal,kernel));		
		}
		ResponsePyramid pyr = new ResponsePyramid(responses);
		return pyr;
	}
	
	private double[][] conv(double[][] signal, double[] kernel){
		// perform measure of similarity; use normalized cross-correlation
		// do cross-correlation first
		int width = signal[0].length;
		int height = signal.length;
		int kernelWidth = kernel.length;
		int kernelHeight = 1;
		
		double[][] conved = Matcher.rowConv(signal, width, height,
											kernel, kernelWidth, kernelHeight);
		
		return conved;
	}
	
	
}
