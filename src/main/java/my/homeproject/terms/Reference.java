package my.homeproject.terms;

public class Reference extends Term {
	
	public Reference(String reference) {
		super(reference);
	}
	
	// Check whether s is reference
	static public boolean Check(String s) {
		return s.matches("^[a-zA-Z]\\d$");
	}
	
}
