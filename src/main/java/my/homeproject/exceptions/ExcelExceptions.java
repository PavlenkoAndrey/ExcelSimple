package my.homeproject.exceptions;

public class ExcelExceptions extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String INCORRECT_INPUT_DATA = "Incorect input data";
	public static final String DIVISION_BY_ZERO = "Division by zero";
	public static final String SYNTAX_ERROR = "Syntax error";
	public static final String INCORRECT_TERMS_ORDER = "Incorrect terms order";
	public static final String CANNOT_CALCULATE = "Cannot calculate";
	public static final String RECURSION = "Recursion";
	public static final String INCORRECT_REFERENCE = "Incorrect reference";
	public static final String RUNTIME_CELL_REFERENCE = "Runtime exception: incorrect reference";
	
	public ExcelExceptions(String s)
	{
		super(s);
	}
}
	
