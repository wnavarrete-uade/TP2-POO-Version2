import java.io.*;
import java.util.*;

class Administrador extends Persona {
    private List<Pago> pagos;
    private List<Reserva> reservas;
    private List<Cochera> cocheras;
    private List<Inquilino> inquilinos;

    public Administrador(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
        this.pagos = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.cocheras = new ArrayList<>();
        this.inquilinos = new ArrayList<>();
        cargarInquilinosDesdeArchivo("inquilinos.txt");
        cargarCocherasDesdeArchivo("cocheras.txt");
    }

    public void registrarPago(Pago pago) {
        pagos.add(pago);
        guardarEnArchivo("pagos.txt", pago.toString());
        System.out.println("Pago registrado con éxito.");
    }

    public void reservarLugar(Reserva reserva) {
        reservas.add(reserva);
        guardarEnArchivo("reservas.txt", reserva.toString());
        System.out.println("Reserva registrada con éxito.");
    }

    public void cancelarReserva(int nroReserva) {
        reservas.removeIf(reserva -> reserva.getNroReserva() == nroReserva);
        System.out.println("Reserva cancelada.");
    }

    public void listarCocheras() {
        System.out.println("Listado de Cocheras:");
        for (Cochera cochera : cocheras) {
            System.out.println(cochera);
        }
    }

    public void listarPagos() {
        System.out.println("Listado de Pagos:");
        leerDesdeArchivo("pagos.txt");
    }

    public void listarReservas() {
        System.out.println("Listado de Reservas:");
        for (Reserva reserva : reservas) {
            System.out.println(reserva);
        }
    }

    public List<Inquilino> getInquilinos() {
        return inquilinos;
    }

    public Cochera getCocheraPorInquilino(Inquilino inquilino) {
        for (Cochera cochera : cocheras) {
            if (cochera.getInquilino().equals(inquilino)) {
                return cochera;
            }
        }
        return null;
    }

    private void cargarInquilinosDesdeArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    inquilinos.add(new Inquilino(datos[0], datos[1], datos[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de inquilinos: " + e.getMessage());
        }
    }

    private void cargarCocherasDesdeArchivo(String archivo) {
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
            System.out.println("Error al leer el archivo de cocheras: " + e.getMessage());
        }
    }

    private Inquilino buscarInquilinoPorDNI(String dni) {
        for (Inquilino inquilino : inquilinos) {
            if (inquilino.getDni().equals(dni)) {
                return inquilino;
            }
        }
        return null;
    }

    private void guardarEnArchivo(String archivo, String contenido) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(contenido);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar en archivo: " + e.getMessage());
        }
    }

    private void leerDesdeArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
