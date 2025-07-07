package com.edutech.msproveedor.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.edutech.msproveedor.model.Proveedor;
import com.edutech.msproveedor.service.ProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProveedorController.class) // indicamos que se esta probando el controllador de Proveedor

public class ProveedorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProveedorService proveedorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Proveedor proveedor;

    @BeforeEach
    void setUp() {
        // configurar un objeto de respaldo
        proveedor = new Proveedor();
        proveedor.setIdproveedor(1);
        proveedor.setNombreEmpresa("cokitas");
        proveedor.setDescrProveedor("CALLS_REAL_METHODS");
    }

    @Test
    public void testGetAllProveedor() throws Exception {

        // mock llama al metodo findAll
        when(proveedorService.findAll()).thenReturn(List.of(proveedor));

        // realiza la peticion get
        mockMvc.perform(get("/api/v1/proveedor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idproveedor").value(1))
                .andExpect(jsonPath("$[0].nombreEmpresa").value("cokitas"))
                .andExpect(jsonPath("$[0].descrProveedor").value("CALLS_REAL_METHODS"));
    }

    @Test
    public void testGetIdProveedor() throws Exception {
        Integer id = 1;
        Proveedor proveedor = new Proveedor();
        proveedor.setIdproveedor(id);
        proveedor.setNombreEmpresa("cokitas");
        proveedor.setDescrProveedor("CALLS_REAL_METHODS");

        when(proveedorService.findById(anyInt())).thenReturn(Optional.of(proveedor));

        mockMvc.perform(get("/api/v1/proveedor/{idproveedor}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idproveedor").value(1))
                .andExpect(jsonPath("$.nombreEmpresa").value("cokitas"))
                .andExpect(jsonPath("$.descrProveedor").value("CALLS_REAL_METHODS"));
    }

    @Test
    public void testPostcrearProveedor() throws Exception {
        when(proveedorService.save(any(Proveedor.class))).thenReturn(proveedor);

        mockMvc.perform(post("/api/v1/proveedor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(jsonPath("$.idproveedor").value(1))
                .andExpect(jsonPath("$.nombreEmpresa").value("cokitas"))
                .andExpect(jsonPath("$.descrProveedor").value("CALLS_REAL_METHODS"));
    }

    @Test
    public void testPutActualizarProveedor() throws Exception {
        when(proveedorService.update(eq(1), any(Proveedor.class))).thenReturn(true);

        mockMvc.perform(put("/api/v1/proveedor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(jsonPath("$.idproveedor").value(1))
                .andExpect(jsonPath("$.nombreEmpresa").value("cokitas"))
                .andExpect(jsonPath("$.descrProveedor").value("CALLS_REAL_METHODS"));
    }

    @Test
    public void testDeletePorId() throws Exception {
        Integer id = 1;
        Proveedor proveedor = new Proveedor();
        proveedor.setIdproveedor(id);

        when(proveedorService.findById(id)).thenReturn(Optional.of(proveedor));

        doNothing().when(proveedorService).deleteById(any());

        mockMvc.perform(delete("/api/v1/proveedor/{idproveedor}", id))
                .andExpect(status().isOk());
        verify(proveedorService, times(1)).deleteById(1);
    }
}
