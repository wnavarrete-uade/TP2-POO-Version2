import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Reserva {

    private int nroReserva;
    private String fecha;
    private Inquilino inquilino;
    private Lugar lugar;

    public Reserva(String fecha, Inquilino inquilino, Lugar lugar) {
        this.nroReserva = obtenerUltimoNroReserva() + 1;
        this.fecha = fecha;
        this.inquilino = inquilino;
        this.lugar = lugar;
    }

    /**
     * Obtiene el último número de reserva registrado en el archivo de reservas.
     * @return Último número de reserva registrado.
     */
    private static int obtenerUltimoNroReserva() {
        int ultimoNumero = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("reservas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length > 0) {
                    try {
                        String numeroReservaTexto = partes[0].replace("Reserva N°", "").trim();
                        ultimoNumero = Math.max(ultimoNumero, Integer.parseInt(numeroReservaTexto));
                    } catch (NumberFormatException e) {
                        System.out.println("Error al leer el número de reserva en el archivo.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de reservas: " + e.getMessage());
        }
        return ultimoNumero;
    }



    @Override
    public String toString() {
        return "Reserva N°" + nroReserva + " | Fecha: " + fecha + " | Lugar: " + lugar + " | Inquilino: " + inquilino.getNombre() + " " + inquilino.getApellido();
    }


}