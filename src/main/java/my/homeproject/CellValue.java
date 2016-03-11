package my.homeproject;

import java.util.Hashtable;

public class CellValue extends CellData 
{
	//protected static Hashtable<String, Integer> calculatedValuesTable = new Hashtable<String, Integer>(); // Table for calculated values
	private Integer calculatedValue;

	public CellValue(String textOfCell, String referenceToCell)
	{
		super(textOfCell, referenceToCell);
		calculatedValue = 0;
	}
	
	public Integer getCalculatedValue() {
		return calculatedValue;
	}

	public Integer setCalculatedValue(Integer calculatedValue) {
		setDataCalculated(true);
		this.calculatedValue = calculatedValue;
		return calculatedValue; 
	}
	
	public Object Calculate()
	{
		if (isDataCalculated()) {
			return calculatedValue;
		}
		try {
			calculatedValue = Integer.parseInt(getTextOfCell());
			if (calculatedValue < 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException ex) {
			setErrorMessage(ErrorTypes.GetMessage(ErrorTypes.SYNTAX_ERROR));
			return setCalculatedValue(null);
		}
		return setCalculatedValue(calculatedValue);
		//calculatedValuesTable.put(getReferenceToCell(), valueCalculated);
	}
	
	public String GetResult()
	{
		if (getErrorMessage() != "") {
			return "#" + getErrorMessage();
		} if (isDataCalculated()) {
			return "" + calculatedValue;
		} else {
			return getTextOfCell();
		}
	}
}
