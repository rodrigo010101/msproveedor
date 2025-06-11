package com.edutech.msproveedor.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import com.edutech.msproveedor.service.ProveedorService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)

public class ProveedorTest {

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

        verify(proveedorRepository.findAll());

    }

}
