package detector;


// The matcher performs template matching based on convolution etc.
public class Matcher {

	// courtesy of "http://stackoverflow.com/questions/13060757/2-dimensional-convolution-how-to-implement-in-java"
	
	// TODO: Implement 1D convolution
	// NOTE: Exhaustive of step-width?
	static double[][] rowConv(double[][] signal, int width, int height,
					double[] kernel, int kernelWidth, int kernelHeight){
		
		int smallWidth = width - kernelWidth + 1;
		int smallHeight = height - kernelHeight + 1;
		double[][] output = new double[smallWidth][smallHeight];
		double acc = 0.0; // accumulator
		// perform row-wise matching (nothing in the y dimension)
		for(int r=0; r<smallHeight; r++){			
			// match every position in the line
			for(int c=0; c<smallWidth; c++){	
				
				// compute match
				acc = 0.0;
				for(int k=0; k<kernelWidth; k++){
					acc += (signal[r][c+k] * kernel[k]);
				}
				// store similarity measure in output
				output[r][c] = acc;
			}				
		}
		return output;
	}
	
	
	// TODO: Implement 1D normalized cross-correlation
	
}
