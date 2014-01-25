package detector;

// useful static functions to be shared (like "Math" class)
public abstract class Util {

	// convert int [0,255] to double [0.0,1.0]
	public static double[] normalizePixelArray(int[] input){
		int size = input.length;
		double[] output = new double[size];
		for(int i=0; i<size; i++){
			output[i] = ( (double)input[i] ) / 255.0;
			// System.out.printf("DefaultDetector.normalizePixelArray: input[i]: %d, output[i]: %f \n", input[i], output[i]);
		}
		return output;
	}
	
	public static int indexOfMax(double[] data){
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
	
	// max of 2D array
	public static double maxOf(double[][] mat){
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
	
	// convert array to square matrix
	public static double[][] array2square(double[] data){
		int wh = (int)Math.floor(Math.sqrt(data.length)); 
		double[][] twoD = new double[wh][wh]; 
		for(int r=0; r<wh; r++){
			for(int c=0; c<wh; c++){
				twoD[r][c] = data[r*wh+c];
			}
		}
		return twoD;
	}
	
}
