package clases;

import java.util.ArrayList;

/**
 * Clase que representa un préstamo de libros realizado por un usuario.
 *
 * <p>Un préstamo incluye el nombre del usuario, una lista de libros
 * y un estatus que indica si el préstamo está activo o no.</p>
 *
 * <p>Permite agregar libros, mostrar detalles del préstamo y calcular
 * el costo total de reemplazo de los libros prestados.</p>
 *
 * @author Ariel
 */
public class Prestamo {
    // Atributos privados
    private String nombre;
    private ArrayList<Libro> libros;
    private boolean estatus;

    /**
     * Constructor sin argumentos.
     *
     * <p>Inicializa un préstamo vacío con el nombre Invitado,
     * con la lista de libros vacía y el estatus en true.</p>
     */
    public Prestamo() {
        this.nombre = "Invitado";
        this.libros = new ArrayList<>();
        this.estatus = true;
    }

    /**
     * Constructor sobrecargado.
     *
     * @param nombre  Nombre del usuario que realiza el préstamo.
     * @param estatus Estatus inicial del préstamo (true/false).
     */
    public Prestamo(String nombre, boolean estatus) {
        this.nombre = nombre;
        this.libros = new ArrayList<>();
        this.estatus = estatus;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }
    public ArrayList<Libro> getLibros() {
        return libros;
    }
    public boolean getEstatus() {
        return estatus;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setLibros(ArrayList<Libro> libros) {
        this.libros = libros;
    }
    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    // Agregar libro
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    /**
     * Devuelve un mensaje indicando el estatus del préstamo.
     *
     * @return "El préstamo está activo." o "El préstamo está inactivo."
     */
    public String mostrarEstatus() {
        return estatus ? "El préstamo está activo." : "El préstamo está inactivo.";
    }

    /**
     * Muestra en consola los detalles del préstamo,
     * incluyendo usuario, estatus, libros y costo total de reemplazo.
     */
    public void mostrarDetallesDelPrestamo() {
        System.out.println("Préstamo de: " + nombre);
        System.out.println("Estatus: " + (estatus ? "Activo" : "Inactivo"));
        System.out.println("Libros prestados:");
        for (Libro libro : libros) {
            System.out.println("   - " + libro.toString());
        }
        System.out.println("Total de reemplazo: $" + calcularTotalDeReemplazo());
        System.out.println("-----------------------------------");
    }

    /**
     * Calcula el total del costo de reemplazo de los libros prestados.
     *
     * @return Suma de los precios de reemplazo de todos los libros.
     */
    public double calcularTotalDeReemplazo() {
        double total = 0;
        for (Libro libro : libros) {
            total += libro.getPrecioDeReemplazo();
        }
        return total;
    }
}
