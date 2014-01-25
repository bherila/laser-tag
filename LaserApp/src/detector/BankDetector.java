package detector;

import java.util.*;

// Detector that operates with a FilterBank
public class BankDetector implements IDetector {

	List<FilterBank> _filterBanks;
	int _nPlayers;
	
	BankDetector(int nPlayers){
		_nPlayers = nPlayers;
		for(int i=0; i<nPlayers; i++){
			_filterBanks.add(FilterBankFactory.getNumber(i));
		}
	}
	
	@Override
	public int detect(int[] red, int[] green, int[] blue) {
		
		// TODO: Figure out how to deal with colors
		double[] dred = Util.normalizePixelArray(red);
//		double[] dgreen = Util.normalizePixelArray(green);
//		double[] dblue = Util.normalizePixelArray(blue);
		
		// use a different filter bank for each player
		
		// leave the signal as it is and make a filter bank
		FilterBank bank1 = new FilterBank();
		double[][] redSignal = Util.array2square(dred);	
		
		
		ResponsePyramid resp1 = bank1.match(redSignal);
		
		
		// find global max
		
		
		// use a different template for each
		double[] maxima = new double[_nPlayers];
		for(int i=0; i<_nPlayers; i++){		
			ResponsePyramid resp = _filterBanks.get(i).match(redSignal);
			maxima[i] = resp.getGlobalMax();
		}
		
		// find which player is hit
		int whoWasHit = Util.indexOfMax(maxima);
		
		System.out.printf("Player %d has been hit\n", whoWasHit);
		
		return whoWasHit;
		
	
		
		
		
		
	}

}
