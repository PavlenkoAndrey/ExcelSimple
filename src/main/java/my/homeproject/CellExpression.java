package my.homeproject;

import java.util.*;

public class CellExpression extends CellValue {

	private ArrayList<Term> terms = new ArrayList<Term>();
	
	private boolean calculationStarted;
	
	public CellExpression(String textOfCell, String referenceToCell) 
	{
		super(textOfCell, referenceToCell);
		calculationStarted = false;
	}
	
	public Object Calculate()
	{
		if (calculationStarted) {
			setErrorMessage(ErrorTypes.GetMessage(ErrorTypes.RECURSION));
			calculationStarted = false;
			return setCalculatedValue(null);			
		}
		if (isDataCalculated()) {
			return getCalculatedValue();
		}
		try 
		{
			String expr = getTextOfCell().substring(1).toUpperCase();
			Parse(expr);
			calculationStarted = true;
			Object result = CalculationCore();
			calculationStarted = false;
			return result;
		}
		catch (Exception er) {
			calculationStarted = false;
			setErrorMessage(er.getMessage());
			return setCalculatedValue(null);
		}
	}
	
	private Object CalculationCore() throws ExcelExceptions
	{
		int sum = 0;			
		char nextOperation = '+';
		boolean  shouldBeOperation = true;
		for (int i = 0; i < terms.size(); ++i) {
			Term term = terms.get(i);
			shouldBeOperation = !shouldBeOperation;
			// Correct terms order: First - operation, second - Value|Reference, third - operation, ... , last - Value|Reference
			if (shouldBeOperation) {
				nextOperation = term.AsChar();
				continue;
			}
			Integer val = 0;
			if (term instanceof Reference)
			{
				Object object = Excel.getCellByReference(term.AsString()).Calculate();
				if (!(object instanceof Integer)) {
					throw new ExcelExceptions(ErrorTypes.CANNOT_CALCULATE);
				}
				// Get the value that was calculated before
				val = (Integer)object;
			} else {
				val = term.AsInteger();
			}
			switch (nextOperation) {
				case '+': sum += val; break;
				case '-': sum -= val; break;
				case '*': sum *= val; break;
				case '/': if (val == 0) {
							throw new ExcelExceptions(ErrorTypes.DIVISION_BY_ZERO);
							}
							sum /= val; break;
			}
		}
		return setCalculatedValue(sum);
	}
	
	private void Parse(String expression) throws ExcelExceptions
	{
		// Some checks
		// if ( expression.matches("(^\\+?\\d.*)|^[a-zA-Z].*") ) { // Unfortunately it does not work // ^(\+?\d)|^[a-zA-Z]
		if ( expression.length() == 0 || (Operation.Check(expression.charAt(0)) && (expression.charAt(0) != '+')) ) {
			// First sign should be+ not -,*,/
			throw new ExcelExceptions(ErrorTypes.SYNTAX_ERROR);
		}
		if ((expression.length() > 1) && (expression.charAt(0) == '+') && !Value.IsFirstDigit(expression) )	{
			// The correct expressions: =+12-A3 or =12-A3,
//          //               incorrect: =-A3 (according to the technical requirements)
			throw new ExcelExceptions(ErrorTypes.SYNTAX_ERROR);
		}
		if (Operation.CheckPlus(expression.charAt(0))) {
			// Skip first plus
			expression = expression.substring(1);
		}
		
		StringTokenizer st = new StringTokenizer(expression, "+-*/", true);
		Term term;
		boolean  shouldBeOperation = true;
		while (st.hasMoreTokens()) {
			String nextTerm = st.nextToken();
			boolean isTermOperation = Operation.Check(nextTerm);
			shouldBeOperation = !shouldBeOperation; 
			if (isTermOperation != shouldBeOperation) {
				// Correct terms order: First - operation, second - Value|Reference, third - operation, ... , last - Value|Reference
				throw new ExcelExceptions(ErrorTypes.INCORRECT_TERMS_ORDER);
			}
			if (isTermOperation) {
				term = new Operation(nextTerm.charAt(0));
			} else if (Value.Check(nextTerm)) {
				term = new Value(nextTerm);
			} else if (Reference.Check(nextTerm)) {
				term = new Reference(nextTerm);
			} else {
				throw new ExcelExceptions(ErrorTypes.SYNTAX_ERROR);
			}
			terms.add(term);			
		}
		if (shouldBeOperation) {
			// Last term should be Value|Reference not operation
			throw new ExcelExceptions(ErrorTypes.SYNTAX_ERROR);
		}
	}
	
	public String GetResult()
	{
		if (getErrorMessage() != "") {
			return "#" + getErrorMessage();
		} if (isDataCalculated()) {
			return "" + getCalculatedValue();
		} else {
			// For debugging
			return getTextOfCell();
		}
		
		/*
		if (isDataCalculated()) {
			return "" + getCalculatedValue();
		}
		if (isDataCalculated() == false && getErrorMessage() == "") {
			// Cases like cell D2 = D1, and cell D1 = D2 or or in some cells syntax errors
			return "#" + ErrorTypes.GetMessage(ErrorTypes.CANNOT_EVALUATE);
		}
		return "#" + getErrorMessage();
		*/
	}

}
