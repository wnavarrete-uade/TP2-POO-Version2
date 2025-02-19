import java.io.*;
import java.util.*;

/**
 * Clase Administrador que gestiona los pagos, reservas, cocheras e inquilinos.
 * Hereda de la clase Persona.
 */
class Administrador extends Persona {

    // Lista para almacenar todos los pagos realizados.
    private List<Pago> pagos;

    // Lista para almacenar todas las reservas realizadas.
    private List<Reserva> reservas;

    // Lista de cocheras disponibles o asignadas a inquilinos.
    private List<Cochera> cocheras;

    // Lista de inquilinos registrados en el sistema.
    private List<Inquilino> inquilinos;

    // Objeto para la gestión de lectura/escritura en archivos de texto.
    private GestorArchivo gestorArchivo;

    /**
     * Constructor de Administrador.
     * Inicializa las listas de pagos, reservas, cocheras e inquilinos.
     * También carga los datos de inquilinos y cocheras desde archivos.
     *
     * @param nombre   Nombre del administrador.
     * @param apellido Apellido del administrador.
     * @param dni      DNI del administrador.
     */
    public Administrador(String nombre, String apellido, String dni) {
        // Se inicializan las listas donde se almacenarán pagos, reservas, cocheras e inquilinos.
        this.pagos = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.cocheras = new ArrayList<>();
        this.inquilinos = new ArrayList<>();

        // Se inicializa el gestor de archivos para manejar la lectura/escritura.
        this.gestorArchivo = new GestorArchivo();

        // Se cargan inquilinos y cocheras desde sus respectivos archivos de texto.
        cargarInquilinos("inquilinos.txt");
        cargarCocheras("cocheras.txt");
    }

    /**
     * Registra un pago realizado por un inquilino.
     * Agrega el objeto Pago a la lista de pagos, lo guarda en el archivo "pagos.txt",
     * actualiza la información de pago en el archivo "cocheras.txt" y recarga
     * la lista de cocheras para mantener la información actualizada.
     *
     * @param pago Objeto Pago que contiene la información del pago.
     */
    public void registrarPago(Pago pago) {
        // Se agrega el pago a la lista local de pagos.
        pagos.add(pago);

        // Se guarda la información del pago en el archivo "pagos.txt".
        gestorArchivo.guardarEnArchivo("pagos.txt", pago.toString());

        // Se actualiza el archivo "cocheras.txt" para reflejar el estado de pago de la cochera.
        // Parámetros de 'actualizarArchivo': archivo, valor clave (número de cochera),
        // columna a actualizar (2 en este caso) y el nuevo valor ("true").
        gestorArchivo.actualizarArchivo("cocheras.txt", pago.getCochera().getNumero(), 2, "true");

        // Tras la actualización, se recarga la lista de cocheras para reflejar los cambios.
        cargarCocheras("cocheras.txt");

        // Mensaje de confirmación.
        System.out.println("Pago registrado con éxito.");
    }

    /**
     * Registra una reserva realizada por un inquilino.
     * Agrega el objeto Reserva a la lista de reservas y lo guarda en el archivo "reservas.txt".
     *
     * @param reserva Objeto Reserva con la información de la reserva.
     */
    public void reservarLugar(Reserva reserva) {
        // Se agrega la reserva a la lista local de reservas.
        reservas.add(reserva);

        // Se guarda la información de la reserva en el archivo "reservas.txt".
        gestorArchivo.guardarEnArchivo("reservas.txt", reserva.toString());
    }

    /**
     * Cancela una reserva existente, buscándola por su número.
     * El proceso de eliminación se realiza en el archivo de texto "reservas.txt".
     *
     * @param nroReserva Número de reserva a cancelar.
     */
    public void cancelarReserva(int nroReserva) {
        System.out.println("nroReserva " + nroReserva);
        eliminarReserva(nroReserva);
    }

    /**
     * Lista todas las cocheras registradas con su información relevante:
     * número, categoría, estado de pago e inquilino asociado.
     */
    public void listarCocheras() {
        System.out.println("Listado de Cocheras:");
        for (Cochera cochera : cocheras) {
            Inquilino inquilino = cochera.getInquilino();
            System.out.println("Cochera N°" + cochera.getNumero() + " | Categoría: " + cochera.getCategoria() +
                    " | Estado de pago: " + (cochera.isEstadoPago() ? "Pagado" : "Pendiente") +
                    " | Inquilino: " + inquilino.getNombre() + " " + inquilino.getApellido() + " (DNI: " + inquilino.getDni() + ")");
        }
    }

    /**
     * Lista todos los pagos registrados, leyendo y mostrando
     * la información desde el archivo "pagos.txt".
     */
    public void listarPagos() {
        System.out.println("Listado de Pagos:");
        gestorArchivo.leerDesdeArchivo("pagos.txt");
    }

    /**
     * Lista todas las reservas registradas, leyendo y mostrando
     * la información desde el archivo "reservas.txt".
     */
    public void listarReservas() {
        System.out.println("Listado de Reservas:");
        gestorArchivo.leerDesdeArchivo("reservas.txt");
    }

    /**
     * Devuelve la lista de inquilinos registrados.
     *
     * @return Lista de objetos Inquilino.
     */
    public List<Inquilino> listarInquilinos() {
        return inquilinos;
    }

    /**
     * Busca y devuelve la cochera asignada a un inquilino específico.
     *
     * @param inquilino Inquilino cuya cochera se desea encontrar.
     * @return Objeto Cochera asignado al inquilino, o null si no tiene una asignada.
     */
    public Cochera getCocheraPorInquilino(Inquilino inquilino) {
        for (Cochera cochera : cocheras) {
            // Se compara el inquilino de la cochera con el inquilino proporcionado.
            if (cochera.getInquilino().equals(inquilino)) {
                return cochera;
            }
        }
        return null;
    }

    /**
     * Carga los inquilinos desde el archivo de texto especificado y los almacena
     * en la lista de inquilinos. Cada línea del archivo debe tener el formato:
     * nombre,apellido,dni
     *
     * @param archivo Nombre del archivo que contiene los datos de los inquilinos.
     */
    private void cargarInquilinos(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            // Se leen línea a línea los datos del archivo.
            while ((linea = br.readLine()) != null) {
                // Separamos la línea por comas.
                String[] datos = linea.split(",");
                // Verificamos que tenga la cantidad correcta de campos.
                if (datos.length == 3) {
                    // Se crea un nuevo inquilino y se agrega a la lista.
                    inquilinos.add(new Inquilino(datos[0], datos[1], datos[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de inquilinos: " + e.getMessage());
        }
    }

    /**
     * Carga las cocheras desde el archivo de texto especificado y las almacena
     * en la lista de cocheras. Cada línea del archivo debe tener el formato:
     * numero,categoria,estadoPago,dniInquilino
     *
     * @param archivo Nombre del archivo que contiene los datos de las cocheras.
     */
    private void cargarCocheras(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            // Se limpia la lista antes de volver a cargarla, para evitar duplicados.
            cocheras.clear();
            // Se leen línea a línea los datos del archivo.
            while ((linea = br.readLine()) != null) {
                // Separamos la línea por comas.
                String[] datos = linea.split(",");
                // Verificamos que tenga la cantidad correcta de campos.
                if (datos.length == 4) {
                    // Se busca el inquilino por DNI para asociarlo a la cochera.
                    Inquilino inquilino = buscarInquilinoPorDNI(datos[3]);
                    if (inquilino != null) {
                        // Se crea y agrega la cochera a la lista local.
                        cocheras.add(new Cochera(
                                Integer.parseInt(datos[0]), // Número de cochera
                                datos[1],                   // Categoría
                                Boolean.parseBoolean(datos[2]), // Estado de pago
                                inquilino                   // Inquilino asociado
                        ));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de cocheras: " + e.getMessage());
        }
    }

    /**
     * Busca un inquilino en la lista de inquilinos a partir de su DNI.
     *
     * @param dni DNI del inquilino a buscar.
     * @return Objeto Inquilino si se encuentra; null en caso contrario.
     */
    private Inquilino buscarInquilinoPorDNI(String dni) {
        for (Inquilino inquilino : inquilinos) {
            if (inquilino.getDni().equals(dni)) {
                return inquilino;
            }
        }
        return null;
    }

    /**
     * Elimina una reserva del archivo "reservas.txt" buscando por el número de reserva.
     * Si se encuentra, se omite al reescribir el contenido del archivo con el resto de las reservas.
     *
     * @param numeroReserva Número de la reserva que se desea eliminar.
     */
    private static void eliminarReserva(int numeroReserva) {
        // Lista temporal para almacenar todas las líneas que no correspondan a la reserva eliminada.
        List<String> lineas = new ArrayList<>();
        boolean reservaEncontrada = false;

        // Primero, se lee todo el contenido del archivo y se excluye la línea que contenga la reserva buscada.
        try (BufferedReader br = new BufferedReader(new FileReader("reservas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Verificamos que la línea no sea la reserva que queremos eliminar.
                // Se asume que la línea de la reserva empieza con "Reserva N°{nroReserva} |".
                if (!linea.startsWith("Reserva N°" + numeroReserva + " |")) {
                    lineas.add(linea);
                } else {
                    reservaEncontrada = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de reservas: " + e.getMessage());
            return;
        }

        // Si no se encontró la reserva, se notifica y se finaliza el método.
        if (!reservaEncontrada) {
            System.out.println("Error: No se encontró una reserva con el número " + numeroReserva);
            return;
        }

        // Se reescribe el archivo de reservas con la lista actualizada de líneas.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reservas.txt"))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar el archivo de reservas: " + e.getMessage());
        }

        // Mensaje de confirmación para el usuario.
        System.out.println("Reserva N°" + numeroReserva + " eliminada con éxito.");
    }
}