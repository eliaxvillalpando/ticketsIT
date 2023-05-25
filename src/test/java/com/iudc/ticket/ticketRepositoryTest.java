package com.iudc.ticket;

import com.iudc.cliente.ClienteRepository;
import com.iudc.entidades.EstatusTicket;
import com.iudc.entidades.Ticket;
import com.iudc.reportes.TicketsPorArea;
import com.iudc.usuario.UserRepository;
import java.util.*;
import java.text.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ticketRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ticketRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    /*
      @Test
    public void testCreateTicket() {

        Ticket ticket = new Ticket();
        Usuario user = userRepo.findById(1).get();
        String usuarioSistemas = user.getNombre();
        
        Cliente cliente = clienteRepo.findById(1).get();
        String area = cliente.getArea();
        String clienteTicket = cliente.getNombre();
        
        ticket.setObservaciones("Dar de alta etiqueta 1501489");
        ticket.setSolicitud("Alta de etiquetas");
        ticket.setUsuarioTicket(usuarioSistemas);
        ticket.setFecha_creacion(new Date());
        ticket.setFecha_modificacion(new Date());
        ticket.setEstatus("Abierto");
        ticket.setHoraInicio(LocalTime.of(12, 54));
        ticket.setHoraFinal(LocalTime.of(13, 10));
        ticket.setFecha_cierre(LocalDate.now());
        ticket.setFecha_solicitud(LocalDate.of(2022, 12, 6));
        ticket.setMedioComunicacion("Whatsapp");
        //ticket.setCliente(cliente);
        ticket.setCliente(clienteTicket);
        ticket.setAreaCliente(area);
        Ticket savedTicket = repo.save(ticket);
        Ticket existTicket = entityManager.find(Ticket.class, savedTicket.getId());
        assertThat(ticket.getId()).isEqualTo(existTicket.getId());

    }

    @Test
    public void testEncontrarTicketPorId() {

        Usuario user = userRepo.findById(1).get();
        Ticket ticket = repo.findById(1).get();
        assertThat(ticket.getUsuarioTicket()).isEqualTo(user);

    }

     */
    @Test
    public void actualizaEstatusTicket() {

        Ticket ticket = repo.findById(1).get();
        ticket.setEstatus("Cerrado");
        long tiempo1 = ticket.getFecha_modificacion().getTime() - ticket.getFecha_creacion().getTime();
        //long minutos =  (tiempo1 / (1000*60)) % 60;
        long minutos = TimeUnit.MILLISECONDS.toMinutes(tiempo1) % 60;
        ticket.setTiempo_total(minutos);
        Ticket updatedTicket = repo.save(ticket);
        assertThat(updatedTicket.getEstatus()).isEqualTo(EstatusTicket.CERRADO);

    }

    @Test
    public void listarTickets() {

        Iterable<Ticket> tickets = repo.findAll();
        tickets.forEach(System.out::println);

        assertThat(tickets).isNotEmpty();

    }

    @Test
    public void listarTicketDeUsuarioSistemas() {

        String usuario = "Elias";
        Iterable<Ticket> tickets = repo.listTicketsPorUsuarioSistemas(usuario);
        tickets.forEach(System.out::println);
        assertThat(tickets).isNotEmpty();

    }

    @Test
    public void eliminarTicket() {

        repo.deleteById(1);
        Optional<Ticket> result = repo.findById(1);

        assertThat(result.isEmpty());

    }

    @Test
    public void areaPorIdCliente() {

        String str = repo.findAreaById(1);
        System.out.println(str);

    }

    @Test
    public void ticketsEntreFechas() throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdformat.parse("2022-12-1");
        Date date2 = sdformat.parse("2022-12-31");

        List<Ticket> a = new ArrayList<Ticket>();

        a = repo.findTicketsTimeBetween(date1, date2);

        System.out.println("test");

        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i).toString());
            //System.out.println(a.toString());
        }

    }

    @Test
    public void ticketsPorArea() throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdformat.parse("2022-12-1");
        Date date2 = sdformat.parse("2022-12-31");

        List<TicketsPorArea> tablaTickets = new ArrayList<>();

        tablaTickets = repo.recuentoDeTicketsPorArea(date1, date2);

        System.out.println(tablaTickets.toString());

    }

    @Test
    public void recuentoTotalTicketsEntreFechas() throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdformat.parse("2022-12-1");
        Date date2 = sdformat.parse("2022-12-31");

        List<TicketsPorArea> tablaTickets = new ArrayList<>();
        tablaTickets = repo.recuentoDeTicketsTotales(date1, date2);

        System.out.println(tablaTickets.toString());

    }

    @Test
    public void recuentoDeTicketsPorEstatusEntreFechas() throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdformat.parse("2022-12-1");
        Date date2 = sdformat.parse("2022-12-31");

        List<TicketsPorArea> tablaTickets = new ArrayList<>();
        tablaTickets = repo.recuentoDeTicketsPorEstatus(date1, date2);

        System.out.println(tablaTickets.toString());

    }

    @Test
    public void porcentajeRecuentoTicketsEstatusEntreFechas() throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdformat.parse("2022-08-1");
        Date date2 = sdformat.parse("2023-12-31");

        List<TicketsPorArea> tablaTickets = new ArrayList<>();
        tablaTickets = repo.porcentajeRecuentoDeTicketsPorEstatus(date1, date2);

        System.out.println(tablaTickets.toString());

    }

    @Test
    public void sumaTiempoAreaEntreFechas() throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdformat.parse("2022-08-1");
        Date date2 = sdformat.parse("2023-12-31");

        List<TicketsPorArea> sumaTicketsArea = new ArrayList<>();
        sumaTicketsArea = repo.sumaTiempoDeTicketsPorArea(date1, date2);

        System.out.println(sumaTicketsArea.toString());

    }

    @Test
    public void promedioTiempoAreaEntreFechas() throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdformat.parse("2022-08-1");
        Date date2 = sdformat.parse("2023-12-31");

        List<TicketsPorArea> promedioTiempoTicketsArea = new ArrayList<>();
        promedioTiempoTicketsArea = repo.sumaTiempoDeTicketsPorArea(date1, date2);

        System.out.println(promedioTiempoTicketsArea.toString());

    }

    @Test
    public void diasTrabajados() throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdformat.parse("2022-08-1");
        Date date2 = sdformat.parse("2023-12-31");

        int dias = repo.diasTrabajados(date1, date2);

        System.out.println(dias);

    }
    
    @Test
    public void sumaHorasTrabajadas() throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdformat.parse("2022-08-1");
        Date date2 = sdformat.parse("2023-12-31");

        int horas = repo.acumuladoHoras(date1, date2);

        System.out.println(horas);

    }
    

    @Test
    public void calcularTiempoTotalTicket() throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdformat.parse("2022-12-01");
        Date date2 = sdformat.parse("2022-12-05");
        LocalTime inicioTicket = LocalTime.parse("13:00");
        LocalTime cierreTicket = LocalTime.parse("10:00");
        LocalTime finDiaTicket = LocalTime.parse("20:00");
        LocalTime inicioDiaTiket = LocalTime.parse("08:00");

        long minutosPorDiaEnteroTrabajado = 720L;
        long timeDiff = Math.abs(date2.getTime() - date1.getTime());
        long daysDiff = (timeDiff / (1000 * 60 * 60 * 24)) % 365;
        long tiempoTotal;
        long tiempoDiaAbierto;
        long tiempoDiaCierre;
        long contadorDias = 0;

        System.out.println(daysDiff);
        if (daysDiff == 0) {

            tiempoTotal = ChronoUnit.MINUTES.between(cierreTicket, inicioTicket);
            System.out.println("Los minutos del ticket fueron: " + tiempoTotal);

        } else if (daysDiff >= 2) {

            tiempoDiaAbierto = inicioTicket.until(finDiaTicket, ChronoUnit.MINUTES);
            tiempoDiaCierre = inicioDiaTiket.until(cierreTicket, ChronoUnit.MINUTES);
            contadorDias = daysDiff - 1L;
            tiempoTotal = (minutosPorDiaEnteroTrabajado * contadorDias) + tiempoDiaAbierto + tiempoDiaCierre;

            System.out.println("Los minutos del ticket fueron: " + tiempoTotal);

        }

    }

    @Test
    public void finesDeSemana() throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdformat.parse("2022-12-01");
        Date end = sdformat.parse("2022-12-23");

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
        System.out.println("Dias de fines de semana" + weekends);
    }

}
