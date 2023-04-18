package com.gasto.gasto.Controlador;

import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Service.GestorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
public class GestorControllerTest {

    @Mock
    private GestorService gestorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        GestorController gestorController = new GestorController(gestorService);
    }



}

