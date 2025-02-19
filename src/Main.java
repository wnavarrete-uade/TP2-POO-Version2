import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que gestiona el menú de opciones para un sistema de pagos y reservas.
 */
public class Main {

    public static void main(String[] args) {
        // Se crea un objeto Scanner para leer datos desde la consola.
        Scanner scanner = new Scanner(System.in);

        // Se crea un Administrador con datos de ejemplo.
        Administrador admin = new Administrador("Juan", "Perez", "12345678");

        // Ciclo infinito para mostrar el menú hasta que el usuario decida salir.
        while (true) {
            mostrarMenu();                 // Muestra las opciones disponibles en el menú.
            int opcion = scanner.nextInt();
            scanner.nextLine();           // Limpia el buffer del Scanner (después de leer un int).

            switch (opcion) {
                case 1:
                    // Opción para registrar un pago.
                    registrarPago(scanner, admin);
                    break;
                case 2:
                    // Opción para reservar un lugar (PARRILLA, SUM, PILETA).
                    reservarLugar(scanner, admin);
                    break;
                case 3:
                    // Opción para cancelar una reserva específica, identificada por su número.
                    mostrarTitulo("Cancelar Reserva");
                    System.out.print("Ingrese número de reserva a cancelar: ");
                    int nroReserva = scanner.nextInt();
                    admin.cancelarReserva(nroReserva);
                    esperarEntrada(scanner);
                    break;
                case 4:
                    // Opción para listar todas las cocheras disponibles en el sistema.
                    mostrarTitulo("Listar Cocheras");
                    admin.listarCocheras();
                    esperarEntrada(scanner);
                    break;
                case 5:
                    // Opción para listar todos los pagos realizados.
                    mostrarTitulo("Listar Pagos");
                    admin.listarPagos();
                    esperarEntrada(scanner);
                    break;
                case 6:
                    // Opción para listar todas las reservas realizadas.
                    mostrarTitulo("Listar Reservas");
                    admin.listarReservas();
                    esperarEntrada(scanner);
                    break;
                case 7:
                    // Opción para salir del sistema.
                    System.out.println("Saliendo...");
                    scanner.close(); // Se cierra el Scanner antes de terminar.
                    return;
                default:
                    // Mensaje para indicar que la opción ingresada no es válida.
                    System.out.println("\n\u001B[31mOpción no válida.\u001B[0m\n");
                    esperarEntrada(scanner);
            }
        }
    }

    /**
     * Método para registrar un pago realizado por un inquilino.
     * @param scanner Objeto Scanner para leer la entrada desde la consola.
     * @param admin   Administrador que gestionará los pagos.
     */
    private static void registrarPago(Scanner scanner, Administrador admin) {
        mostrarTitulo("Registrar Pago");

        // Se selecciona el inquilino de la lista de inquilinos disponibles.
        Inquilino inquilino = seleccionarInquilino(scanner, admin.listarInquilinos());
        if (inquilino == null) return;  // Si no se selecciona un inquilino válido, se sale del método.

        // Se solicita el monto del pago.
        System.out.print("Ingrese monto: ");
        double monto = scanner.nextDouble();
        scanner.nextLine(); // Limpia el buffer después de leer el número.

        // Se toma la fecha de hoy como fecha de pago, en formato dd/MM/yyyy.
        String fechaPago = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Se obtiene la cochera asociada al inquilino, si existe.
        Cochera cochera = admin.getCocheraPorInquilino(inquilino);
        if (cochera != null) {
            // Se registra el pago mediante el objeto Administrador.
            admin.registrarPago(new Pago(monto, fechaPago, inquilino, cochera));
        } else {
            // Si el inquilino no tiene cochera asignada, se informa al usuario.
            System.out.println("Error: El inquilino no tiene una cochera asignada.");
        }
        esperarEntrada(scanner);
    }

    /**
     * Método para reservar un lugar común (ej: PARRILLA, SUM o PILETA).
     * @param scanner Objeto Scanner para leer la entrada desde la consola.
     * @param admin   Administrador que gestionará la reserva.
     */
    private static void reservarLugar(Scanner scanner, Administrador admin) {
        try {
            mostrarTitulo("Reservar Lugar");

            // 1. Solicitar la fecha en formato dd/MM/yyyy
            System.out.print("Ingrese fecha de reserva (dd/MM/yyyy): ");
            String fechaReserva = scanner.nextLine();

            LocalDate fecha;
            try {
                // 2. Crear un formatter para el formato dd/MM/yyyy
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fecha = LocalDate.parse(fechaReserva, formatter);

                // Validar que la fecha no sea anterior a hoy
                if (fecha.isBefore(LocalDate.now())) {
                    System.out.println("Error: La fecha de reserva no puede ser anterior a la fecha actual.");
                    return;
                }
            } catch (DateTimeParseException e) {
                // Capturar errores de formato
                System.out.println("Error: Formato de fecha inválido. Use el formato dd/MM/yyyy.");
                return;
            }

            // Seleccionar inquilino
            Inquilino inquilino = seleccionarInquilino(scanner, admin.listarInquilinos());
            if (inquilino == null) return;

            // Validar el lugar
            System.out.print("Ingrese lugar (PARRILLA, SUM, PILETA): ");
            String lugarIngresado = scanner.nextLine().toUpperCase();
            Lugar lugar;
            try {
                lugar = Lugar.valueOf(lugarIngresado);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Lugar inválido. Ingrese PARRILLA, SUM o PILETA.");
                return;
            }

            // Registrar la reserva
            admin.reservarLugar(new Reserva(fechaReserva, inquilino, lugar));
            System.out.println("Reserva realizada con éxito.");

        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }

        esperarEntrada(scanner);
    }

    /**
     * Método para seleccionar un inquilino de una lista y retornarlo.
     * @param scanner     Objeto Scanner para leer la entrada desde la consola.
     * @param inquilinos  Lista de inquilinos disponibles.
     * @return            El inquilino seleccionado o null si la selección es inválida.
     */
    private static Inquilino seleccionarInquilino(Scanner scanner, List<Inquilino> inquilinos) {
        System.out.println("Seleccione el inquilino:");

        // Se muestra la lista de inquilinos, numerados.
        for (int i = 0; i < inquilinos.size(); i++) {
            System.out.println((i + 1) + ". " + inquilinos.get(i).getNombre() + " " + inquilinos.get(i).getApellido());
        }

        // Se lee la opción ingresada por el usuario (restando 1 para acceder al índice correcto).
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Limpia el buffer

        // Se valida que el índice ingresado sea correcto.
        if (index < 0 || index >= inquilinos.size()) {
            System.out.println("Selección inválida.");
            return null;
        }

        // Si la selección es válida, se obtiene el inquilino correspondiente.
        Inquilino inquilino = inquilinos.get(index);
        System.out.println("Inquilino seleccionado: " + inquilino.getNombre() + " " + inquilino.getApellido());
        return inquilino;
    }

    /**
     * Método que muestra el menú principal con las opciones disponibles.
     */
    private static void mostrarMenu() {
        System.out.println("\n==== Menú Principal ====");
        System.out.println("1. Registrar Pago");
        System.out.println("2. Reservar Lugar");
        System.out.println("3. Cancelar Reserva");
        System.out.println("4. Listar Cocheras");
        System.out.println("5. Listar Pagos");
        System.out.println("6. Listar Reservas");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Método auxiliar para mostrar un título con un formato específico.
     * @param titulo Cadena de texto que se mostrará como título.
     */
    private static void mostrarTitulo(String titulo) {
        System.out.println("\n==== " + titulo + " ====");
    }

    /**
     * Método para pausar la ejecución hasta que el usuario presione ENTER.
     * Útil para que el usuario pueda leer los mensajes en pantalla.
     * @param scanner Objeto Scanner para leer la entrada desde la consola.
     */
    private static void esperarEntrada(Scanner scanner) {
        System.out.println("\nPresione ENTER para volver al menú...");
        scanner.nextLine();
    }
}