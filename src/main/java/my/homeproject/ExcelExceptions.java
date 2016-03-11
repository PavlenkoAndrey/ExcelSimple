package my.homeproject;

public class ExcelExceptions extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ExcelExceptions(ErrorTypes err)
	{
		super(ErrorTypes.GetMessage(err));
	}
}

enum ErrorTypes
{
	NONE,
	INCORRECT_DATA_IN_INPUT,
	DIVISION_BY_ZERO,
	SYNTAX_ERROR,
	INCORRECT_TERMS_ORDER,
	CANNOT_CALCULATE,
	RECURSION;
	
	public static String GetMessage(ErrorTypes et)
	{
		switch (et) {
			case NONE:					return "";
			case INCORRECT_DATA_IN_INPUT:return "Incorect input data";
			case DIVISION_BY_ZERO:		return "Division by zero";
			case SYNTAX_ERROR:			return "Syntax error";
			case INCORRECT_TERMS_ORDER:	return "Incorrect terms order";
			case CANNOT_CALCULATE:		return "Cannot calculate";
			case RECURSION:				return "Recursion";
		}
		return "Unknown error";		
	}
}