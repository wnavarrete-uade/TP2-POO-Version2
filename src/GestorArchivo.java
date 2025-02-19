import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase GestorArchivo que ofrece métodos para:
 * <ul>
 *     <li>Guardar contenido en un archivo de texto.</li>
 *     <li>Leer y mostrar el contenido de un archivo de texto.</li>
 *     <li>Actualizar valores específicos dentro de un archivo de texto.</li>
 * </ul>
 */
public class GestorArchivo {

    /**
     * Guarda información en un archivo de texto, añadiéndola al final (append).
     *
     * @param archivo   Nombre o ruta del archivo donde se guardará la información.
     * @param contenido Cadena de texto que se escribirá en el archivo.
     */
    public static void guardarEnArchivo(String archivo, String contenido) {
        // Se utiliza un try-with-resources para asegurar el cierre del BufferedWriter.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            // Escribe el contenido en el archivo y añade una nueva línea.
            bw.write(contenido);
            bw.newLine();
        } catch (IOException e) {
            // Si ocurre un error de lectura/escritura, se notifica al usuario.
            System.out.println("Error al guardar en archivo: " + e.getMessage());
        }
    }

    /**
     * Lee y muestra el contenido de un archivo de texto línea por línea.
     *
     * @param archivo Nombre o ruta del archivo a leer.
     */
    public static void leerDesdeArchivo(String archivo) {
        // Se utiliza un try-with-resources para asegurar el cierre del BufferedReader.
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            // Se lee cada línea del archivo hasta llegar al final (null).
            while ((linea = br.readLine()) != null) {
                // Muestra cada línea en consola.
                System.out.println(linea);
            }
        } catch (IOException e) {
            // Si ocurre un error de lectura/escritura, se notifica al usuario.
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Actualiza un valor específico dentro de un archivo de texto.
     * La lógica de este método asume que:
     * <ul>
     *     <li>La primera columna (índice 0) es un identificador único (ej. ID, número de cochera, etc.).</li>
     *     <li>Los campos de cada línea están separados por comas (CSV simple).</li>
     *     <li>El campo a actualizar se especifica por un índice.</li>
     * </ul>
     *
     * @param archivo         Nombre o ruta del archivo a actualizar.
     * @param id              Identificador del registro (en la primera columna) a modificar.
     * @param campoActualizar Índice del campo dentro de la línea CSV que se desea modificar.
     * @param nuevoValor      Nuevo valor a establecer en ese campo.
     */
    public static void actualizarArchivo(String archivo, int id, int campoActualizar, String nuevoValor) {
        // Lista para almacenar temporalmente todas las líneas del archivo (modificadas o no).
        List<String> lineas = new ArrayList<>();

        // Primero, se lee todo el contenido del archivo, línea por línea.
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Se asume que cada línea es un CSV con campos separados por comas.
                String[] datos = linea.split(",");
                /*
                 * Si el primer campo (datos[0]) coincide con 'id', se actualiza el
                 * campo indicado por 'campoActualizar' con el 'nuevoValor'.
                 */
                if (Integer.parseInt(datos[0]) == id) {
                    datos[campoActualizar] = nuevoValor;
                }
                // Se reconstruye la línea modificada y se agrega a la lista.
                lineas.add(String.join(",", datos));
            }
        } catch (IOException e) {
            // Mensaje de error en caso de problemas de lectura.
            System.out.println("Error al leer el archivo " + archivo + ": " + e.getMessage());
        }

        // Luego, se reescribe el archivo con las líneas actualizadas.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (String lineaModificada : lineas) {
                bw.write(lineaModificada);
                bw.newLine();
            }
        } catch (IOException e) {
            // Mensaje de error en caso de problemas de escritura.
            System.out.println("Error al actualizar el archivo " + archivo + ": " + e.getMessage());
        }
    }
}