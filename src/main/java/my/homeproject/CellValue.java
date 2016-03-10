package my.homeproject;

import java.util.Hashtable;

public class CellValue extends CellData 
{
	protected static Hashtable<String, Integer> calculatedValuesTable = new Hashtable<String, Integer>(); // Table for calculated values
	protected int valueCalculated = 0;
	public CellValue(String textOfCell, String referenceToCell)
	{
		super(textOfCell, referenceToCell);
	}
	
	public int Calculate()
	{
		if (isDataCalculated()) {
			return 1;
		}
		if (getErrorMessage() != "") {
			return 0;
		}
		try {
			valueCalculated = Integer.parseInt(getTextOfCell());
		} catch (NumberFormatException ex) {
			setErrorMessage(ErrorTypes.GetMessage(ErrorTypes.SYNTAX_ERROR));
			return 0;
		}
		if (valueCalculated < 0) {
			setErrorMessage(ErrorTypes.GetMessage(ErrorTypes.SYNTAX_ERROR));
			return 0;
		}
		setDataCalculated(true);
		calculatedValuesTable.put(getReferenceToCell(), valueCalculated);
		return 1;
	}
	
	public String GetResult()
	{
		if (isDataCalculated()) {
			return "" + valueCalculated;
		} else if (getErrorMessage() != "") {
			return "#" + getErrorMessage();
		} else {
			return getTextOfCell();
		}
	}
}
