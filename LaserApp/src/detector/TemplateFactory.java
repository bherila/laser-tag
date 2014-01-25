package detector;

import java.util.*;

// makes all the patterns we need
class TemplateFactory {

	final static double[] FOO_PATTERN = {0, 1, 0};
	final static double[] BAR_PATTERN = {1, 0, 1};
	final static double[] BAZ_PATTERN = {0, 0, 1};
	
	private List<double[]> _patterns;
	
	TemplateFactory(){
		// set up patterns
		_patterns = new ArrayList<double[]>();
		_patterns.add(FOO_PATTERN);
		_patterns.add(BAR_PATTERN);
		_patterns.add(BAZ_PATTERN);
	}
	
	// return list of all patterns
	List<Template> getPatternList(){
		
		List<Template> lot = new ArrayList<Template>();
		for(double[] pat : _patterns){
			lot.add(new Template(pat));
		}		
		return lot;
	}
	
}
