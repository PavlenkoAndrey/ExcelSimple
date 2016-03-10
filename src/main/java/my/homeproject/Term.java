package my.homeproject;

public class Term {
	
	enum TermType {
		OPERATION,
		VALUE,
		REFERENCE
	}
	protected TermType termType;	

	public Term(TermType trmtype) {
		termType = trmtype;
	}
	
	public boolean CheckType(TermType termType) {
		return this.termType == termType;
	}
	
	public int AsInteger() {
		assert(false);
		return 0;
	}
	
	public String AsString() {
		assert(false);
		return "";
	}
	
	public char	AsChar() {
		assert(false);
		return ' ';
	}
}
