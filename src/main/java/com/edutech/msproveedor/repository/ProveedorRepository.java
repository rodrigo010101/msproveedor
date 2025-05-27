package com.edutech.msproveedor.repository;

import org.springframework.stereotype.Repository;

import com.edutech.msproveedor.model.Proveedor;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.lang.NonNull;;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    // create
    // Proveedor save(Proveedor proveedor);

    // List all
    // List<Proveedor> findByAll();

    // find By id
    @NonNull
    Proveedor findById(int idproveedor);

    // delet for id
    // Proveedor deleteBy(Integer idproveedor);
    Proveedor getReferenceById(int idproveedor);

}
