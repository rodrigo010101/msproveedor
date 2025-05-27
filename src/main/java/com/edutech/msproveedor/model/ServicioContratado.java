package com.edutech.msproveedor.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "servicio_contratado")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class ServicioContratado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idservicio;

    @Column(name = "descripcion_servicio", nullable = false, length = 150)
    private String descripcionServicio;

    @Column(nullable = false)
    LocalDate fechaInicioContrato;

    @Column(nullable = false)
    LocalDate fechaFinContrato;

    @ManyToOne
    @JoinColumn(name = "idproveedor")
    @JsonBackReference
    private Proveedor proveedor;

}
