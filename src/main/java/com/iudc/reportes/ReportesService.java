package com.iudc.reportes;

import com.iudc.entidades.Ticket;
import com.iudc.ticket.ticketRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
public class ReportesService {

    @Autowired
    private ticketRepository repo;

    public void listarTicketsEntreFechas(Model model) throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdformat.parse("2022-12-1");
        Date date2 = sdformat.parse("2022-12-31");

        Iterable<Ticket> iterableTicket = repo.findTicketsTimeBetween(date1, date2);

        model.addAttribute("ticketsReporte", iterableTicket);

    }

    public void listarTicketsEntreFechasInput(Model model, String date1, String date2) throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFormatoDesde = sdformat.parse(date1);
        Date dateFormatoHasta = sdformat.parse(date2);

        Iterable<Ticket> iterableTicket = repo.findTicketsTimeBetween(dateFormatoDesde, dateFormatoHasta);

        System.out.println(iterableTicket.toString());

        if (iterableTicket.spliterator().getExactSizeIfKnown() == 0) {

            model.addAttribute("mensaje", "No hay tickets entre esas fechas");
        }

        model.addAttribute("date1", dateFormatoDesde);
        model.addAttribute("date2", dateFormatoHasta);
        model.addAttribute("ticketsReporte", iterableTicket);

    }

    public void recuentoDeTicketsPorAreaServicio(Model model, String desde, String hasta) throws ParseException {

        //Este es el metodo para procesar lo que se va entregar en el controller
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFormatoDesde = sdformat.parse(desde);
        Date dateFormatoHasta = sdformat.parse(hasta);

        List<TicketsPorArea> tablaTickets = new ArrayList<TicketsPorArea>();
        List<TicketsPorArea> recuentoTickets = new ArrayList<TicketsPorArea>();
        List<TicketsPorArea> recuentoTicketsPorEstatus = new ArrayList<TicketsPorArea>();
        List<TicketsPorArea> porcentajeTicketsEstatus = new ArrayList<>();
        List<TicketsPorArea> sumaTicketsArea = new ArrayList<>();
        List<TicketsPorArea> promedioTiempoTicketsArea = new ArrayList<>();

        tablaTickets = repo.recuentoDeTicketsPorArea(dateFormatoDesde, dateFormatoHasta);
        recuentoTickets = repo.recuentoDeTicketsTotales(dateFormatoDesde, dateFormatoHasta);
        recuentoTicketsPorEstatus = repo.recuentoDeTicketsPorEstatus(dateFormatoDesde, dateFormatoHasta);
        porcentajeTicketsEstatus = repo.porcentajeRecuentoDeTicketsPorEstatus(dateFormatoDesde, dateFormatoHasta);
        sumaTicketsArea = repo.sumaTiempoDeTicketsPorArea(dateFormatoDesde, dateFormatoHasta);
        promedioTiempoTicketsArea = repo.promedioTiempoDeTicketsPorArea(dateFormatoDesde, dateFormatoHasta);
        int diasTrabajados = repo.diasTrabajados(dateFormatoDesde, dateFormatoHasta);
        int horasTotales = repo.acumuladoHoras(dateFormatoDesde, dateFormatoHasta);

        model.addAttribute("desde", dateFormatoDesde);
        model.addAttribute("hasta", dateFormatoHasta);
        model.addAttribute("ticketsReporte", tablaTickets);
        model.addAttribute("ticketsTotales", recuentoTickets);
        model.addAttribute("ticketsPorEstatus", recuentoTicketsPorEstatus);
        model.addAttribute("porcentajeTicketsPorEstatus", porcentajeTicketsEstatus);
        model.addAttribute("sumaTicketsArea", sumaTicketsArea);
        model.addAttribute("promedioTiempoTicketsArea", promedioTiempoTicketsArea);
        model.addAttribute("diasTrabajados", diasTrabajados);
        model.addAttribute("horasTotales", horasTotales);

    }

    public void exportarExcelKPIs(String desde, String hasta) throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFormatoDesde = sdformat.parse(desde);
        Date dateFormatoHasta = sdformat.parse(hasta);

        List<TicketsPorArea> tablaTickets = new ArrayList<TicketsPorArea>();
        List<TicketsPorArea> recuentoTickets = new ArrayList<TicketsPorArea>();
        List<TicketsPorArea> recuentoTicketsPorEstatus = new ArrayList<TicketsPorArea>();
        List<TicketsPorArea> porcentajeTicketsEstatus = new ArrayList<>();
        List<TicketsPorArea> sumaTicketsArea = new ArrayList<>();
        List<TicketsPorArea> promedioTiempoTicketsArea = new ArrayList<>();

        tablaTickets = repo.recuentoDeTicketsPorArea(dateFormatoDesde, dateFormatoHasta);
        recuentoTickets = repo.recuentoDeTicketsTotales(dateFormatoDesde, dateFormatoHasta);
        recuentoTicketsPorEstatus = repo.recuentoDeTicketsPorEstatus(dateFormatoDesde, dateFormatoHasta);
        porcentajeTicketsEstatus = repo.porcentajeRecuentoDeTicketsPorEstatus(dateFormatoDesde, dateFormatoHasta);
        sumaTicketsArea = repo.sumaTiempoDeTicketsPorArea(dateFormatoDesde, dateFormatoHasta);
        promedioTiempoTicketsArea = repo.promedioTiempoDeTicketsPorArea(dateFormatoDesde, dateFormatoHasta);
        int diasTrabajados = repo.diasTrabajados(dateFormatoDesde, dateFormatoHasta);
        int horasTotales = repo.acumuladoHoras(dateFormatoDesde, dateFormatoHasta);

    }

}
