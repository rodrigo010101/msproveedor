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
import com.edutech.msproveedor.model.ServicioContratado;
import com.edutech.msproveedor.repository.ProveedorRepository;
import com.edutech.msproveedor.service.ProveedorService;
import com.edutech.msproveedor.service.ServicioContratadoService;

@RestController
@RequestMapping("/api/v1/servicioContratado")
public class ServicioContratadoController {

    @Autowired
    private ServicioContratadoService servicioContratadoService;

    @Autowired
    private ProveedorService proveedorService;

    // crear
    @PostMapping
    public ResponseEntity<ServicioContratado> createServContr(@RequestBody ServicioContratado servicioContratado) {
        int idLink = servicioContratado.getProveedor().getIdproveedor();
        Proveedor proveedor = proveedorService.proveedorxId(idLink);

        if (proveedor != null) {
            servicioContratado.setProveedor(proveedor);
        }

        Optional<ServicioContratado> servExist = servicioContratadoService.findById(servicioContratado.getIdservicio());
        if (servExist.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            // obj new
            ServicioContratado newContratoservicio = servicioContratadoService.save(servicioContratado);
            return new ResponseEntity<>(newContratoservicio, HttpStatus.ACCEPTED);
        }
    }

    // listar findAll
    @GetMapping
    public ResponseEntity<List<ServicioContratado>> listarServico() {
        List<ServicioContratado> listServicio = servicioContratadoService.findAll();
        if (listServicio.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    // leer by id
    @GetMapping("/{idservicio}")
    public ResponseEntity<ServicioContratado> readServicio(@PathVariable Integer idservicio) {
        // obj
        Optional<ServicioContratado> readservicio = servicioContratadoService.findById(idservicio);
        if (!readservicio.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(readservicio.get(), HttpStatus.OK);
        }
    }

    // delete by id
    @DeleteMapping("/{idservicio}")
    public ResponseEntity<?> deleteServicio(@PathVariable Integer idservicio) {

        try {
            // obj para verificar si existe el contrato de servicio
            Optional<ServicioContratado> contratoExistente = servicioContratadoService.findById(idservicio);
            if (!contratoExistente.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                ServicioContratado servicioCont = contratoExistente.get();
                servicioContratadoService.deleteById(idservicio);
                return new ResponseEntity<>("Idservicio eliminado " + servicioCont, HttpStatus.ACCEPTED);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Error en la integridad de los datos.", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{idservicio}")
    public ResponseEntity<ServicioContratado> updateServicioContrato(@PathVariable Integer idservicio,
            @RequestBody ServicioContratado servicioContratado) {

        try {
            // obj 1
            Optional<ServicioContratado> updateServicio = servicioContratadoService.findById(idservicio);
            if (!updateServicio.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                // obj 2
                ServicioContratado newUpdate = updateServicio.get();
                newUpdate.setDescripcionServicio(servicioContratado.getDescripcionServicio());
                newUpdate.setFechaInicioContrato(servicioContratado.getFechaInicioContrato());
                newUpdate.setFechaFinContrato(servicioContratado.getFechaFinContrato());

                servicioContratadoService.save(newUpdate);
                return new ResponseEntity<>(newUpdate, HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
