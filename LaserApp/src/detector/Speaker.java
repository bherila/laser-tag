package detector;

// utility class that manages verbosity
public class Speaker {

	private static final boolean DETECTOR_FACTORY_GET_DETECTOR = true;
	
	// whenver I give feedback, I set it here
	static boolean getDetector() {return DETECTOR_FACTORY_GET_DETECTOR;}
}
