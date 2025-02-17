class Cochera {
    private int numero;
    private String categoria;
    private boolean estadoPago;
    private Inquilino inquilino;

    public Cochera(int numero, String categoria, boolean estadoPago, Inquilino inquilino) {
        this.numero = numero;
        this.categoria = categoria;
        this.estadoPago = estadoPago;
        this.inquilino = inquilino;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    @Override
    public String toString() {
        return "Cochera N°" + numero + " | Categoría: " + categoria + " | Estado de pago: " + (estadoPago ? "Pagado" : "Pendiente");
    }


}
