/**
 * Clase que representa una Cochera dentro de un edificio o complejo habitacional,
 * asociada a un inquilino y con información acerca de su pago.
 */
class Cochera {

    // Número de la cochera (identificador único).
    private int numero;

    // Categoría de la cochera (por ejemplo, puede indicar el tamaño o tipo).
    private String categoria;

    // Indica si el pago de la cochera está al día (true) o pendiente (false).
    private boolean estadoPago;

    // Inquilino al que se le ha asignado esta cochera.
    private Inquilino inquilino;

    /**
     * Constructor de la clase Cochera.
     *
     * @param numero      Número de la cochera (ID único).
     * @param categoria   Categoría de la cochera (puede diferenciar tipo/tamaño).
     * @param estadoPago  Estado de pago: true si está pagado, false en caso contrario.
     * @param inquilino   Inquilino al que pertenece la cochera.
     */
    public Cochera(int numero, String categoria, boolean estadoPago, Inquilino inquilino) {
        this.numero = numero;
        this.categoria = categoria;
        this.estadoPago = estadoPago;
        this.inquilino = inquilino;
    }

    /**
     * Obtiene el inquilino asignado a la cochera.
     *
     * @return Objeto Inquilino que ocupa la cochera.
     */
    public Inquilino getInquilino() {
        return inquilino;
    }

    /**
     * Obtiene el número identificador de la cochera.
     *
     * @return Número de la cochera.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Verifica si la cochera está pagada.
     *
     * @return true si el estado de pago está al día, false si está pendiente.
     */
    public boolean isEstadoPago() {
        return estadoPago;
    }

    /**
     * Devuelve la categoría de la cochera.
     *
     * @return Cadena que representa la categoría de la cochera.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Sobrescribe el método toString para mostrar la información básica de la cochera:
     * número, categoría y estado de pago.
     *
     * @return Cadena con la representación de la cochera.
     */
    @Override
    public String toString() {
        return "Cochera N°" + numero
                + " | Categoría: " + categoria
                + " | Estado de pago: " + (estadoPago ? "Pagado" : "Pendiente");
    }
}