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
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    mostrarTitulo("Registrar Pago");
                    System.out.println("Seleccione el inquilino:");
                    List<Inquilino> inquilinos = admin.getInquilinos();
                    for (int i = 0; i < inquilinos.size(); i++) {
                        System.out.println((i + 1) + ". " + inquilinos.get(i).getNombre() + " " + inquilinos.get(i).getApellido());
                    }
                    int inquilinoIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    Inquilino inquilino = inquilinos.get(inquilinoIndex);

                    System.out.print("Ingrese monto: ");
                    double monto = scanner.nextDouble();
                    scanner.nextLine();

                    String fechaPago = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    Cochera cochera = admin.getCocheraPorInquilino(inquilino);
                    if (cochera != null) {
                        admin.registrarPago(new Pago(monto, fechaPago, inquilino, cochera));
                        System.out.println("Pago registrado con éxito.");
                    } else {
                        System.out.println("Error: El inquilino no tiene una cochera asignada.");
                    }
                    esperarEntrada(scanner);
                    break;
                case 2:
                    mostrarTitulo("Reservar Lugar");
                    System.out.print("Ingrese fecha de reserva: ");
                    String fechaReserva = scanner.nextLine();
                    System.out.print("Ingrese lugar (PARRILLA, SUM, PILETA): ");
                    Lugar lugar = Lugar.valueOf(scanner.nextLine().toUpperCase());
                    Inquilino inquilinoReserva = new Inquilino("Carlos", "Lopez", "87654321");
                    admin.reservarLugar(new Reserva(fechaReserva, inquilinoReserva, lugar));
                    esperarEntrada(scanner);
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
                    scanner.close();
                    return;
                default:
                    System.out.println("\n\u001B[31mOpción no válida.\u001B[0m\n");
                    esperarEntrada(scanner);
            }
        }
    }

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

    private static void mostrarTitulo(String titulo) {
        System.out.println("\n==== " + titulo + " ====");
    }

    private static void esperarEntrada(Scanner scanner) {
        System.out.println("\nPresione ENTER para volver al menú...");
        scanner.nextLine();
    }
}