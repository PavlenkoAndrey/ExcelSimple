package my.homeproject;

public class Operation extends Term
{
	private char operation;	
	
	public Operation(char opereation) {
		//super(Term.TermType.OPERATION);
		this.operation = opereation; 		
	}
	
	static boolean Check(char ch) {
		return ch == '+' || ch == '-' || ch == '*' || ch == '/';
	}
	
	static boolean Check(String s) {
		return s.length() == 1 && Check(s.charAt(0));
	}
	
	static boolean CheckPlus(char ch) {
		return ch == '+';
	}
	
	public char  AsChar() {
		return operation;
	}
}
