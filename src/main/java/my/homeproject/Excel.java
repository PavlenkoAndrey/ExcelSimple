package my.homeproject;

import java.lang.String;
import java.io.*;
import java.util.*;

public class Excel {
	
	static int rows;
	static int columns;
	static ArrayList<CellData> cells = new ArrayList<CellData>();
	PrintWriter outstream;
	
	public Excel(String from, String to)
	{
		try {
			OutputStream outstreamFile = (to != "cout") ? new FileOutputStream(to) : System.out;
			outstream = new PrintWriter(outstreamFile, true);
			
			InputStream inpstreamFile = (from != "cin") ? new FileInputStream(from) : System.in;
			Reader(inpstreamFile);
			inpstreamFile.close();
		}
    	catch (Exception er)
    	{
    		outstream.println("#" + er.getMessage());
    		System.exit(1);
    	}
	}
	
	private void Reader(InputStream ist) throws Exception, ExcelExceptions
	{
		Scanner scan = new Scanner(ist);
		
		String str = scan.nextLine();
		StringTokenizer st = new StringTokenizer(str, "\t");
		try {
			rows = Integer.parseInt(st.nextToken());
			columns = Integer.parseInt(st.nextToken());
		}
		catch (Exception ex)
		{
			throw new ExcelExceptions(ErrorTypes.INCORRECT_DATA_IN_INPUT);
		}
		if (rows < 1 || rows > 9 || columns < 1 || columns > 26 ) {
			throw new ExcelExceptions(ErrorTypes.INCORRECT_DATA_IN_INPUT);
		}

		cells.ensureCapacity(rows * columns);
		for (int i = 0; scan.hasNextLine() && i < rows; ++i) {
			str = scan.nextLine();
			// Parse the next line of the input data
			st = new StringTokenizer(str, "\t");
			int j = 0;
			while (st.hasMoreTokens()) {
				String txtcell = st.nextToken();
				// Reference to cell like A2, B5
				String ref = "" + (char)(j + 'A') + (char)(i + 1 + '0');
				AddCell(txtcell, ref);
				++j;
				if (j == columns) {
					// Skip the remaining data at the end of the line
					break;
				}
			}
			if (j < columns) {
				// The number of columns less than #columns
				throw new ExcelExceptions(ErrorTypes.INCORRECT_DATA_IN_INPUT);
			}
		}
			
		if (cells.size() != rows * columns) {
			throw new ExcelExceptions(ErrorTypes.INCORRECT_DATA_IN_INPUT);
		}
	}
	
	private void AddCell(String textOfCell, String referenceToCell) throws ExcelExceptions
	{
		if (textOfCell.length() == 0)	{
			throw new ExcelExceptions(ErrorTypes.INCORRECT_DATA_IN_INPUT);
		}
		CellData cell;
        if (textOfCell.charAt(0) == '\'') {
            cell = new CellText(textOfCell, referenceToCell);
        } else if (textOfCell.charAt(0) == '=') {
            cell = new CellExpression(textOfCell, referenceToCell);
        } else {
            cell = new CellValue(textOfCell, referenceToCell);
        }
		cells.add(cell);
	}
	
	static public CellData getCellByReference(String referenceToCell) throws IllegalArgumentException
	{
		int i = (int)(referenceToCell.charAt(1) - '0') - 1;
		int j = (int)(referenceToCell.charAt(0) - 'A');
		
		if ((i < 0) || (i >= rows) || (j < 0) || (j >= columns)) {
			throw new IllegalArgumentException();
		}
		return cells.get(i * columns + j) ;
	}
	
	public void PrintCells()
	{
		// Determine the column size for the columns alignment
		int cellsWidth[] = new int[columns];
		for(int i = 0; i < rows; ++i) {
			for(int j = 0; j < columns; ++j) {
				cellsWidth[j] = Math.max(cellsWidth[j], cells.get(i * columns + j).GetResult().length());
			}
		}
		// Print data with columns alignment
		for(int i = 0; i < rows; ++i) {
			for(int j = 0; j < columns; ++j) {
				String s = cells.get(i * columns + j).GetResult();
				StringBuffer sb = new StringBuffer(s.subSequence(0, s.length()));
				sb.setLength(cellsWidth[j] + 2);
				outstream.print(sb.toString());
			}
			outstream.println();
		}
	}
	
	// We will calculate expressions by iterations. At each iteration the new values will be found 
	// and located in the CellValue.Hashtable<cell_name, value>, where String cell_name is the cell refer like A3, B5,... 
	// and int value is the calculated value of the cell. If the new values will not be found, we will stop iterating.

	public void Calculate()
	{
		for(CellData cell : cells) {
			cell.Calculate();
		}
		/*
		int calculatedCellsCountOnPreviousStep = -1;
		int calculatedCellsCount = 0;
		while ((calculatedCellsCount = CalculationIteration()) != calculatedCellsCountOnPreviousStep) {
			calculatedCellsCountOnPreviousStep = calculatedCellsCount;
		}
		*/
	}

	/*
	private int CalculationIteration()
	{
		int calculatedCellsCount = 0;
		for(int i = 0; i < cells.size(); ++i) {
			calculatedCellsCount += cells.get(i).Calculate();
		}
		return calculatedCellsCount;
	}
	*/

}