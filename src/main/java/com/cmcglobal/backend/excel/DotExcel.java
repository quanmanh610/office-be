package com.cmcglobal.backend.excel;

import com.cmcglobal.backend.dto.response.dot.ReportDotResponse;
import com.cmcglobal.backend.entity.pojo.DotExcelEnum;
import com.cmcglobal.backend.utility.DateUtility;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.cmcglobal.backend.constant.Constant.YYYY_MM_DD;

public class DotExcel {
    private XSSFSheet sheet;
    private final XSSFWorkbook workbook;
    private final List<ReportDotResponse.DotDTO> dotDTOList;
    private final LocalDate startDateMonth;
    private final LocalDate endDateMonth;

    public DotExcel(List<ReportDotResponse.DotDTO> dotReportDTOList, String fromDate, String toDate) {
        this.dotDTOList = dotReportDTOList;
        this.startDateMonth = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern(YYYY_MM_DD));
        this.endDateMonth = LocalDate.parse(toDate, DateTimeFormatter.ofPattern(YYYY_MM_DD));
        this.workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Dot management");

        Row rowTitle = sheet.createRow(0);
        CellStyle styleTitle = workbook.createCellStyle();
//        styleTitle.setFo;
//        createCell(rowTitle,0, LocalDateTime.now().toString(),);
        Row row = sheet.createRow(1);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
        int columnCount = 0;
        for (DotExcelEnum header : DotExcelEnum.values()) {
            createCell(row, columnCount++, header.getType(), style);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);

        int numbering = 0;
        for (ReportDotResponse.DotDTO dotDTO : dotDTOList) {
            Row row = sheet.createRow(rowCount++);
            int allDaysInMonth = DateUtility.calculateDaysBetween(endDateMonth, startDateMonth);
            float pricePerDay = dotDTO.getPrice() != null ? (dotDTO.getPrice() / allDaysInMonth) : 0;
            int numberOfRentDay = this.getNumberOfRentDaysInMonth(dotDTO.getFromDate(), dotDTO.getToDate());
            int columnCount = 0;
            createCell(row, columnCount++, numbering++, style);
            createCell(row, columnCount++, dotDTO.getOwner(), style);
            createCell(row, columnCount++, dotDTO.getFullName(), style);
            createCell(row, columnCount++, dotDTO.getBuildingName(), style);
            createCell(row, columnCount++, dotDTO.getFloorName(), style);
            createCell(row, columnCount++, dotDTO.getGroup(), style);
            createCell(row, columnCount++, dotDTO.getDepartment(), style);
            createCell(row, columnCount++, dotDTO.getStatus(), style);
            createCell(row, columnCount++, dotDTO.getFromDate().toString(), style);
            createCell(row, columnCount++, dotDTO.getToDate().toString(), style);
            createCell(row, columnCount++, numberOfRentDay, style);
            createCell(row, columnCount++, (float) Math.floor((pricePerDay * numberOfRentDay) * 1000) / 1000, style);
        }
    }

    private int getNumberOfRentDaysInMonth(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.compareTo(this.startDateMonth) >= 0 && toDate.compareTo(this.endDateMonth) <= 0) {
            return DateUtility.calculateDaysBetween(fromDate, toDate);
        } else if (fromDate.compareTo(this.startDateMonth) <= 0 && toDate.compareTo(this.endDateMonth) <= 0) {
            return DateUtility.calculateDaysBetween(toDate, startDateMonth);
        } else if (fromDate.compareTo(this.startDateMonth) >= 0 && toDate.compareTo(this.endDateMonth) >= 0) {
            return DateUtility.calculateDaysBetween(fromDate, endDateMonth);
        } else if (fromDate.compareTo(this.startDateMonth) <= 0 && toDate.compareTo(this.endDateMonth) >= 0) {
            return DateUtility.calculateDaysBetween(startDateMonth, endDateMonth);
        }
        return 0;
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
