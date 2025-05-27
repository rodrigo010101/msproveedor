package com.edutech.msproveedor.model;

import java.time.LocalDate;

// import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
// import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Anotaciones 
@Entity
@Table(name = "proveedor")
@Data
@NoArgsConstructor
@AllArgsConstructor

// FinAnotaciones

public class Proveedor {
    // @OneToMany(mappedBy = "idproveedor", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproveedor;

    @Column(length = 100, nullable = false)
    private String nombreEmpresa;

    @Column(length = 100, nullable = false)
    private String descrProveedor;

    @Column(length = 20, nullable = false)
    private String contactoProveedor;

    @Column(length = 100, nullable = false)
    private String direccionProveedor;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoRecurso tipoRecurso;

    public enum TipoRecurso {
        MATERIAL,
        HUMANO,
        TECNOLOGICO
    }
}