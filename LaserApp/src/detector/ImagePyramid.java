package detector;

import java.util.*;

class ImagePyramid {

	private List<double[][]> _scales;
	
	// use 1D array that represents a quad
	static ImagePyramid from1DQuad(double[] quad){
		System.out.println(">>> ImagePyramid.from1DQuad");
		
		int wh = (int)Math.floor(Math.sqrt(quad.length));	
		// convert 1D array to 2D hierarchical array (slower, but safer)
		// TODO: speed up with 1D arrays?
		double[][] twoD = new double[wh][wh];
		for(int r=0; r<wh; r++){
			for(int c=0; c<wh; c++){
				twoD[r][c] = quad[r*wh+c];
			}
		}
		ImagePyramid pyr = new ImagePyramid(twoD, wh, wh);
		System.out.println("<<< ImagePyramid.from1DQuad");
		return pyr;
	}
	
	// package private
	ImagePyramid(double[][] data, int height, int width){		
		// TODO: Find way of making scales (bicubic scaling)
		// (for now just use one scale)
		_scales = new ArrayList<double[][]>();
		_scales.add(data);
		// currently singleton list
		System.out.println("ImagePyramid.ImagePyramid: making list of scales");
	}
	
	// returns similarity measure of best match
	double match(Template temp){	
		System.out.println(">>> ImagePyramid.match");
		
		double[] kernel = temp.get1DPattern();
		double champion = 0.0;
		
		// for-each scale
		for(double[][] signal : _scales){	
			System.out.println("ImagePyramid.match: $$$ Iterating over another scale");
			
			// match each line
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
		
		System.out.println("<<< ImagePyramid.match");
		return champion;
	}	
	
	// max of 2D array
	private double maxOf(double[][] mat){
		int height = mat.length;
		int width = mat[0].length;
		double max = 0.0;
		for(int r=0; r<height; r++){
			for(int c=0; c<width; c++){
				max = (mat[r][c] > max) ? mat[r][c] : max;
			}
		}
		return max;
	}
	
}
