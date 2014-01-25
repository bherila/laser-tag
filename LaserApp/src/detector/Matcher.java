package detector;


// The matcher performs template matching based on convolution etc.
class Matcher {
	
	// gateway
	static double[][] corr(double[][] signal, int width, int height,
			double[] kernel, int kernelWidth, int kernelHeight){
//		return ssdCorrRow(signal, width, height, kernel, kernelWidth, kernelHeight);
		return normXCorrRow(signal, width, height, kernel, kernelWidth, kernelHeight);
	}
	
	
	
	// inspired by: "http://stackoverflow.com/questions/13060757/
	// 2-dimensional-convolution-how-to-implement-in-java"
	static double[][] rowConv(double[][] signal, int width, int height,
					double[] kernel, int kernelWidth, int kernelHeight){
		
		int smallWidth = width - kernelWidth + 1;
		int smallHeight = height - kernelHeight + 1;
		double[][] output = new double[smallHeight][smallWidth];
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

	
	static double[][] ssdCorrRow(double[][]signal, int width, int height,
									double[] kernel, int kernelWidth, int kernelHeight){
		
		int smallWidth = width - kernelWidth + 1;
		int smallHeight = height - kernelHeight + 1;
		double[][] output = new double[smallHeight][smallWidth];
		double acc = 0.0; // accumulator
		
		double meanSignal = 42.0;
		double meanKernel = 13.0;
		double inc = 0.0;
		// perform row-wise matching (nothing in the y dimension)
		for(int r=0; r<smallHeight; r++){			
			// match every position in the line
			for(int c=0; c<smallWidth; c++){	
				
				// compute match
				acc = 0.0;
				for(int k=0; k<kernelWidth; k++){
					inc = ((signal[r][c+k]-meanSignal) * (kernel[k]-meanKernel));
					inc *= inc;
					acc += inc;
				}
				// store similarity measure in output
				output[r][c] = acc;
			}				
		}
	
		return output;
	}
	
	
	static double[][] normXCorrRow(double[][]signal, int width, int height,
									double[] kernel, int kernelWidth, int kernelHeight){
		
		int smallWidth = width - kernelWidth + 1;
		int smallHeight = height - kernelHeight + 1;
		double[][] output = new double[smallHeight][smallWidth];
		
		double meanPatch = 0.0;
		double meanKernel = 0.0;
		
		double acc = 0.0; // accumulator
		double inc = 0.0;
		
		double acc1 = 0.0;
		double inc1 = 0.0;
		
		double acc2 = 0.0;
		double inc2 = 0.0;	
		
		double sumPatch = 0.0;
		double sumKernel = 0.0;
		
		for(int k=0; k<kernelWidth; k++){
			sumKernel += kernel[k];
		}
		meanKernel = sumKernel/kernelWidth;
		
		for(int r=0; r<smallHeight; r++){			
			// match every position in the line
			for(int c=0; c<smallWidth; c++){	
				
				// compute mean of patch
				sumPatch = 0.0;
				for(int k=0; k<kernelWidth; k++){
					sumPatch += signal[r][c+k];
				}
				meanPatch = sumPatch/kernelWidth;
				
				acc = 0.0;
				for(int k=0; k<kernelWidth; k++){
					
					// numerator
					inc = ((signal[r][c+k]-meanPatch) * (kernel[k]-meanKernel));
					acc += inc*inc;
					
					// denominator left
					inc1 = (signal[r][c+k]-meanPatch);
					acc1 = inc1*inc1;
					
					// denominator right
					inc2 = (kernel[k]-meanKernel);
					acc2 = inc2*inc2;
				}

				// store similarity measure in output
				output[r][c] = acc / Math.sqrt(acc1 * acc2);
			}				
		}
	
		return output;
	}	
	
}
