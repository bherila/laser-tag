package detector;

public class FilterBankFactory {

	static double[][] BASE_PATTERNS = { {0,1,0, 0,1}, {0,1,0, 1,0}, {0,1,0, 1,1}, {0,1,0, 0,0} };
		
	// return filter bank for player n
	static FilterBank getNumber(int n){
		// simplification
		return new FilterBank(BASE_PATTERNS[n]);
	}
	
}
