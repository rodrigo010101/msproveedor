package com.edutech.msproveedor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.msproveedor.model.ServicioContratado;

@Repository
public interface ServicioContratadoRepository extends JpaRepository<ServicioContratado, Integer> {
    // list,update,actualizar se crean automaticamente de jpa

    Optional<ServicioContratado> findByID(Integer idservicio);
    
}
