package my.homeproject.celldata;

import java.lang.String; 

public abstract class CellData {
	
	// The original text of the cell
	private String textOfCell;
	// The reference to the cell (row, col) like A2, C4
	private String referenceToCell;
	private String errorMessage;
	private boolean calculationFinished;
	
	private Object calculatedValue;

	public CellData(String textOfCell, String referenceToCell)
	{
		this.referenceToCell = referenceToCell; 
		this.textOfCell = textOfCell; 
		calculationFinished = false; 
		errorMessage = "";
	}
	
	public String getTextOfCell() {
		return textOfCell;
	}

	public String getReferenceToCell() {
		return referenceToCell;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		calculationFinished = true;
		this.errorMessage = errorMessage;
	}
	public boolean isCalculationFinished() {
		return calculationFinished;
	}

	public Object getCalculatedValue() {
		return calculatedValue;
	}

	public void setCalculatedValue(Object calculatedValue) {
		calculationFinished = true;
		this.calculatedValue = calculatedValue;
	}
	
	abstract public Object Calculate();
	
	//abstract public String GetResult();
	public String GetResult()
	{
		if (!getErrorMessage().isEmpty()) {
			return "#" + getErrorMessage();
		} if (isCalculationFinished()) {
			return "" + getCalculatedValue();
		} else {
			// For debugging
			return getTextOfCell();
		}
	}	
}
