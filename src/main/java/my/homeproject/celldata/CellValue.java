package my.homeproject.celldata;

import my.homeproject.exceptions.ExcelExceptions;

public class CellValue extends CellData 
{
	public CellValue(String textOfCell, String referenceToCell)
	{
		super(textOfCell, referenceToCell);
	}
	
	public Object Calculate()
	{
		if (isCalculationFinished()) {
			return getCalculatedValue();
		}
		Integer result = null;
		try {
			result = Integer.parseInt(getTextOfCell());
			if (result < 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException ex) {
			setErrorMessage(ExcelExceptions.SYNTAX_ERROR);
			result = null;
		}
		setCalculatedValue(result);
		return result;
	}
}
