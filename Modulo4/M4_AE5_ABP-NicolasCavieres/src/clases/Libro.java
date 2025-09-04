package clases;

/**
 * Clase que representa un libro en el sistema de préstamos.
 *
 * <p>Contiene información básica como el título, autor y el precio
 * de reemplazo en caso de pérdida o daño.</p>
 *
 * @author Ariel
 */
public class Libro {
    // Atributos
    String titulo;
    String autor;
    Double precioDeReemplazo;

    /**
     * Constructor para el objeto Libro
     *
     */
    public Libro(String titulo, String autor, double precioDeReemplazo) {
        this.titulo = titulo;
        this.autor = autor;
        this.precioDeReemplazo = precioDeReemplazo;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }
    public String getAutor() {
        return autor;
    }
    public Double getPrecioDeReemplazo() {
        return precioDeReemplazo;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public void setPrecioDeReemplazo(Double precioDeReemplazo) {
        this.precioDeReemplazo = precioDeReemplazo;
    }

    /**
     * Devuelve una representación en texto del libro.
     *
     * @return Cadena con título, autor y precio de reemplazo.
     */
    @Override
    public String toString() {
        return titulo + ", " + autor + " ($" + precioDeReemplazo + ")";
    }
}
