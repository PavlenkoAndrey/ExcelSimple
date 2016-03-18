package my.homeproject.celldata;

import java.util.*;

import my.homeproject.exceptions.ExcelExceptions;
import my.homeproject.Excel;
import my.homeproject.terms.Operation;
import my.homeproject.terms.Reference;
import my.homeproject.terms.Term;

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
		if (isCalculationFinished()) {
			return getCalculatedValue();
		}
		if (calculationStarted) {
			setErrorMessage(ExcelExceptions.RECURSION);
			calculationStarted = false;
			setCalculatedValue(null);
			return null;			
		}
		Integer result = null;
		try 
		{
			String expr = getTextOfCell().substring(1).toUpperCase();
			Parse(expr);
			calculationStarted = true;
			result = CalculationCore();
			calculationStarted = false;
		}
		catch (Exception er) {
			if (!getErrorMessage().isEmpty()) {
				// Case of recursion
				return null;
			}
			calculationStarted = false;
			setErrorMessage(er.getMessage() != null ? er.getMessage() : ExcelExceptions.SYNTAX_ERROR);
			result = null;
		}
		setCalculatedValue(result);
		return result;		
	}
	
	private Integer CalculationCore() throws ExcelExceptions
	{
		int sum = 0;			
		char currentOperation = '+';
		for (int i = 0; i < terms.size(); ++i) {
			Term term = terms.get(i);
			// Correct terms order: First - operation, second - Value|Reference, third - operation, ... , last - Value|Reference
			if (i % 2 == 1) {
				currentOperation = term.AsChar();
				continue;
			}
			Integer result = 0;
			if (term instanceof Reference)
			{
				Object object = Excel.getCellByReference(term.AsString()).Calculate();
				if (!(object instanceof Integer)) {
					throw new ExcelExceptions(ExcelExceptions.CANNOT_CALCULATE);
				}
				// Get the value that was calculated before
				result = (Integer)object;
			} else {
				result = term.AsInteger();
			}
			switch (currentOperation) {
				case '+': sum += result; break;
				case '-': sum -= result; break;
				case '*': sum *= result; break;
				case '/': if (result == 0) {
							throw new ExcelExceptions(ExcelExceptions.DIVISION_BY_ZERO);
							}
							sum /= result; break;
			}
		}
		return sum;
	}
	
	private void Parse(String expression) throws ExcelExceptions
	{
		// Verification of the sign before the first token (should be plus or absent for value and absent for reference)
		if ( !expression.matches("(^\\+?\\d.*)|(^[a-zA-Z].*)") ) {
            // The correct expressions: =+12-A3, =12-A3
            //               incorrect: =-A3, =-12 (according to the technical requirements)
			throw new ExcelExceptions(ExcelExceptions.SYNTAX_ERROR);
		}
		if (Operation.CheckPlus(expression.charAt(0))) {
			// Skip first plus
			expression = expression.substring(1);
		}
		
		StringTokenizer st = new StringTokenizer(expression, "+-*/", true);
		boolean  shouldBeOperation = true;
		while (st.hasMoreTokens()) {
			String nextTerm = st.nextToken();
			boolean isTermOperation = Operation.Check(nextTerm);
			shouldBeOperation = !shouldBeOperation; 
			if (isTermOperation != shouldBeOperation) {
				// Correct terms order: First - operation, second - Value|Reference, third - operation, ... , last - Value|Reference
				throw new ExcelExceptions(ExcelExceptions.INCORRECT_TERMS_ORDER);
			}
			terms.add(Term.CreateObject(nextTerm));			
		}
		if (shouldBeOperation) {
			// Last term should be Value|Reference not operation
			throw new ExcelExceptions(ExcelExceptions.SYNTAX_ERROR);
		}
	}
	
}
