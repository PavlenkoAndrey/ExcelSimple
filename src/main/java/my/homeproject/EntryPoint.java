package my.homeproject;

public class EntryPoint {

	public static void main(String[] args) {
		String input = (args.length >= 1) ? args[0] : "cin";
		String output = (args.length >= 2) ? args[1] : "cout";

        try {
        	Excel excel = new Excel(input, output);
        	excel.Calculate();
        	excel.PrintCells();
        }
        catch (Exception er) {
        	System.out.println("#" + er.getMessage());
        } 
        	
	}
	
}
