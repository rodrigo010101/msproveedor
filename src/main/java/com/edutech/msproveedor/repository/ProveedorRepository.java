package com.edutech.msproveedor.repository;

import org.springframework.stereotype.Repository;

import com.edutech.msproveedor.model.Proveedor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.lang.NonNull;;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    @NonNull
    Optional<Proveedor> findById(Integer idproveedor);

    Proveedor getReferenceById(Integer idproveedor);

    Optional<Proveedor> findByNombreEmpresa(String nombreEmpresa);

}
