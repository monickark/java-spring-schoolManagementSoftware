package com.jaw.common.exceptions.batch;

//User defined Exception class for Validating each cell in excel 
public class InValidCellValue extends Exception {
	String message;
	Integer row;
	Integer column;

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public InValidCellValue(String message) {
		this.message = message;
	}

}
