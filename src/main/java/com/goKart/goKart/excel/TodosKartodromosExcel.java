package com.goKart.goKart.excel;

import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.model.Piloto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodosKartodromosExcel {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private List<Kartodromo> listaKartodromos;

	public TodosKartodromosExcel(List<Kartodromo> listaKartodromo) {
		this.listaKartodromos=listaKartodromo;
		workbook = new XSSFWorkbook();
	}
	
	private void criaCelula(Row linha,int contadorColuna, Object value,CellStyle style) {
		sheet.autoSizeColumn(contadorColuna);
		Cell cell=linha.createCell(contadorColuna);
		if(value instanceof Long) {
			cell.setCellValue((Long) value);
		}else if(value instanceof Integer) {
			cell.setCellValue((Integer) value);
		}else if(value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}
	private void writeHeaderLine() {
		sheet=workbook.createSheet("todosKartodromos");
		
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		
		row=sheet.createRow(0);
		font.setBold(false);
        font.setFontHeight(12);
        style.setFont(font);
        criaCelula(row, 0, "Nome", style);
		criaCelula(row, 1, "E-mail", style);
        criaCelula(row, 2, "Cidade", style);
	}
	
	private void writeDataLines() {
		int rowCount=1;
		
		CellStyle style=workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		font.setFontHeight(12);
		style.setFont(font);

		for(Kartodromo kartodromo :listaKartodromos) {
			Row row=sheet.createRow(rowCount++);
			int columnCount=0;

			criaCelula(row, columnCount++, kartodromo.getNome(), style);
			criaCelula(row, columnCount++, kartodromo.getEmail(), style);
			criaCelula(row, columnCount++, kartodromo.getCidade(), style);
		}
	}
	
	public void export(HttpServletResponse response) throws IOException{
		writeHeaderLine();
		writeDataLines();
		
		String fileName = "todosKartodromos.csv";
		
		String headerKey = "Content-Disposition";
		String headervalue = "attachment; filename=" + fileName;
		
		response.setHeader(headerKey, headervalue);
		
		ServletOutputStream outputStream=response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
		
	}
	
	

}
