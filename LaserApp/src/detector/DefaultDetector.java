package detector;

public class DefaultDetector implements IDetector {

	@Override
	public int detect(int[] red, int[] green, int[] blue) {
		
		// Ok, what needs to be done?
		// I need some access to a template
		// perform math to match them
		
		
		// generate pyramid of images
		
		
		// how to compute the normalized cross-correlation?
		// make a comparator class or something?
		// Matcher class for ImagePyramid?
		// new ImagePyramid
		
		// find a way of bicubic rescaling
		
		// Knuth style: Top down, then bottom up!
		
		// start with one color
		pyr = new ImagePyramid(red);
		fac = new TemplateFactory();
		
		// what does this return?
		// a normalized something?
		// just boolean if there was a match?
		pyramid.match(fac.getTemp("foo"));
		
		// match all with all?
		
		
		
		// TODO Auto-generated method stub
		return 0;
	}

}
