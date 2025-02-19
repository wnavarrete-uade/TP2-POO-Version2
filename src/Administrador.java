import java.io.*;
import java.util.*;

/**
 * Clase Administrador que gestiona los pagos, reservas, cocheras e inquilinos.
 */
class Administrador extends Persona {
    private List<Pago> pagos;
    private List<Reserva> reservas;
    private List<Cochera> cocheras;
    private List<Inquilino> inquilinos;
    private GestorArchivo gestorArchivo;

    /**
     * Constructor de Administrador.
     * Inicializa las listas de pagos, reservas, cocheras e inquilinos.
     * También carga los datos de inquilinos y cocheras desde archivos.
     */
    public Administrador(String nombre, String apellido, String dni) {
//        super(nombre, apellido, dni);
        this.pagos = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.cocheras = new ArrayList<>();
        this.inquilinos = new ArrayList<>();
        this.gestorArchivo = new GestorArchivo();
        cargarInquilinos("inquilinos.txt");
        cargarCocheras("cocheras.txt");
    }

    /**
     * Registra un pago realizado por un inquilino.
     * @param pago Objeto Pago que contiene la información del pago.
     */
    public void registrarPago(Pago pago) {
        pagos.add(pago);
        gestorArchivo.guardarEnArchivo("pagos.txt", pago.toString());
        gestorArchivo.actualizarArchivo("cocheras.txt", pago.getCochera().getNumero(), 2, "true");
        cargarCocheras("cocheras.txt");
        System.out.println("Pago registrado con éxito.");
    }

    /**
     * Registra una reserva realizada por un inquilino.
     * @param reserva Objeto Reserva con la información de la reserva.
     */
    public void reservarLugar(Reserva reserva) {
        reservas.add(reserva);
        gestorArchivo.guardarEnArchivo("reservas.txt", reserva.toString());
        System.out.println("Reserva registrada con éxito.");
    }

    /**
     * Cancela una reserva existente.
     * @param nroReserva Número de reserva a cancelar.
     */
    public void cancelarReserva(int nroReserva) {
        System.out.println("nroReserva " + nroReserva);
        eliminarReserva(nroReserva);
    }

    /**
     * Lista todas las cocheras registradas con su información relevante.
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
     * Lista todos los pagos registrados.
     */
    public void listarPagos() {
        System.out.println("Listado de Pagos:");
        gestorArchivo.leerDesdeArchivo("pagos.txt");
    }

    /**
     * Lista todas las reservas registradas.
     */
    public void listarReservas() {
        System.out.println("Listado de Reservas:");
        gestorArchivo.leerDesdeArchivo("reservas.txt");
    }

    /**
     * Devuelve la lista de inquilinos registrados.
     * @return Lista de inquilinos.
     */
    public List<Inquilino> listarInquilinos() {
        return inquilinos;
    }

    /**
     * Busca y devuelve una cochera asignada a un inquilino específico.
     * @param inquilino Inquilino cuya cochera se desea encontrar.
     * @return Cochera asignada al inquilino, o null si no tiene una asignada.
     */
    public Cochera getCocheraPorInquilino(Inquilino inquilino) {
        for (Cochera cochera : cocheras) {
            if (cochera.getInquilino().equals(inquilino)) {
                return cochera;
            }
        }
        return null;
    }

    /**
     * Carga los inquilinos desde un gestorArchivo de texto y los almacena en la lista de inquilinos.
     * @param archivo Nombre del gestorArchivo que contiene los datos de los inquilinos.
     */
    private void cargarInquilinos(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    inquilinos.add(new Inquilino(datos[0], datos[1], datos[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el gestorArchivo de inquilinos: " + e.getMessage());
        }
    }

    /**
     * Carga las cocheras desde un gestorArchivo de texto y las almacena en la lista de cocheras.
     * @param archivo Nombre del gestorArchivo que contiene los datos de las cocheras.
     */
    private void cargarCocheras(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    Inquilino inquilino = buscarInquilinoPorDNI(datos[3]);
                    if (inquilino != null) {
                        cocheras.add(new Cochera(Integer.parseInt(datos[0]), datos[1], Boolean.parseBoolean(datos[2]), inquilino));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el gestorArchivo de cocheras: " + e.getMessage());
        }
    }

    /**
     * Busca un inquilino en la lista de inquilinos a partir de su DNI.
     * @param dni DNI del inquilino a buscar.
     * @return Objeto Inquilino si se encuentra, null en caso contrario.
     */
    private Inquilino buscarInquilinoPorDNI(String dni) {
        for (Inquilino inquilino : inquilinos) {
            if (inquilino.getDni().equals(dni)) {
                return inquilino;
            }
        }
        return null;
    }

    private static void eliminarReserva(int numeroReserva) {
        List<String> lineas = new ArrayList<>();
        boolean reservaEncontrada = false;

        try (BufferedReader br = new BufferedReader(new FileReader("reservas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.startsWith("Reserva N°" + numeroReserva + " |")) {
                    lineas.add(linea);
                } else {
                    reservaEncontrada = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el gestorArchivo de reservas: " + e.getMessage());
            return;
        }

        if (!reservaEncontrada) {
            System.out.println("Error: No se encontró una reserva con el número " + numeroReserva);
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reservas.txt"))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar el gestorArchivo de reservas: " + e.getMessage());
        }

        System.out.println("Reserva N°" + numeroReserva + " eliminada con éxito.");
    }
}

