package my.homeproject.terms;

public class Operation extends Term
{

	public Operation(char operation) {
		super(operation);
	}
	
	// Check whether s is a operation
	public static boolean Check(String s) {
		return (s.length() == 1) && ((s.charAt(0) == '+') || (s.charAt(0) == '-') || (s.charAt(0) == '*') || (s.charAt(0) == '/'));
	}

	public static boolean CheckPlus(char ch) {
		return ch == '+';
	}

}
