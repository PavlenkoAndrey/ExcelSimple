package my.homeproject.terms;

public class Reference extends Term {
	
	public Reference(String reference) {
		super(reference);
	}
	
	// Check whether s is reference
	public static boolean Check(String s) {
		return s.matches("^[a-zA-Z]\\d$");
	}
	
	// Convert (row, column) to cell reference
	public static String IntsToString(int i, int j) {
		return "" + (char)(j + 'A') + (char)(i + 1 + '0');
	}
	
	// Convert cell reference to (row, column)
	public static int StringToRow(String reference) {
		return (int)(reference.charAt(1) - '0') - 1;
	}

	public static int StringToColumn(String reference) {
		return (int)(reference.charAt(0) - 'A');		
	}
	
}
