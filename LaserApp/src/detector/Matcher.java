package detector;


// The matcher performs template matching based on convolution etc.
class Matcher {
	
	// inspired by: "http://stackoverflow.com/questions/13060757/
	// 2-dimensional-convolution-how-to-implement-in-java"
	static double[][] rowConv(double[][] signal, int width, int height,
					double[] kernel, int kernelWidth, int kernelHeight){
		
//		System.out.println(">>> Matcher.rowConv");
		
		int smallWidth = width - kernelWidth + 1;
		int smallHeight = height - kernelHeight + 1;
		double[][] output = new double[smallHeight][smallWidth];
		double acc = 0.0; // accumulator
		
		// what do signal and kernel look like?
//		System.out.println("Signal: ");
//		for(int j=0; j<height; j++){
//			for(int i=0; i<width; i++){
//				System.out.printf("%f ", signal[j][i]);	
//			}
//			System.out.println();
//		}
		
		
//		System.out.println("kernel: ");
//		int len = kernel.length;
//		for(int i=0; i<len; i++){
//			System.out.printf("%f ",  kernel[i]);
//		}
//		System.out.println();
		
//		System.out.printf("smallHeight: %d\n", smallHeight);
//		System.out.printf("smallWidth: %d\n", smallWidth);

		
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
		
//		System.out.println("<<< Matcher.rowConv");
		return output;
	}
		
	// TODO: Implement 1D normalized cross-correlation
	

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
				
				// account for normalization
				
				// store similarity measure in output
				output[r][c] = acc;
			}				
		}
		
		return output;
	}
	
}
