package detector;

import java.util.*;

// test functionality
public class DetectorMain {

	static final int[] SIGNAL = {1, 2, 3, 4,
								5, 6, 7, 8,
								9, 10, 11, 12,
								13, 60, 125, 255};
	
	
	
	public static void main(String[] argv){		
//		IDetector det = DetectorFactory.getDetector();	
		
		// int[] sig = SIGNAL;
//		Random rand = new Random();
//		for(int i=0; i<1; i++){
//			int[] sig = randomSignal(500);
//			int hit = det.detect(sig, sig, sig);		
//			System.out.printf("Player %d has been hit!\n", hit);
//		}
//		return;		
		int nPlayers = 1;
		IDetector det = DetectorFactory.getDetector(nPlayers);		
		int[] sig = randomSignal(500);		
		int whoWasHit = det.detect(sig, sig, sig);
		
		System.out.println("Player " + whoWasHit + " was hit!");
		
	}
	
	public static int[] randomSignal(int wh){
		
		Random rand = new Random();
		
		int[] data = new int[wh*wh];
		for(int j=0; j<wh; j++){
			for(int i=0; i<wh; i++){
				data[j*wh+i] = rand.nextInt(255);
			}
		}
		
		return data;
		
	}
	
}
