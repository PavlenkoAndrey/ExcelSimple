package my.homeproject;

import java.lang.String;
import java.io.*;
import java.util.*;

import my.homeproject.celldata.CellData;
import my.homeproject.celldata.CellExpression;
import my.homeproject.celldata.CellText;
import my.homeproject.celldata.CellValue;
import my.homeproject.exceptions.ExcelExceptions;
import my.homeproject.exceptions.ExcelReadInputException;

public class Excel {
	
	static int rows;
	static int columns;
	static CellData[][] cells;
	String outputFileName;
	
	public Excel(String inputFileName, String outputFileName)
	{
		this.outputFileName = outputFileName;
		try (InputStream inputStreamFile = (inputFileName != "cin") ? new FileInputStream(inputFileName) : System.in;
			 Scanner scanner = new Scanner(inputStreamFile);)
		{
			Reader(scanner);
		}
		catch (Exception exception) {
			if (exception.getMessage() != null) {
				System.out.println("#" + exception.getMessage());
				System.exit(1);
			} else {
				exception.printStackTrace();
			}
		}
	}
	
	private void Reader(Scanner scanner) throws ExcelReadInputException
	{
		String str = scanner.nextLine();
		StringTokenizer st = new StringTokenizer(str, "\t");
		try {
			rows = Integer.parseInt(st.nextToken());
			columns = Integer.parseInt(st.nextToken());
		}
		catch (Exception ex)
		{
			throw new ExcelReadInputException();
		}
		if (rows < 1 || rows > 9 || columns < 1 || columns > 26 ) {
			throw new ExcelReadInputException();
		}

		cells = new CellData[rows][columns];
		for (int i = 0; i < rows; ++i) {
			if (!scanner.hasNextLine()) {
				throw new ExcelReadInputException();
			}
			str = scanner.nextLine();
			// Parse the next line of the input data
			st = new StringTokenizer(str, "\t");
			for (int j = 0; j < columns; ++j) {
				if (!st.hasMoreTokens()) {
					throw new ExcelReadInputException();
				}
				String txtcell = st.nextToken();
				// Reference to cell like A2, B5
				String ref = "" + (char)(j + 'A') + (char)(i + 1 + '0');
				cells[i][j] = CreateCell(txtcell, ref);
			}
		}
	}
	
	private CellData CreateCell(String textOfCell, String referenceToCell) throws ExcelReadInputException
	{
		if (textOfCell.length() == 0)	{
			throw new ExcelReadInputException();
		}
		CellData cell;
        if (textOfCell.charAt(0) == '\'') {
            cell = new CellText(textOfCell, referenceToCell);
        } else if (textOfCell.charAt(0) == '=') {
            cell = new CellExpression(textOfCell, referenceToCell);
        } else {
            cell = new CellValue(textOfCell, referenceToCell);
        }
        return cell;
	}
	
	static public CellData getCellByReference(String referenceToCell) throws ExcelExceptions
	{
		int i = (int)(referenceToCell.charAt(1) - '0') - 1;
		int j = (int)(referenceToCell.charAt(0) - 'A');
		
		if ((i < 0) || (i >= rows) || (j < 0) || (j >= columns)) {
			// Incorrect data
			throw new ExcelExceptions(ExcelExceptions.INCORRECT_REFERENCE);
		}
		CellData cell = cells[i][j]; 
		if ((cell == null) || (cell != null && !cell.getReferenceToCell().equals(referenceToCell)) ) {
			// Something wrong with algorithm			
			throw new RuntimeException("Cell reference error");
		}
		return cells[i][j];
	}
	
	public void PrintResult()
	{
		try (OutputStream outStream = (outputFileName != "cout") ? new FileOutputStream(outputFileName) : System.out;
			 PrintWriter printWriter = new PrintWriter(outStream, true);) 
		{
			// Determine the column size for the columns alignment
			int cellsWidth[] = new int[columns];
			for(int i = 0; i < rows; ++i) {
				for(int j = 0; j < columns; ++j) {
					cellsWidth[j] = Math.max(cellsWidth[j], cells[i][j].GetResult().length());
				}
			}
			// Print data with columns alignment
			for(int i = 0; i < rows; ++i) {
				for(int j = 0; j < columns; ++j) {
					String s = cells[i][j].GetResult();
					printWriter.print(s + new String(new char[cellsWidth[j]-s.length()+2]));
				}
				printWriter.println();
			}
		}
		catch (Exception exception) {
			if (exception.getMessage() != null) {
				System.out.println("#" + exception.getMessage());
				System.exit(1);
			} else {
				exception.printStackTrace();
			}
		}
	}
	
	public void Calculate()
	{
		for(int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				cells[i][j].Calculate();
			}
		}
	}

}