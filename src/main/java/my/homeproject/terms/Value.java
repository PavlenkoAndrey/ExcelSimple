package my.homeproject.terms;

public class Value extends Term
{
	
	public Value(String s) throws NumberFormatException {
			super(Integer.parseInt(s)); 
	}  

	// Check whether s is a positive integer
	static public boolean Check(String s) {
		return s.matches("^\\+?\\d+$");
	}
	
}

