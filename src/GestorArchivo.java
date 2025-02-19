import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivo {

    /**
     * Guarda información en un archivo.
     * @param archivo   Nombre del archivo.
     * @param contenido Contenido a guardar.
     */
     public static void guardarEnArchivo(String archivo, String contenido) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(contenido);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar en archivo: " + e.getMessage());
        }
    }

    /**
     * Lee y muestra el contenido de un archivo.
     * @param archivo Nombre del archivo a leer.
     */
    public static void leerDesdeArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Actualiza un valor específico dentro de un archivo de texto.
     * @param archivo        Nombre del archivo a actualizar.
     * @param id             Identificador del registro a modificar.
     * @param campoActualizar Índice del campo a modificar en la línea.
     * @param nuevoValor     Nuevo valor a establecer en el archivo.
     */
    public static void actualizarArchivo(String archivo, int id, int campoActualizar, String nuevoValor) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (Integer.parseInt(datos[0]) == id) {
                    datos[campoActualizar] = nuevoValor;
                }
                lineas.add(String.join(",", datos));
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo " + archivo + ": " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar el archivo " + archivo + ": " + e.getMessage());
        }
    }
}
