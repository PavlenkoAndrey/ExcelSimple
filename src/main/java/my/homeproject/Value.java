package my.homeproject;

public class Value extends Term
{
	private int value;
	
	public Value(String s) {
		//super(Term.TermType.VALUE);
		// Before calling the constructor should be checked whether s is positive integer
		value = Integer.parseInt(s); 
	}  

	public int AsInteger() {
		return value;
	}
	
	static public boolean Check(String s) {
		int val = 0; 
		try {
			val = Integer.parseInt(s); 
		} 
		catch(Exception ex) {
			return false;
		}; 
		return val >= 0;
	}
	
	// Check whether first character (not zero character!) is a digit
	// Length of s should be greater than one
	static public boolean IsFirstDigit(String s) {
		char ch = s.charAt(1);
		return (ch >= '0') && (ch <= '9');
	}
	
}

