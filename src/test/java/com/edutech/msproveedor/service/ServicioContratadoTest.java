package com.edutech.msproveedor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edutech.msproveedor.model.Proveedor;
import com.edutech.msproveedor.model.ServicioContratado;
import com.edutech.msproveedor.repository.ServicioContratadoRepository;

@ExtendWith(MockitoExtension.class)

public class ServicioContratadoTest {
    @Mock
    private ServicioContratadoRepository servRepository;

    @InjectMocks
    private ServicioContratadoService servService;

    @Captor
    private ArgumentCaptor<Integer> idCaptor;

    @Captor
    private ArgumentCaptor<ServicioContratado> servCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarServicioYdebeRetornarElGuardado() {
        ServicioContratado serv = new ServicioContratado();
        serv.setIdservicio(1);
        serv.setDescripcionServicio("Arriendo");

        when(servRepository.save(any(ServicioContratado.class))).thenReturn(serv);

        ServicioContratado resultado = servService.save(serv);

        assertEquals(1, resultado.getIdservicio());
        assertEquals("Arriendo", resultado.getDescripcionServicio());
        assertEquals(serv, resultado);

        verify(servRepository).save(resultado);
        verify(servRepository).save(servCaptor.capture());
    }

    @Test
    void buscarServicioPorIdDebeRetornarElBuscado() {
        ServicioContratado servicioId = new ServicioContratado();
        servicioId.setIdservicio(1);

        when(servRepository.findById(anyInt())).thenReturn(Optional.of(servicioId));

        Optional<ServicioContratado> resultado = servService.findById(1);
        assertEquals(1, resultado.get().getIdservicio());

        verify(servRepository).findById(idCaptor.capture());
        assertEquals(1, idCaptor.getValue());

    }

    @Test
    void listarLosServiciosYdebeRetornarLaLista() {
        LocalDate fechaInicioContrato = LocalDate.now();
        LocalDate fechaFinContrato = LocalDate.now();

        List<ServicioContratado> listServicio = Arrays.asList(
                new ServicioContratado(1, "Arriendo", fechaInicioContrato, fechaFinContrato, new Proveedor()));

        when(servRepository.findAll()).thenReturn(listServicio);
        // resultado de la lista
        List<ServicioContratado> resulList = servService.findAll();
        assertEquals(listServicio, resulList);
        assertEquals(1, resulList.get(0).getIdservicio());
        assertEquals(listServicio.size(), resulList.size());

        verify(servRepository).findAll();
    }

    @Test
    void eliminarProveedorYNoretornaNada() {
        doNothing().when(servRepository).deleteById(anyInt());
        servService.deleteById(1);

        verify(servRepository).deleteById(idCaptor.capture());
        assertEquals(1, idCaptor.getValue());

    }

    @Test
    void actualizarProveedorDebeRetornarElnuevoProveedor() {
        LocalDate fechaInicioContrato = LocalDate.now();
        LocalDate fechaFinContrato = LocalDate.now();

        ServicioContratado servicoExistente = new ServicioContratado(1, "Arriendo", fechaInicioContrato,
                fechaFinContrato, new Proveedor());

        ServicioContratado servicioActualizado = new ServicioContratado(1, "Comprado", fechaInicioContrato,
                fechaFinContrato, new Proveedor());

        when(servRepository.findById(1)).thenReturn(Optional.of(servicoExistente));
        when(servRepository.save(any(ServicioContratado.class))).thenReturn(servicoExistente);

        boolean actualizado = servService.update(1, servicioActualizado);
        verify(servRepository).findById(1);

        assertTrue(actualizado);
        assertEquals(1, servicioActualizado.getIdservicio());
        assertEquals(servicoExistente, servicioActualizado);

        verify(servRepository).save(servCaptor.capture());

        ServicioContratado capturado = servCaptor.getValue();
        assertEquals(capturado.getIdservicio(), servicioActualizado.getIdservicio());

    }

}
