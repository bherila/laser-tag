package detector;

import java.util.*;

// The FilterBank contains a collection of kernels and can perform a match against a signal
// It returns a pyramid of responses (represented as a list)
public class FilterBank {

	// a few trivial filters for testing purposes only
	private static final double[] FILTER1 = {1};
	private static final double[] FILTER2 = {1,0,1};
	private static final double[] FILTER3 = {1,0,1,0,1};
	
	private List<double[]> _filters;
	
	public FilterBank() {
		_filters = new ArrayList<double[]>();
		_filters.add(FILTER1);
		_filters.add(FILTER2);
		_filters.add(FILTER3);		
	}
	
	public List<double[][]> match(double[][] signal){
		// perform match with each level
		List<double[][]> responses = new ArrayList<double[][]>();
		for(double[] kernel : _filters){
			responses.add(conv(signal,kernel));		
		}
		return responses;
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
