package com.ada.exception;

/**
 * Excepción no verificada que indica que se intentó crear un archivo que ya
 * existe.
 *
 * @author Luis Monterroso
 * @version 1.0
 * @since 2025-07-17
 */
public class ArchivoYaExisteException extends Exception {

    /**
     * Crea una nueva excepción con el mensaje especificado.
     *
     * @param message El mensaje que describe la causa del error.
     */
    public ArchivoYaExisteException(String message) {
        super(message);
    }
}
