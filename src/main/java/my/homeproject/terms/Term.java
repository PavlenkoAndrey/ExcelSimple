package my.homeproject.terms;

import my.homeproject.exceptions.ExcelExceptions;

public class Term {
	private Object value;
	
	Term(Object value) {
		this.value = value;
	}
	
	public static Term CreateObject(String sterm) throws ExcelExceptions {
		if (Operation.Check(sterm)) {
			return new Operation(sterm.charAt(0));
		} else if (Value.Check(sterm)) {
			return new Value(sterm);
		} else if (Reference.Check(sterm)) {
			return new Reference(sterm);
		} else {
			throw new ExcelExceptions(ExcelExceptions.SYNTAX_ERROR);
		}
	}
	
	public int AsInteger() {
		return (int)value;
	}
	
	public String AsString() {
		return (String)value;
	}
	
	public char	AsChar() {
		return (char)value;
	}
}
