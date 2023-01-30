package tech.ada.testes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MediaTest {

    private LeitorTeclado leitorTeclado = Mockito.mock(LeitorTeclado.class);
    private Media media;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() {
        media = new Media(leitorTeclado);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void testPrimeiroNegativo() {
        when(leitorTeclado.getNumero(any())).thenReturn(-1);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> media.calula());

        assertEquals("Nenhum valor digitado para fazer o cálculo de média.", e.getMessage());
    }

    @Test
    void testDoisValores() {
        when(leitorTeclado.getNumero(any()))
                .thenReturn(2)
                .thenReturn(8)
                .thenReturn(-10);

        double resultado = media.calula();

        assertEquals("O resultado da média das 2 provas é de 5,00", outContent.toString());
        verify(leitorTeclado, times(3)).getNumero(any());
        assertEquals(5, resultado);

    }

}