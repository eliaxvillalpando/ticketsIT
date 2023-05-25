package com.iudc.reportes;

import com.iudc.ticket.ticketRepository;
import com.iudc.ticket.ticketService;

import java.text.*;
import java.util.List;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorReporte {

    @Autowired
    private ReportesService reporteServiceRepo;

    @Autowired
    private ticketRepository ticketRepo;

    @Autowired
    private ticketService ticketServiceRepo;

    @GetMapping("/reportes")
    private String viewReportes(Model model) throws ParseException {

        return "reportes/reportes";

    }

    @GetMapping("/reportes-tickets")
    private String viewReportesTickets() throws ParseException {

        return "reportes/reportes-tickets";

    }

    @RequestMapping("/tickets-por-fecha")
    public String MostrarReporteTicketsEntreFechas(Model model, @RequestParam(name = "desde") String desde, @RequestParam(name = "hasta") String hasta, HttpServletRequest request) throws ParseException {

        reporteServiceRepo.listarTicketsEntreFechasInput(model, desde, hasta);

        return "reportes/reportes-tickets";

    }

    @RequestMapping("/tickets-por-area")
    public String MostrarReporteTicketsEntreFechasGroupByArea(Model model, @RequestParam(name = "desde") String desde, @RequestParam(name = "hasta") String hasta, HttpServletRequest request) throws ParseException {

        reporteServiceRepo.recuentoDeTicketsPorAreaServicio(model, desde, hasta);

        return "reportes/reportes-resultado";

    }

    //COMO ENVIAR DESDE UN INPUT EN EL PREVIO DEL REPORTE DEL KPI???????
    @RequestMapping("/kpi/export/excel")
    public void exportToExcel(HttpServletResponse response, @RequestParam(name = "desde") String desde, @RequestParam(name = "hasta") String hasta) throws IOException, ParseException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFormatoDesde = sdformat.parse(desde);
        Date dateFormatoHasta = sdformat.parse(hasta);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<TicketsPorArea> tablaTickets = new ArrayList<TicketsPorArea>();
        List<TicketsPorArea> tablaTickets2 = new ArrayList<TicketsPorArea>();
        int ticketsTotales;
        List<TicketsPorArea> tablaTickets4 = new ArrayList<TicketsPorArea>();
        List<TicketsPorArea> tablaTickets5 = new ArrayList<TicketsPorArea>();
        List<TicketsPorArea> tablaTickets6 = new ArrayList<TicketsPorArea>();
        int diasTrabajados;
        int acumuladoHoras;

        tablaTickets = ticketRepo.recuentoDeTicketsPorArea(dateFormatoDesde, dateFormatoHasta);
        tablaTickets2 = ticketRepo.porcentajeRecuentoDeTicketsPorEstatus(dateFormatoDesde, dateFormatoHasta);
        tablaTickets4 = ticketRepo.recuentoDeTicketsPorEstatus(dateFormatoDesde, dateFormatoHasta);
        tablaTickets5 = ticketRepo.sumaTiempoDeTicketsPorArea(dateFormatoDesde, dateFormatoHasta);
        tablaTickets6 = ticketRepo.promedioTiempoDeTicketsPorArea(dateFormatoDesde, dateFormatoHasta);
        diasTrabajados = ticketRepo.diasTrabajados(dateFormatoDesde, dateFormatoHasta);
        acumuladoHoras = ticketRepo.acumuladoHoras(dateFormatoDesde, dateFormatoHasta);
        ticketsTotales = ticketRepo.ticketsTotales(dateFormatoDesde, dateFormatoHasta);

        KPIExcelHelperExportador excelExporter = new KPIExcelHelperExportador(tablaTickets, tablaTickets2,
                 ticketsTotales, tablaTickets4, tablaTickets5, tablaTickets6, diasTrabajados, acumuladoHoras);

        excelExporter.export(response);
    }

}
