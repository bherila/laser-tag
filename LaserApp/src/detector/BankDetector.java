package detector;

import java.util.*;

// Detector that operates with a FilterBank
public class BankDetector implements IDetector {

	List<FilterBank> _filterBanks;
	int _nPlayers;
	
	BankDetector(int nPlayers){
		_nPlayers = nPlayers;
		_filterBanks = new ArrayList<FilterBank>();
		for(int i=0; i<nPlayers; i++){
			_filterBanks.add(FilterBankFactory.getNumber(i));
		}
	}
	
	@Override
	public int detect(int[] red, int[] green, int[] blue) {
		System.out.println(">>> BankDetector.detect");
		
		// TODO: Figure out how to deal with colors
		double[] dred = Util.normalizePixelArray(red);
//		double[] dgreen = Util.normalizePixelArray(green);
//		double[] dblue = Util.normalizePixelArray(blue);
		
		double[][] redSignal = Util.array2square(dred);	
		// use a different template for each
		double[] maxima = new double[_nPlayers];
		// use a different filter bank for each player
		
		for(int i=0; i<_nPlayers; i++){		
			
			System.out.println("BankDetector: match signal for player " + i);		
			ResponsePyramid resp = _filterBanks.get(i).match(redSignal);
			System.out.println("BankDetector: signal for player " + i + " has been matched");
			
			
			System.out.println("BankDetector.detect: search for global max");
			maxima[i] = resp.getGlobalMax();
			System.out.println("BankDetector.detect: global max found");

		}
		
		for(int i=0; i<_nPlayers; i++){
			System.out.println("BankDetector.detect: Max response for player " + i + ": " + maxima[i]);
		}
		
		// find which player is hit
		int whoWasHit = Util.indexOfMax(maxima);
		System.out.printf("Player %d has been hit\n", whoWasHit);
		
		System.out.println("BankDetector.detect: max found; stop and think!");
		System.exit(0);
		
		return whoWasHit;	
	}

}
