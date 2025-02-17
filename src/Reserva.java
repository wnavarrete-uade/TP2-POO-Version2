class Reserva {
    private static int contador = 1;
    private int nroReserva;
    private String fecha;
    private Inquilino inquilino;
    private Lugar lugar;

    public Reserva(String fecha, Inquilino inquilino, Lugar lugar) {
        this.nroReserva = contador++;
        this.fecha = fecha;
        this.inquilino = inquilino;
        this.lugar = lugar;
    }

    public int getNroReserva() {
        return nroReserva;
    }

    @Override
    public String toString() {
        return "Reserva N°" + nroReserva + " | Fecha: " + fecha + " | Lugar: " + lugar + " | Inquilino: " + inquilino.nombre;
    }
}