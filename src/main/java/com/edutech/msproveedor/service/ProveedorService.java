package com.edutech.msproveedor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.edutech.msproveedor.model.Proveedor;
import com.edutech.msproveedor.repository.ProveedorRepository;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    // create
    public Proveedor save(Proveedor proveedor) {
        if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor no puede ser NULL");
        }
        if (proveedor.getNombreEmpresa() == null || proveedor.getNombreEmpresa().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio.");
        }
        try {
            return proveedorRepository.save(proveedor);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    // delete
    public void deleteById(Integer idproveedor) {
        try {
            proveedorRepository.deleteById(idproveedor);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Proveedor no encontrado en la BD_proveedor " + idproveedor, e);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar,tiene datos asociados con el id( " + idproveedor + " )");
        }
    }

    // list findAll
    public List<Proveedor> findAll() {
        try {
            return proveedorRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al recuperar los datos", e);
        }
    }

    // findById
    public Optional<Proveedor> findById(Integer idproveedor) {
        return proveedorRepository.findById(idproveedor);
    }

    public Proveedor proveedorxId(int idproveedor) {
        return proveedorRepository.getReferenceById(idproveedor);
    }
}
