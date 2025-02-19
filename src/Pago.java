/**
 * Clase que representa un Pago realizado por un inquilino, asociado a una cochera.
 * Contiene información sobre el monto, la fecha, el inquilino y la cochera pagada.
 */
class Pago {

    // Monto total del pago.
    private double monto;

    // Fecha en la que se realiza el pago (generalmente en formato dd/MM/yyyy).
    private String fecha;

    // Inquilino que realiza el pago.
    private Inquilino inquilino;

    // Cochera que está siendo pagada o asociada a este pago.
    private Cochera cochera;

    /**
     * Constructor de la clase Pago.
     *
     * @param monto     Cantidad de dinero abonada en el pago.
     * @param fecha     Fecha en la que se realizó el pago.
     * @param inquilino Inquilino que efectuó el pago.
     * @param cochera   Cochera por la que se está pagando.
     */
    public Pago(double monto, String fecha, Inquilino inquilino, Cochera cochera) {
        this.monto = monto;
        this.fecha = fecha;
        this.inquilino = inquilino;
        this.cochera = cochera;
    }

    /**
     * Devuelve la cochera asociada a este pago.
     *
     * @return La cochera pagada.
     */
    public Cochera getCochera() {
        return cochera;
    }

    /**
     * Retorna una representación en cadena del pago, incluyendo
     * el monto, la fecha y los datos básicos del inquilino.
     *
     * @return Cadena con la información del pago.
     */
    @Override
    public String toString() {
        return "Pago por : $" + monto
                + " | Fecha: " + fecha
                + " | Inquilino: " + inquilino.getNombre() + " " + inquilino.getApellido();
    }
}