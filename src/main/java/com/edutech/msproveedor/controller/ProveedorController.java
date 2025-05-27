package com.edutech.msproveedor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.msproveedor.model.Proveedor;
import com.edutech.msproveedor.service.ProveedorService;

@RestController
@RequestMapping("/api/v1/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping // List
    public ResponseEntity<List<Proveedor>> listaProveedor() {
        // obj
        List<Proveedor> listProve = proveedorService.findAll();
        if (listProve.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listProve, HttpStatus.OK);
    }

    @GetMapping("/{idproveedor}") // read
    public ResponseEntity<Proveedor> readProveedor(@PathVariable Integer idproveedor) {
        // obj
        Optional<Proveedor> proveedor = proveedorService.findById(idproveedor);
        if (proveedor.isPresent()) {
            return new ResponseEntity<>(proveedor.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping // create
    public ResponseEntity<Proveedor> createProveedor(@RequestBody Proveedor proveedor) {
        // obj para validar si esta creado el objeto
        if (proveedorService.exitsEmpresa(proveedor.getNombreEmpresa())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Proveedor newProveedor = proveedorService.save(proveedor);
        return new ResponseEntity<>(newProveedor, HttpStatus.CREATED);

    }

    @DeleteMapping("/{idproveedor}") // delete
    public ResponseEntity<Proveedor> deleteProveedor(@PathVariable Integer idproveedor) {

        try {
            Optional<Proveedor> proveedorExist = proveedorService.findById(idproveedor);
            if (!proveedorExist.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // Proveedor prove = proveedorExist.get();
            proveedorService.deleteById(idproveedor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{idproveedor}") // actualizar por id HTTP put
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable Integer idproveedor,
            @RequestBody Proveedor proveedor) {
        if (proveedorService.update(idproveedor, proveedor)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
