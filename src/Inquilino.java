/**
 * Clase que representa a un Inquilino en el sistema,
 * heredando características básicas de la clase Persona.
 */
public class Inquilino extends Persona {

    /**
     * Constructor que inicializa al inquilino con sus datos personales.
     *
     * @param nombre   Nombre del inquilino.
     * @param apellido Apellido del inquilino.
     * @param dni      DNI (Documento Nacional de Identidad) del inquilino.
     */
    public Inquilino(String nombre, String apellido, String dni) {
        // Asigna los valores proporcionados a los campos de la clase Persona.
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
}