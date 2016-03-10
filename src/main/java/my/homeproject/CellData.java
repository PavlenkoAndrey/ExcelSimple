package my.homeproject;

import java.lang.String; 

public abstract class CellData {

	// The original text of the cell
	private String textOfCell;
	// The reference to the cell (row, col) like A2, C4
	private String referenceToCell;
	private String errorMessage;
	private boolean isDataCalculated;
	
	public CellData(String textOfCell, String referenceToCell)
	{
		this.referenceToCell = referenceToCell; 
		this.textOfCell = textOfCell; 
		isDataCalculated = false; 
		errorMessage="";
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
		this.errorMessage = errorMessage;
	}
	public boolean isDataCalculated() {
		return isDataCalculated;
	}
	public void setDataCalculated(boolean isCalculated) {
		this.isDataCalculated = isCalculated;
	}

	abstract public int Calculate();
	abstract public String GetResult();	
}
