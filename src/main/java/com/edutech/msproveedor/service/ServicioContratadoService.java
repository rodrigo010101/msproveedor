package com.edutech.msproveedor.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.edutech.msproveedor.model.ServicioContratado;
import com.edutech.msproveedor.repository.ServicioContratadoRepository;

@Service
public class ServicioContratadoService {

    @Autowired
    private ServicioContratadoRepository servicioContratadoRepository;

    // crear
    public ServicioContratado save(ServicioContratado servicioContratado) {
        if (servicioContratado.getIdservicio() == null) {
            throw new IllegalArgumentException("El servicio contratado no puede ser null");
        }
        if (servicioContratado.getDescripcionServicio() == null
                || servicioContratado.getDescripcionServicio().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La descripcion del servicio no puede ser NUll");
        }
        return servicioContratadoRepository.save(servicioContratado);

    }

    // read
    public List<ServicioContratado> findAll() {
        return servicioContratadoRepository.findAll();
    }

    // find by id
    public Optional<ServicioContratado> findById(Integer idservicio) {
        return servicioContratadoRepository.findById(idservicio);
    }

    // delete by id

    public void deleteById(Integer idservicio) {
        try {
            servicioContratadoRepository.deleteById(idservicio);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "idservicio no encontrado en la BD_ServicioContratado" + idservicio);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar uno o mas datos asociados");
        }
    }

    // actualiza
    public boolean update(Integer id, ServicioContratado servicioContratado) {

        Optional<ServicioContratado> upd = servicioContratadoRepository.findById(id);
        upd.get().setIdservicio(id);
        upd.get().setDescripcionServicio(servicioContratado.getDescripcionServicio());
        upd.get().setFechaInicioContrato(LocalDate.now());
        upd.get().setFechaFinContrato(LocalDate.now());

        servicioContratadoRepository.save(servicioContratado);
        return true;
    }
}
