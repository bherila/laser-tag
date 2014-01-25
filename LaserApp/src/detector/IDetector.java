package detector;

public interface IDetector {

	/**
	 * 
	 * @author Robin Martens
	 * 
	 * @brief 	Consume red, blue and green channels and return
	 * 			id of target hit (0 indicates no hit)
	 */
	int detect(int[] red, int[] green, int[] blue);
	
}
