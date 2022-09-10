package com.goKart.goKart.excel;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.goKart.goKart.model.Bateria;

public class BateriaExcel {
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private List<Bateria> listaBateria;
	
	public BateriaExcel(List<Bateria> listaBaterias) {
		this.listaBateria=listaBaterias;
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
		sheet=workbook.createSheet("Baterias");
		
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		
		row=sheet.createRow(0);
		font.setBold(false);
        font.setFontHeight(12);
        style.setFont(font);
		criaCelula(row, 0, "ID", style);
        criaCelula(row, 1, "Data", style);
        criaCelula(row, 2, "Horario", style);
        criaCelula(row, 3, "Valor UN", style);
        criaCelula(row, 4, "Vagas Confirmadas", style);
        criaCelula(row, 5, "Valor Total", style);
        
	}
	
	private void writeDataLines() {
		int rowCount=1;
		
		CellStyle style=workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		font.setFontHeight(12);
		style.setFont(font);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		for(Bateria bateria :listaBateria) {
			Row row=sheet.createRow(rowCount++);
			int columnCount=0;
	
			Integer newInteger = bateria.getVagasConfirmadas().intValue();
			BigDecimal vagaConfirmadaBig = new BigDecimal(newInteger);
			
			BigDecimal resultado = bateria.getValorBateria().multiply(vagaConfirmadaBig);

			criaCelula(row, columnCount++, bateria.getId(), style);
			criaCelula(row, columnCount++, bateria.getData().format(formatter), style);
			criaCelula(row, columnCount++, bateria.getHoraBateria().toString(), style);
			criaCelula(row, columnCount++, bateria.getValorBateria().toString(), style);
			criaCelula(row, columnCount++, bateria.getVagasConfirmadas(), style);
			criaCelula(row, columnCount++, resultado.toString(), style);
		}
	}
	
	public void export(HttpServletResponse response) throws IOException{
		writeHeaderLine();
		writeDataLines();
		
		String fileName = "Baterias.csv";
		
		String headerKey = "Content-Disposition";
		String headervalue = "attachment; filename=" + fileName;
		
		response.setHeader(headerKey, headervalue);
		
		ServletOutputStream outputStream=response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
		
	}
	
	

}
