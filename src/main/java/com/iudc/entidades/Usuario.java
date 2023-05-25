package com.iudc.entidades;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
import org.springframework.stereotype.Component;


@Entity
@Getter
@Setter
@Component
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 65)
    private String email;

    @Column(nullable = false, length = 184)
    private String password;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 20)
    private String apellido;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_usuario",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
            )
    private Set<Roles> roles = new HashSet<>();
    
    

}
