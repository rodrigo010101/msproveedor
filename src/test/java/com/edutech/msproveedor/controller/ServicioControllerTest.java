package com.edutech.msproveedor.controller;
// Test Controller

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.edutech.msproveedor.model.Proveedor;
import com.edutech.msproveedor.model.ServicioContratado;
import com.edutech.msproveedor.service.ServicioContratadoService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServicioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioContratadoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ServicioContratado servicioContratado;

    @BeforeEach
    void setUp() {
        Proveedor proveedor = new Proveedor();
        // configuracion del objeto ServicioContratado
        servicioContratado = new ServicioContratado();
        servicioContratado.setIdservicio(1);
        servicioContratado.setDescripcionServicio("Arriendo de libros");
        servicioContratado.setProveedor(proveedor);
    }

    @Test
    public void getAllServicio() throws Exception {
        when(service.findAll()).thenReturn(List.of(servicioContratado));

        mockMvc.perform(get("/api/v1/servicio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idservicio").value(1))
                .andExpect(jsonPath("$[0].descripcionServicio").value("Arriendo de libros"))
                .andExpect(jsonPath("$[0].proveedor").value(servicioContratado.getProveedor()));
    }

    @Test
    public void getBuscarServicioId() throws Exception {
        when(service.findById(anyInt())).thenReturn(Optional.of(servicioContratado));

        mockMvc.perform(get("/api/v1/servicio/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".idproveedor").value(1))
                .andExpect(jsonPath("$.descripcionServicio").value("Arriendo de libros"))
                .andExpect(jsonPath("$.proveedor").value(servicioContratado.getProveedor()));
    }

    @Test
    public void postCrearServiciocontratado() throws Exception {
        when(service.save(any(ServicioContratado.class))).thenReturn(servicioContratado);

        mockMvc.perform(post("/api/v1/servicio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servicioContratado)))
                .andExpect(jsonPath(".idproveedor").value(1))
                .andExpect(jsonPath("$.descripcionServicio").value("Arriendo de libros"))
                .andExpect(jsonPath("$.proveedor").value(servicioContratado.getProveedor()));
    }

    @Test
    public void eliminarServicioPorId() throws Exception {
        doNothing().when(service).deleteById(anyInt());
        mockMvc.perform(delete("/api/v1/servicio/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteById(1);
    }

    @Test
    public void actualizarServicioContratado() throws Exception {
        when(service.save(any(ServicioContratado.class))).thenReturn(servicioContratado);

        mockMvc.perform(put("/api/v1/servicio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servicioContratado)))
                .andExpect(jsonPath(".idproveedor").value(1))
                .andExpect(jsonPath("$.descripcionServicio").value("Arriendo de libros"))
                .andExpect(jsonPath("$.proveedor").value(servicioContratado.getProveedor()));

    }
}
