package com.iudc.ticket;

import com.iudc.entidades.Ticket;
import com.iudc.reportes.TicketsPorArea;
import java.util.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ticketRepository extends JpaRepository<Ticket, Integer> {

    @Query("SELECT area FROM Cliente c WHERE c.id = ?1")
    public String findAreaById(Integer id);

    @Query("SELECT area FROM Cliente")
    public List<Ticket> listArea();

    //@Query("SELECT * FROM Ticket t WHERE t.usuarioTicket = ?1")
    @Query(value = "SELECT * FROM ticket WHERE usuario_ticket = ?1", nativeQuery = true)
    public List<Ticket> listTicketsPorUsuarioSistemas(String nombre);

    @Query(value = "SELECT * FROM tickets.ticket where fecha_solicitud between ?1 and ?2", nativeQuery = true)
    public List<Ticket> findTicketsTimeBetween(Date desde, Date hasta);

    @Query(value = "SELECT area_cliente as Answer, count(area_cliente) AS Cnt FROM tickets.ticket where fecha_solicitud between ?1 and ?2 GROUP BY area_cliente", nativeQuery = true)
    public List<TicketsPorArea> recuentoDeTicketsPorArea(Date desde, Date hasta);

    @Query(value = "SELECT count(*) AS CntTotal FROM tickets.ticket where fecha_solicitud between ?1 and ?2", nativeQuery = true)
    public List<TicketsPorArea> recuentoDeTicketsTotales(Date desde, Date hasta);

    @Query(value = "SELECT estatus as Estatus, count(estatus) AS Cnt FROM tickets.ticket where fecha_solicitud between ?1 and ?2 GROUP BY estatus", nativeQuery = true)
    public List<TicketsPorArea> recuentoDeTicketsPorEstatus(Date desde, Date hasta);
    
    @Query(value = "SELECT estatus, COUNT(*) / SUM(COUNT(*)) OVER () * 100 AS Cnt FROM tickets.ticket where fecha_solicitud between ?1 and ?2 GROUP BY estatus", nativeQuery = true)
    public List<TicketsPorArea> porcentajeRecuentoDeTicketsPorEstatus(Date desde, Date hasta);
    
    @Query(value = "SELECT area_cliente as Answer, SUM(tiempo_total) AS Cnt FROM tickets.ticket where fecha_solicitud between ?1 and ?2 GROUP BY area_cliente ", nativeQuery = true)
    public List<TicketsPorArea> sumaTiempoDeTicketsPorArea(Date desde, Date hasta);
    
    @Query(value = "SELECT area_cliente as Answer, AVG(tiempo_total) AS Cnt FROM tickets.ticket where fecha_solicitud between ?1 and ?2 GROUP BY area_cliente ", nativeQuery = true)
    public List<TicketsPorArea> promedioTiempoDeTicketsPorArea(Date desde, Date hasta);
    
    @Query(value = "SELECT COUNT(DISTINCT fecha_solicitud) FROM tickets.ticket where fecha_solicitud between ?1 and ?2", nativeQuery = true)
    public int diasTrabajados(Date desde, Date hasta);
    
    @Query(value = "SELECT SUM(tiempo_total/60) AS Cnt FROM tickets.ticket where fecha_solicitud between ?1 and ?2", nativeQuery = true)
    public int acumuladoHoras(Date desde, Date hasta);
    
    
    @Query(value = "SELECT count(*) AS CntTotal FROM tickets.ticket where fecha_solicitud between ?1 and ?2", nativeQuery = true)
    public int ticketsTotales(Date desde, Date hasta);
    

}

//@Query(value="SELECT area_cliente, count(*) FROM tickets.ticket where fecha_solicitud between ?1 and ?2 GROUP BY area_cliente", nativeQuery = true)
//public List<TicketsPorArea> recuentoDeTicketsPorArea(Date desde, Date hasta);
