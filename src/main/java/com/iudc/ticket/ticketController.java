package com.iudc.ticket;

import com.iudc.cliente.ClienteRepository;

import com.iudc.cliente.ClienteService;
import com.iudc.entidades.Cliente;
import java.time.temporal.ChronoUnit;
import com.iudc.entidades.Ticket;
import com.iudc.usuario.MyUserDetails;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ticketController {

    @Autowired
    private ClienteRepository repoCliente;

    @Autowired
    private ticketService serviceTicket;

    @Autowired
    private ClienteService serviceCliente;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private MyUserDetails usuarioRepo;

    @GetMapping("/tickets")
    public String verTickets(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {

        listarTickets(userDetails, model);
        return "tickets/tickets";

    }

    @GetMapping("/tickets/nuevo")
    public String nuevoTicket(Model model) {

        listarClientes(model);
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("tituloPagina", "Crear nuevo ticket");
        return "tickets/ticketForma";

    }

    @GetMapping("/tickets/editar/{id}")
    public String editarTicket(@PathVariable(name = "id") Integer id, Model model,
            RedirectAttributes ra) {
        listarClientes(model);

        try {
            Ticket ticket = serviceTicket.getTicket(id);

            model.addAttribute("ticket", ticket);
            model.addAttribute("tituloPagina", "Edtiar ticket (ID: " + id + ")");

            return "tickets/ticketForma";
        } catch (Exception ex) {
            ra.addFlashAttribute("mensaje", "No fue posible encontrar un ticket con ese ID");
            return "tickets/tickets";
        }

    }

    @GetMapping("/tickets/cerrar-ticket/{id}")
    public String cerrarTicket(@PathVariable(name = "id") Integer id, Model model,
            RedirectAttributes ra) {

        try {
            Ticket ticket = serviceTicket.getTicket(id);
            ticket.setEstatus("Cerrado");
            serviceTicket.guardar(ticket);
            //model.addAttribute("ticket", ticket);
            ra.addFlashAttribute("mensaje", "Ticket cerrado");
            return "redirect:/tickets";

        } catch (Exception ex) {
            ra.addFlashAttribute("mensaje", "No fue posible encontrar un ticket con ese ID");
            return "redirect:/tickets";
        }

    }

    public void listarClientes(Model model) {

        //Iterable<Cliente> iterableCliente = serviceCliente.listarClientes();
        Iterable<Cliente> iterableCliente = repoCliente.findAll();
        ArrayList<Cliente> iterableClienteActivo = new ArrayList<Cliente>();;

        for (Cliente cliente : iterableCliente) {

            if (cliente.isActivo() == true) {

                iterableClienteActivo.add(cliente);

            }

        }

        model.addAttribute("clientes", iterableClienteActivo);

    }

    public void listarTickets(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {

        String usuarioEnSesion = userDetails.getUsername();

        Iterable<Ticket> iterableTicket = serviceTicket.listarTicketsPorUsuarioSistemas(usuarioEnSesion);

        model.addAttribute("tickets", iterableTicket);

    }

    @PostMapping("/tickets/guardar")
    public String saveTicket(Ticket ticket, RedirectAttributes ra, Model model,
            @RequestParam(name = "fecha_solicitud") String desde,
            @RequestParam(name = "fecha_cierre") String hasta) throws ParseException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuarioAlta = auth.getName();
        ticket.setUsuarioTicket(usuarioAlta);
        Cliente cliente = clienteRepo.findAreaByName(ticket.getCliente());
        String area = cliente.getArea();
        ticket.setAreaCliente(area);
        
        

        LocalTime horaInicio = ticket.getHoraInicio();
        LocalTime horaFinal = ticket.getHoraFinal();

        // Check if horaInicio has seconds
        if (horaInicio.getSecond() == 0) {
            horaInicio = horaInicio.withSecond(0);
            ticket.setHoraInicio(horaInicio);
        }

        // Check if horaFinal has seconds
        if (horaFinal.getSecond() == 0) {
            horaFinal = horaFinal.withSecond(0);
            ticket.setHoraFinal(horaFinal);
        }

        long minutes = calcularTiempoTotalTicket(desde, hasta, horaInicio, horaFinal);
        ticket.setTiempo_total(minutes);

        serviceTicket.guardar(ticket);
        ra.addFlashAttribute("mensaje", "El ticket ha sido guardado con exito.");
        listarClientes(model);

        return "redirect:/tickets";
    }

    public long calcularTiempoTotalTicket(String desde, String hasta, LocalTime inicioTicket, LocalTime cierreTicket) throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdformat.parse(desde);
        Date date2 = sdformat.parse(hasta);
        LocalTime finDiaTicket = LocalTime.parse("20:00");
        LocalTime inicioDiaTiket = LocalTime.parse("08:00");

        long minutosPorDiaEnteroTrabajado = 720L;
        long timeDiff = Math.abs(date2.getTime() - date1.getTime());
        long daysDiff = (timeDiff / (1000 * 60 * 60 * 24)) % 365;
        long tiempoTotal;
        long tiempoDiaAbierto;
        long tiempoDiaCierre;
        long contadorDias = 0;
        long weekends = finesDeSemana(desde, hasta);

        if (daysDiff == 0) {

            tiempoTotal = ChronoUnit.MINUTES.between(inicioTicket, cierreTicket);
            return tiempoTotal;

        } else {

            tiempoDiaAbierto = inicioTicket.until(finDiaTicket, ChronoUnit.MINUTES);
            tiempoDiaCierre = inicioDiaTiket.until(cierreTicket, ChronoUnit.MINUTES);
            contadorDias = (daysDiff - 1L) - weekends;
            tiempoTotal = (minutosPorDiaEnteroTrabajado * contadorDias) + tiempoDiaAbierto + tiempoDiaCierre;
            return tiempoTotal;

        }

    }

    public long finesDeSemana(String desde, String hasta) throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdformat.parse(desde);
        Date end = sdformat.parse(hasta);

        int weekends = 0;
        final Calendar startCal = Calendar.getInstance();
        startCal.setTime(start);
        final Calendar endCal = Calendar.getInstance();
        endCal.setTime(end);
        while (startCal.before(endCal)) {
            if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                weekends++;
            }
            startCal.add(Calendar.DATE, 1);
        }
        return weekends;
    }

}
