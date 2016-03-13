package my.homeproject.terms;

public class Reference extends Term {
	
	public Reference(String reference) {
		super(reference);
	}
	
	// Check whether s is reference
	static public boolean Check(String s) {
		return s.matches("^[a-zA-Z]\\d$");
	}
	
	// Convert (row, column) to cell reference
	static public String IntsToString(int i, int j) {
		return "" + (char)(j + 'A') + (char)(i + 1 + '0');
	}
	
	// Convert cell reference to (row, column)
	static public int StringToRow(String reference) {
		return (int)(reference.charAt(1) - '0') - 1;
	}

	static public int StringToColumn(String reference) {
		return (int)(reference.charAt(0) - 'A');		
	}
	
}
