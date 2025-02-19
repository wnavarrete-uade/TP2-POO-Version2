class Pago {
    private double monto;
    private String fecha;
    private Inquilino inquilino;
    private Cochera cochera;

    public Pago(double monto, String fecha, Inquilino inquilino, Cochera cochera) {
        this.monto = monto;
        this.fecha = fecha;
        this.inquilino = inquilino;
        this.cochera = cochera;
    }

    public Cochera getCochera() {
        return cochera;
    }

    @Override
    public String toString() {
        return "Pago por : $" + monto + " | Fecha: " + fecha + " | Inquilino: " + inquilino.getNombre() + " " + inquilino.getApellido();
    }
}