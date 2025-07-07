package com.edutech.msproveedor.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edutech.msproveedor.model.Proveedor;
import com.edutech.msproveedor.model.Proveedor.TipoRecurso;
import com.edutech.msproveedor.repository.ProveedorRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)

public class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarProveedorYLuegoRetornarElProveedor() {

        LocalDate fechaRegistro = LocalDate.now();

        Proveedor proveedor = new Proveedor(null, "Empresa lucida", "embotelladora", "123456789", "paicavi #234",
                fechaRegistro, TipoRecurso.HUMANO, new ArrayList<>());

        Proveedor guardarProveedor = new Proveedor(1, "Empresa lucida", "embotelladora", "123456789", "paicavi #234",
                fechaRegistro, TipoRecurso.HUMANO, new ArrayList<>());

        when(proveedorRepository.save(proveedor)).thenReturn(guardarProveedor);

        Proveedor resultado = proveedorService.save(proveedor);

        assertThat(resultado.getIdproveedor()).isEqualTo(1);
        verify(proveedorRepository).save(proveedor);
    }

    @Test
    void listarProveedor_debeDevolverLaListaProveedor() {

        LocalDate fechaRegistro = LocalDate.now();

        Proveedor proveedor = new Proveedor(null, "Empresa lucida", "embotelladora", "123456789", "paicavi #234",
                fechaRegistro, TipoRecurso.HUMANO, new ArrayList<>());

        List<Proveedor> listProveedor = Arrays.asList(proveedor);
        when(proveedorRepository.findAll()).thenReturn(listProveedor);

        // resultados de la lista
        List<Proveedor> resultado = proveedorService.findAll();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Empresa lucida", resultado.get(0).getNombreEmpresa());

        verify(proveedorRepository).findAll();

    }

    @Test
    void eliminarProveedorYdebeLlamarAlRepository() {

        doNothing().when(proveedorRepository).deleteById(1);
        proveedorService.deleteById(1);
        verify(proveedorRepository).deleteById(1);
    }

    @Test
    void actualizarProveedorDebeRetornarElnuevoProveedor() {

        LocalDate fechaRegistro = LocalDate.now();

        Proveedor proveedorExistente = new Proveedor(1, "Empresa lucida", "embotelladora", "123456789",
                "paicavi #234",
                fechaRegistro, TipoRecurso.HUMANO, new ArrayList<>());

        Proveedor proveedorNuevo = new Proveedor(1, "Enviroment Technology", "Enginner Software", "123456789",
                "Concepcion",
                fechaRegistro, TipoRecurso.TECNOLOGICO, new ArrayList<>());

        when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedorExistente));
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedorExistente);

        boolean resultado = proveedorService.update(1, proveedorNuevo);
        assertTrue(resultado);
        verify(proveedorRepository).findById(1);
        verify(proveedorRepository).save(any(Proveedor.class));

    }

    @Test
    void buscarProveedorPorIDYdebeRetornarelIdProveedor() {

        LocalDate fechaRegistro = LocalDate.now();

        Proveedor proveedorExistente = new Proveedor(1, "Empresa lucida", "embotelladora", "123456789",
                "paicavi #234",
                fechaRegistro, TipoRecurso.HUMANO, new ArrayList<>());

        when(proveedorRepository.findById(anyInt())).thenReturn(Optional.of(proveedorExistente));

        Optional<Proveedor> resultado = proveedorService.findById(1);

        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
        assertEquals(proveedorExistente, resultado.get());
        assertEquals("Empresa lucida", resultado.get().getNombreEmpresa());
        assertEquals("embotelladora", resultado.get().getDescrProveedor());
        assertEquals(TipoRecurso.HUMANO, resultado.get().getTipoRecurso());

        verify(proveedorRepository).findById(1);
    }

}
