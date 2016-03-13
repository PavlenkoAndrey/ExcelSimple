package my.homeproject.celldata;

public class CellText extends CellData {

	public CellText(String txtcell, String cellref) {
		super(txtcell, cellref);
	}
	
	public Object Calculate() {
		if (!getErrorMessage().isEmpty()) {
			return null;
		}
		if (isCalculationFinished()) {
			return getCalculatedValue();
		}
		String calculatedText = getTextOfCell().substring(1);
		setCalculatedValue(calculatedText);
		return calculatedText;
	}
	
}