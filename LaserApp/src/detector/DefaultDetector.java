package detector;


// where to store the patterns?

class DefaultDetector implements IDetector {

	// package private constructor to be called by DetectorFactory
	DefaultDetector(){
		// no-op
	}
	
	@Override
	public int detect(int[] red, int[] green, int[] blue) {
		
		// run the code on all channels

		// Knuth style: Top down, then bottom up!
		
		// start with one color
		pyr = new ImagePyramid(red);
		fac = new TemplateFactory();
		
		// what does this return?
		// a normalized something?
		// just boolean if there was a match?
		double best = pyramid.match(fac.getTemp("foo"));
		
		// match all with all?
		
		
		
		
		
		// TODO Auto-generated method stub
		return 0;
	}

}
