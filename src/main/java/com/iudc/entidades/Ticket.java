package com.iudc.entidades;

import java.util.*;
import java.time.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ticket")
public class Ticket {

    @Id
    @Column(name = "id_ticket")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    @Column(name = "fecha_creacion")
    private Date fecha_creacion;
    //new Date(System.currentTimeMillis());

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    @Column(name = "fecha_modificacion")
    private Date fecha_modificacion;
    //new Date(System.currentTimeMillis());

    @Column(name = "tiempo_total")
    private Long tiempo_total;

    @Column(name = "usuarioTicket")
    private String usuarioTicket;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "areaCliente")
    private String areaCliente;

    @Column(name = "solicitud")
    private String solicitud;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "estatus")
    private String estatus;

    @DateTimeFormat(pattern = "HH:mm")//nueva linea
    @Column(name = "horaInicio")
    private LocalTime horaInicio;

    @DateTimeFormat(pattern = "HH:mm")//nueva linea
    @Column(name = "horaFinal")
    private LocalTime horaFinal;

    @Column(name = "medioComunicacion")
    private String medioComunicacion;
    
    @Column(name ="fecha_solicitud")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_solicitud;
    
    @Column(name ="fecha_cierre")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_cierre;
    
    
    

}
