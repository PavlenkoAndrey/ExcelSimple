package my.homeproject;

public class CellText extends CellData {

	private String calculatedText;
	public CellText(String txtcell, String cellref) {
		super(txtcell, cellref);
		calculatedText = "";
	}
	
	public Object Calculate() {
		if (getErrorMessage() != "") {
			return null;
		}
		if (isDataCalculated()) {
			return calculatedText;
		}
		calculatedText = getTextOfCell().substring(1);
		setDataCalculated(true);
		return calculatedText;
	}
	
	public String GetResult() {
		if (isDataCalculated()) {
			return calculatedText;
		} if (getErrorMessage() != "") {
			return "#" + getErrorMessage();
		} else {
			return getTextOfCell();
		}
	}
}
