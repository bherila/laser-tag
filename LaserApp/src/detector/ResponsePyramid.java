package detector;

import java.util.*;

// I'm using a ton of types, but that keeps the code clean
// I'm so into Haskell!

// wrapper for output of FilterBank
// encapsulates convenient functions
public class ResponsePyramid {
	
	private final List<double[][]> _resp;
	
	public ResponsePyramid(List<double[][]> responses){
		_resp = responses;
	}
	
	// Pool across scale and translation
	double getGlobalMax(){		
		double max = Double.NEGATIVE_INFINITY;	
		for(double[][] scale : _resp){			
			int height = scale.length;
			int width = scale[0].length;			
			for(int r=0; r<height; r++){
				for(int c=0; c<width; c++){
					max = (scale[r][c] > max) ? scale[r][c] : max;
				}
			}	
		}	
		return max;
	}
	
	// Find coordinates of global max
	// {scale, row, colum}
	double locateGlobalMax(){
		double max = Double.NEGATIVE_INFINITY;
		
		int whichScale = 0;
		int whichRow = 0;
		int whichColumn = 0;
		
		double[][] scale;		
		int nScales = _resp.size();
		for(int s=0; s<nScales; s++){
			scale = _resp.get(s);
			int height = scale.length;
			int width = scale[0].length;			
			for(int r=0; r<height; r++){
				for(int c=0; c<width; c++){
					max = (scale[r][c] > max) ? scale[r][c] : max;				
					if(scale[r][c] > max){
						max = scale[r][c];
						whichScale = s;
						whichRow = r;
						whichColumn = c;	
					}
				}
			}	
		}	
		double[] resp = {whichScale, whichRow, whichColumn};
		return resp;
	}
	
}
