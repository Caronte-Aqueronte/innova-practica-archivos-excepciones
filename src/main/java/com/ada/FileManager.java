package com.ada;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.ada.exception.ArchivoNoEncontradoException;
import com.ada.exception.ArchivoYaExisteException;
/**
 *
 *
 * @author Luis Monterroso
 * @version 1.0
 * @since 2025-07-18
 */
public class FileManager {
    private static final String MENSAJE_CONTENIDO_DOCUMENTO = "------------------Condenido del archivo %s-----------------";
    private static final String MENSAJE_LINEA = "-----------------Linea 3%s del archivo-----------------";
    private static final String MENSAJE_LINEA_NO_ENCONTRADA = "No se encontro la linea especificada.";
    private static final String MENSAJE_INGRESE_NOMBRE_ARCHIVO = "Ingrese el nombre del archivo:";
    private static final String MENSAJE_NOMBRE_ARCHIVO_INVALIDO = "Nombre de archivo invalido.";
    private static final String MENSAJE_OPCION_INVALIDA = "Numero de opcion invalido.";
    private static final String MENSAJE_LINEA_CERO_INVALIDA = "El número de línea debe ser mayor a 0.";

    private static final String MENSAJE_CREANDOLO = "Creandolo...";
    private static final String MENSAJE_INGRESE_NUEVA_LINEA = "Ingrese una nueva línea para agregar al archivo:";
    private static final String MENSAJE_INGRESE_NUMERO_LINEA = "Ingrese el número de línea que desea visualizar:";
    private static final String MENU = "\n╔═════════════════════════════════════╗\n" +
            "║         Hola de nuevo! :)           ║\n" +
            "╠═════════════════════════════════════╣\n" +
            "  1. Agregar nuevas líneas           \n" +
            "  2. Mostrar contenido completo      \n" +
            "  3. Mostrar una línea específica    \n" +
            "  4. Salir del programa              \n" +
            "═════════════════════════════════════\n" +
            ">> Ingresa una opción (1-4): ";

    private static final Integer NUMERO_PARA_SALIR = 4;

    public static void main(String[] args) {

        Integer opcion = 0;
        String nombreArchivo;

        try (Scanner scanner = new Scanner(System.in)) {

            // pide el nombre del archivo al usuario
            System.out.println(MENSAJE_INGRESE_NOMBRE_ARCHIVO);
            nombreArchivo = scanner.nextLine();

            // verifica que la entrada este inicializada y no este en blanco
            if (nombreArchivo == null || nombreArchivo.isBlank()) {
                System.err.println(MENSAJE_NOMBRE_ARCHIVO_INVALIDO);
                return;
            }

            // verifica la exitencia del archivo, si no existe lo intenta crear
            try {
                verificarArchivo(nombreArchivo);
            } catch (ArchivoNoEncontradoException e) {
                System.err.println(e.getMessage());
                System.out.println(MENSAJE_CREANDOLO);

                try {
                    crearArchivo(nombreArchivo);
                } catch (ArchivoYaExisteException e2) {
                    System.err.println(e2.getMessage());
                }

            }

            // sigue hasta que el usuario elija salir de la app
            while (opcion != NUMERO_PARA_SALIR) {

                System.out.println(MENU);// muestra el menu
                opcion = scanner.nextInt();// lee la entrada del usuario
                scanner.nextLine();

                if (opcion > 0 && opcion < 5) {

                    switch (opcion) {
                        case 1:
                            agregarNuevaLineaAArchivo(scanner, nombreArchivo);
                            break;
                        case 2:
                            leerYMostrarContenidoDeArchivo(nombreArchivo);
                            break;
                        case 3:
                            leerYMostrarUnaLineaEspecificaDelArchivo(scanner, nombreArchivo);
                            break;
                        default:
                            break;
                    }

                } else {
                    System.err.println(MENSAJE_OPCION_INVALIDA);// muestra opcion invalida
                }

            }

        } catch (Exception e) {
            throw e;
        }

    }

    // Metodo que pide al usuario una entrada para agregarla al archivo
    private static void agregarNuevaLineaAArchivo(Scanner scanner, String nombreArchivo) {
        System.out.println(MENSAJE_INGRESE_NUEVA_LINEA);
        String nuevaLinea = scanner.nextLine();
        agregarNuevaLineaAArchivo(nuevaLinea, nombreArchivo);
    }

    // Metodo que pide al usuario un numero de linea para mostrarla
    private static void leerYMostrarUnaLineaEspecificaDelArchivo(Scanner scanner, String nombreArchivo) {
        System.out.println(MENSAJE_INGRESE_NUMERO_LINEA);
        Integer numeroLineaArchivo = scanner.nextInt();
        scanner.nextLine();
        leerYMostrarUnaLineaEspecificaDelArchivo(nombreArchivo, numeroLineaArchivo);
    }

    // Método para verificar la existencia de un archivo
    private static void verificarArchivo(String nombreArchivo) throws ArchivoNoEncontradoException {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            throw new ArchivoNoEncontradoException("El archivo no existe.");
        }
    }

    // Método para crear un archivo
    private static void crearArchivo(String nombreArchivo) throws ArchivoYaExisteException {
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            throw new ArchivoYaExisteException("El archivo ya existe.");
        } else {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Metodo para agregar una nueva linea a un archivo
    private static void agregarNuevaLineaAArchivo(String nuevaLinea, String nombreArchivo) {
        // crea el writer, con append en true para que adjunte nuevo contenido y
        // establece el canal de escritura
        try (FileWriter writer = new FileWriter(nombreArchivo, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(nuevaLinea + "\n");// escribimos en el archivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo para leer y mostrar un archivo
    private static void leerYMostrarContenidoDeArchivo(String nombreArchivo) {
        try (FileReader reader = new FileReader(nombreArchivo);
                BufferedReader bufferedReader = new BufferedReader(reader)) {
            imprimirMensaje(MENSAJE_CONTENIDO_DOCUMENTO, nombreArchivo);// imprime un mensaje para el usuario
            // obtiene el stream que representa las lineas del documento, las recorre y las
            // va imprimiendo
            bufferedReader.lines().forEach(linea -> System.out.println(linea));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo para leer y mostrar una linea especifica del archivo
    private static void leerYMostrarUnaLineaEspecificaDelArchivo(String nombreArchivo, Integer numeroLineaArchivo) {

        if (numeroLineaArchivo <= 0) {
            System.out.println(MENSAJE_LINEA_CERO_INVALIDA);
            return;
        }

        try (FileReader reader = new FileReader(nombreArchivo);
                BufferedReader bufferedReader = new BufferedReader(reader)) {

            imprimirMensaje(MENSAJE_LINEA, numeroLineaArchivo);// imprime un mensaje para el usuario

            String linea = bufferedReader.lines() // obtiene el stream con las lineas
                    .skip(numeroLineaArchivo - 1) // salta los elementos anteriores a la linea inidcada (empezando de 0)
                    .findFirst()// obtiene el primer elemento de los restantes (la linea especificada)
                    .orElse(null);// obtiene el elemento, si se encuentra, si obtiene nulo

            if (linea != null) {// evalua que la linea haya sido encontrada
                System.out.println(linea);
            } else {
                System.out.println(MENSAJE_LINEA_NO_ENCONTRADA);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void imprimirMensaje(String mensaje, Object parametroImprimible) {
        // formatea el mensaje al usuario
        System.out.println(String.format(mensaje, parametroImprimible));
    }
}