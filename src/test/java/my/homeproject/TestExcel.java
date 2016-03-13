package my.homeproject;

import org.junit.Test;
import static org.junit.Assert.*;
import my.homeproject.celldata.CellExpression;
import my.homeproject.celldata.CellText;
import my.homeproject.celldata.CellValue;
import my.homeproject.exceptions.ExcelExceptions;

public class TestExcel {

	// Tests for calculation by recursion should be updated
	@Test
	public void TestCellExpression1() {
		CellExpression cell = new CellExpression("=10-5*3/4", "A1");
		cell.Calculate();
		assertEquals(cell.GetResult(), "3");
	}

	@Test
	public void TestCellExpression2() {
		CellExpression cell = new CellExpression("=+10", "A1");
		cell.Calculate();
		assertEquals(cell.GetResult(), "10");
	}

	@Test
	public void TestCellExpression3() {
		CellExpression cell = new CellExpression("=-12", "A1");
		cell.Calculate();
		assertEquals(cell.getErrorMessage(), ExcelExceptions.SYNTAX_ERROR);
	}

	@Test
	public void TestCellExpression4() {
		CellExpression cell = new CellExpression("=1/0", "A1");
		cell.Calculate();
		assertEquals(cell.getErrorMessage(), ExcelExceptions.DIVISION_BY_ZERO);
	}

	/*
	@Test
	public void TestCellExpression5() {
		CellExpression cell1 = new CellExpression("=5", "A1");
		cell1.Calculate();

		CellExpression cell2 = new CellExpression("=A1-2", "B1");
		cell2.Calculate();

		CellExpression cell3 = new CellExpression("=B1-A1", "C1");
		cell3.Calculate();
		
		CellExpression cell = new CellExpression("=A1+B1*C1/5+1", "A1");
		cell.Calculate();
		assertEquals(cell.GetResult(), "-2");
	}
	*/

	@Test
	public void TestCellExpression6() {
		CellValue cell = new CellValue("+5", "A1");
		cell.Calculate();
		assertEquals(cell.GetResult(), "5");
	}

	@Test
	public void TestCellExpression7() {
		CellValue cell = new CellValue("-5", "A1");
		cell.Calculate();
		assertEquals(cell.getErrorMessage(), ExcelExceptions.SYNTAX_ERROR);
	}
	
	@Test
	public void TestCellExpression8() {
		CellText cell = new CellText("'Sheet", "A1");
		cell.Calculate();
		assertEquals(cell.GetResult(), "Sheet");
	}
	
}
