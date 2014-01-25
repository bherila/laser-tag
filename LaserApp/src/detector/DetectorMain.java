package detector;

// test functionality
public class DetectorMain {

	static final int[] SIGNAL = {1, 2, 3, 4,
								5, 6, 7, 8,
								9, 10, 11, 12,
								13, 60, 125, 255};
	
	public static void main(String[] argv){		
		IDetector det = DetectorFactory.getDetector();		
		int hit = det.detect(SIGNAL, SIGNAL, SIGNAL);		
		System.out.printf("Player %d has been hit!\n", hit);		
		return;
	}
	
}
