package my.homeproject;

public class Reference extends Term {
	String reference;
	
	Reference(String reference) {
		//super(Term.TermType.REFERENCE);
		this.reference = reference;
	}
	
	static boolean Check(String s) {
		char ch0 = s.charAt(0);
		char ch1 = s.charAt(1);
		return s.length() == 2 && ch1 >= '0' && ch1 <= '9' && (ch0 >= 'a' && ch0 <= 'z' || ch0 >= 'A' && ch0 <= 'Z');
	}
	
	public String AsString() {
		return reference;
	}
}
