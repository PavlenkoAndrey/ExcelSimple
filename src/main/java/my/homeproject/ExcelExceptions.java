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
	OPEN_INPUT_FILE_ERROR,
	OPEN_OUTPUT_FILE_ERROR,
	INCORRECT_DATA_IN_INPUT,
	DIVISION_BY_ZERO,
	SYNTAX_ERROR,
	INCORRECT_VALUE,
	INCORRECT_TERMS_ORDER,
	CANNOT_EVALUATE,
	PARSER_ERROR,
	EMPTY_CELL;
	
	public static String GetMessage(ErrorTypes et)
	{
		switch (et) {
			case NONE:					return "";
			case OPEN_INPUT_FILE_ERROR:	return "Open input file error";
			case OPEN_OUTPUT_FILE_ERROR:return "Open output file error";
			case INCORRECT_DATA_IN_INPUT:return "Incorect input data";
			case DIVISION_BY_ZERO:		return "Division by zero";
			case SYNTAX_ERROR:			return "Syntax error";
			case INCORRECT_VALUE:		return "Incorrect value";
			case INCORRECT_TERMS_ORDER:	return "Incorrect terms order";
			case CANNOT_EVALUATE:		return "Cannot calculate";
			case PARSER_ERROR:			return "Parser error";
			case EMPTY_CELL:			return "Empty cell";
		}
		return "Unknown error";		
	}
}