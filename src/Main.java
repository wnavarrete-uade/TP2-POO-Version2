import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Administrador admin = new Administrador("Juan", "Perez", "12345678");

        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpia el buffer del scanner

            switch (opcion) {
                case 1:
                    registrarPago(scanner, admin);
                    break;
                case 2:
                    reservarLugar(scanner, admin);
                    break;
                case 3:
                    mostrarTitulo("Cancelar Reserva");
                    System.out.print("Ingrese número de reserva a cancelar: ");
                    int nroReserva = scanner.nextInt();
                    admin.cancelarReserva(nroReserva);
                    esperarEntrada(scanner);
                    break;
                case 4:
                    mostrarTitulo("Listar Cocheras");
                    admin.listarCocheras();
                    esperarEntrada(scanner);
                    break;
                case 5:
                    mostrarTitulo("Listar Pagos");
                    admin.listarPagos();
                    esperarEntrada(scanner);
                    break;
                case 6:
                    mostrarTitulo("Listar Reservas");
                    admin.listarReservas();
                    esperarEntrada(scanner);
                    break;
                case 7:
                    System.out.println("Saliendo...");
                    scanner.close(); // Cierra el scanner antes de salir
                    return;
                default:
                    System.out.println("\n\u001B[31mOpción no válida.\u001B[0m\n");
                    esperarEntrada(scanner);
            }
        }
    }

    // Método para registrar un pago de un inquilino
    private static void registrarPago(Scanner scanner, Administrador admin) {
        mostrarTitulo("Registrar Pago");
        Inquilino inquilino = seleccionarInquilino(scanner, admin.listarInquilinos());
        if (inquilino == null) return;

        System.out.print("Ingrese monto: ");
        double monto = scanner.nextDouble();
        scanner.nextLine(); // Limpia el buffer después de leer un número

        String fechaPago = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Cochera cochera = admin.getCocheraPorInquilino(inquilino);
        if (cochera != null) {
            admin.registrarPago(new Pago(monto, fechaPago, inquilino, cochera));
        } else {
            System.out.println("Error: El inquilino no tiene una cochera asignada.");
        }
        esperarEntrada(scanner);
    }

    // Método para reservar un lugar común (ejemplo: parrilla, SUM, pileta)
    private static void reservarLugar(Scanner scanner, Administrador admin) {
        try {
            mostrarTitulo("Reservar Lugar");
            System.out.print("Ingrese fecha de reserva (yyyy/MM/dd): ");
            String fechaReserva = scanner.nextLine();

            LocalDate fecha;
            try {
                fecha = LocalDate.parse(fechaReserva);
                if (fecha.isBefore(LocalDate.now())) {
                    System.out.println("Error: La fecha de reserva no puede ser anterior a la fecha actual.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error: Formato de fecha inválido. Use el formato yyyy/MM/dd.");
                return;
            }

            Inquilino inquilino = seleccionarInquilino(scanner, admin.listarInquilinos());
            if (inquilino == null) return;

            System.out.print("Ingrese lugar (PARRILLA, SUM, PILETA): ");
            Lugar lugar;
            try {
                lugar = Lugar.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Lugar inválido. Ingrese PARRILLA, SUM o PILETA.");
                return;
            }

            admin.reservarLugar(new Reserva(fechaReserva, inquilino, lugar));

        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
        esperarEntrada(scanner);
    }

    // Método para seleccionar un inquilino de la lista
    private static Inquilino seleccionarInquilino(Scanner scanner, List<Inquilino> inquilinos) {
        System.out.println("Seleccione el inquilino:");
        for (int i = 0; i < inquilinos.size(); i++) {
            System.out.println((i + 1) + ". " + inquilinos.get(i).getNombre() + " " + inquilinos.get(i).getApellido());
        }
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Limpia el buffer
        if (index < 0 || index >= inquilinos.size()) {
            System.out.println("Selección inválida.");
            return null;
        }
        Inquilino inquilino = inquilinos.get(index);
        System.out.println("Inquilino seleccionado: " + inquilino.getNombre() + " " + inquilino.getApellido());
        return inquilino;
    }

    // Método que muestra el menú principal
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

    // Método auxiliar para mostrar un título con formato
    private static void mostrarTitulo(String titulo) {
        System.out.println("\n==== " + titulo + " ====");
    }

    // Método para pausar la ejecución hasta que el usuario presione ENTER
    private static void esperarEntrada(Scanner scanner) {
        System.out.println("\nPresione ENTER para volver al menú...");
        scanner.nextLine();
    }
}

