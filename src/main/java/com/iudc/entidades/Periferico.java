package com.iudc.entidades;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "periferico")
public class Periferico {

    @Id
    @Column(name = "id_periferico")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "modelo", nullable = false)
    private String modelo;
    
    @Column(name = "tipoPeriferico", nullable = false)
    private String tipoPeriferico;
    
    @Column(name = "marca", nullable = false)
    private String marca;
    
    @Column(name = "areaPeriferico", nullable = false)
    private String areaPeriferico;
    
    @Column(name = "usuario", nullable = false)
    ArrayList<String> usuario = new ArrayList<String>();
    
    @Column(name = "foto1")
    private String foto1;
    
    @Column(name = "foto2")
    private String foto2;
    
    @Column(name = "foto3")
    private String foto3;
    
    @Column(name = "foto4")
    private String foto4;
    
    
    
    @Transient
	public String getPhotosImagePathFoto1() {
		//if (id == null || foto1 == null) return "/images/default-user.png";
		
		return "Perifericos/" + this.id + "/1/" + this.foto1;
                
	}
    
    
    
    
    
    
}
