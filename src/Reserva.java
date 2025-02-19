import java.io.*;

/**
 * Clase que representa una Reserva, identificada por un número único, fecha,
 * el inquilino que la realiza y el lugar reservado.
 */
class Reserva {

    // Número de la reserva (se asigna automáticamente a partir del último número registrado).
    private int nroReserva;

    // Fecha en la que se realiza la reserva (formato manejado externamente).
    private String fecha;

    // Inquilino que realiza la reserva.
    private Inquilino inquilino;

    // Lugar que se ha reservado (por ejemplo, PARRILLA, SUM, PILETA).
    private Lugar lugar;

    /**
     * Constructor de la clase Reserva. Asigna el número de reserva tomando
     * como base el último número en el archivo "reservas.txt".
     *
     * @param fecha      Cadena que representa la fecha de la reserva.
     * @param inquilino  Inquilino que realiza la reserva.
     * @param lugar      Lugar a reservar.
     */
    public Reserva(String fecha, Inquilino inquilino, Lugar lugar) {
        // Se asigna el número de reserva al valor máximo actual +1.
        this.nroReserva = obtenerUltimoNroReserva() + 1;
        this.fecha = fecha;
        this.inquilino = inquilino;
        this.lugar = lugar;
    }

    /**
     * Método privado que lee el archivo "reservas.txt" para obtener el
     * mayor número de reserva registrado hasta el momento.
     *
     * @return El último número de reserva registrado. Si no hay registros,
     *         se devuelve 0.
     */
    private static int obtenerUltimoNroReserva() {
        int ultimoNumero = 0;
        // Uso de try-with-resources para asegurar el cierre del BufferedReader.
        try (BufferedReader br = new BufferedReader(new FileReader("reservas.txt"))) {
            String linea;
            // Se leen las líneas del archivo para extraer el número de reserva.
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length > 0) {
                    try {
                        // Ejemplo de línea: "Reserva N°123 | Fecha: ... | Lugar: ... | Inquilino: ..."
                        String numeroReservaTexto = partes[0].replace("Reserva N°", "").trim();
                        // Se convierte a entero y se compara con el último número encontrado.
                        ultimoNumero = Math.max(ultimoNumero, Integer.parseInt(numeroReservaTexto));
                    } catch (NumberFormatException e) {
                        System.out.println("Error al leer el número de reserva en el archivo.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de reservas: " + e.getMessage());
        }
        // Devuelve el número más alto encontrado.
        return ultimoNumero;
    }

    /**
     * Genera la representación en cadena de la reserva, que incluye:
     * número de reserva, fecha, lugar e inquilino.
     *
     * @return Cadena representando la información de la reserva.
     */
    @Override
    public String toString() {
        return "Reserva N°" + nroReserva
                + " | Fecha: " + fecha
                + " | Lugar: " + lugar
                + " | Inquilino: " + inquilino.getNombre() + " " + inquilino.getApellido();
    }
}