package com.iudc.reportes;

import com.iudc.ticket.ticketService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

public class KPIExcelHelperExportador {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<TicketsPorArea> listUsers;
    private List<TicketsPorArea> listUsers2;
    private List<TicketsPorArea> listUsers4;
    private List<TicketsPorArea> listUsers5;
    private List<TicketsPorArea> listUsers6;
    int diasTrabajados;
    int acumuladoHoras;
    int ticketsTotales;

    @Autowired
    private ticketService repoService;

    public KPIExcelHelperExportador(List<TicketsPorArea> listUsers, List<TicketsPorArea> listUsers2, int ticketsTotales,
            List<TicketsPorArea> listUsers4, List<TicketsPorArea> listUsers5, List<TicketsPorArea> listUsers6, int diasTrabajados,
            int acumuladoHoras) {
        this.listUsers = listUsers;
        this.listUsers2 = listUsers2;
        this.ticketsTotales = ticketsTotales;
        this.listUsers4 = listUsers4;
        this.listUsers5 = listUsers5;
        this.listUsers6 = listUsers6;
        this.diasTrabajados = diasTrabajados;
        this.acumuladoHoras = acumuladoHoras;

        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("KPI Tickets");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Rcuento de tickets por area", style);
        createCell(row, 4, "% de tickets por estatus", style);
        createCell(row, 6, "Recuento de tickets por estatus", style);
        createCell(row, 9, "Tiempo por area (minutos)", style);
        createCell(row, 12, "Promedio de tiempo por area (minutos)", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
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

        for (TicketsPorArea ticketsporArea : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, ticketsporArea.getAnswer(), style);
            createCell(row, columnCount++, ticketsporArea.getCnt(), style);
        }

        for (TicketsPorArea ticketsporArea2 : listUsers2) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 4;
            createCell(row, columnCount++, ticketsporArea2.getEstatus(), style);
            createCell(row, columnCount++, ticketsporArea2.getCnt(), style);

        }

        for (TicketsPorArea ticketsporArea2 : listUsers4) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 6;
            createCell(row, columnCount++, ticketsporArea2.getEstatus(), style);
            createCell(row, columnCount++, ticketsporArea2.getCnt(), style);

        }

        for (TicketsPorArea ticketsporArea : listUsers5) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 9;
            createCell(row, columnCount++, ticketsporArea.getAnswer(), style);
            createCell(row, columnCount++, ticketsporArea.getCnt(), style);

        }

        for (TicketsPorArea ticketsporArea : listUsers6) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 12;
            createCell(row, columnCount++, ticketsporArea.getAnswer(), style);
            createCell(row, columnCount++, ticketsporArea.getCnt(), style);

        }

        Row rowTitulo = sheet.createRow(40);
        Cell cellTitulo = rowTitulo.createCell(0);
        cellTitulo.setCellValue("Información general");

        Row row2Titulo = sheet.createRow(41);
        Cell cell2Titulo = row2Titulo.createCell(0);
        cell2Titulo.setCellValue("Dias trabajados");

        Row rowValor = sheet.createRow(42);
        Cell cell2Valor = rowValor.createCell(0);
        cell2Valor.setCellValue(diasTrabajados);

        Row row3Titulo = sheet.createRow(43);
        Cell cell3Titulo = row3Titulo.createCell(0);
        cell3Titulo.setCellValue("Acumulado de horas");

        Row row3Valor = sheet.createRow(44);
        Cell cell3Valor = row3Valor.createCell(0);
        cell3Valor.setCellValue(acumuladoHoras);

        Row row4Titulo = sheet.createRow(45);
        Cell cell4Titulo = row4Titulo.createCell(0);
        cell4Titulo.setCellValue("Total de tickets");

        Row row5Titulo = sheet.createRow(46);
        Cell cell5Titulo = row5Titulo.createCell(0);
        cell5Titulo.setCellValue(ticketsTotales);

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
