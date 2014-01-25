package detector;

// wrapper class for visual templates
class Template {

	private final double[] _pattern;
	
	// initialize pattern (shouldn't change)
	Template(double[] pattern){
		_pattern = pattern;
	}
	
	// return pattern to be matched in 1D
	double[] get1DPattern() {	
		return _pattern;
	}
	
}
