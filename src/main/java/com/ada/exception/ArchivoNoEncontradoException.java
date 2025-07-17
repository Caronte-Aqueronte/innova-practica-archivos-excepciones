package com.ada.exception;

/**
 * Excepción personalizada que indica que un archivo no fue encontrado.
 *
 * @author Luis Monterroso
 * @version 1.0
 * @since 2025-07-17
 */
public class ArchivoNoEncontradoException extends Exception {

    /**
     * Crea una nueva excepción con el mensaje especificado.
     *
     * @param message El mensaje que describe la causa del error.
     */
    public ArchivoNoEncontradoException(String message) {
        super(message);
    }
}
