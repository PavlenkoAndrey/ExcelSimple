package my.homeproject;

public class CellText extends CellData {

	private String calculatedText;
	public CellText(String txtcell, String cellref) 
	{
		super(txtcell, cellref);
		calculatedText = "";
	}
	
	public int Calculate()
	{
		if (isDataCalculated()) {
			return 1;
		}
		calculatedText = getTextOfCell().substring(1);
		setDataCalculated(true);
		return 1;
	}
	
	public String GetResult()
	{
		if (isDataCalculated()) {
			return calculatedText;
		} if (getErrorMessage() != "") {
			return "#" + getErrorMessage();
		} else {
			return getTextOfCell();
		}
	}
}
