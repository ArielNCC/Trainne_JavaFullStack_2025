import clases.Libro;
import clases.Prestamo;

/**
 * <h2>Clase principal.</h2>
 *
 * <p>En esta clase se crean distintos objetos de tipo {@link Prestamo} y {@link Libro},
 * se agregan libros a los préstamos, se muestran los detalles de cada uno y se
 * comprueba el estado (activo o inactivo) de los préstamos.</p>
 *
 * <p>Objetivos principales:</p>
 * <ul>
 *   <li>Crear préstamos con y sin parámetros.</li>
 *   <li>Agregar libros a cada préstamo.</li>
 *   <li>Mostrar la información completa de cada préstamo.</li>
 *   <li>Comprobar y mostrar el estatus de los préstamos.</li>
 * </ul>
 *
 * @author Ariel
 * @version 1.0
 */
public class Aplicacion {

    /**
     * Método principal de la aplicación.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este programa).
     */
    public static void main(String[] args) {
        // 2 préstamos con usuarios no especificados (constructor sin argumentos)
        Prestamo prestamo1 = new Prestamo();
        Prestamo prestamo2 = new Prestamo();

        // 3 préstamos personalizados con nombre y estatus
        Prestamo prestamo3 = new Prestamo("Carlos", true);
        Prestamo prestamo4 = new Prestamo("Ana", false);
        Prestamo prestamo5 = new Prestamo("Luis", true);

        // Agregar libros a los préstamos
        prestamo1.agregarLibro(new Libro("Cien Años de Soledad", "Gabriel García Márquez", 15000));
        prestamo1.agregarLibro(new Libro("El Principito", "Antoine de Saint-Exupéry", 10000));

        prestamo2.agregarLibro(new Libro("1984", "George Orwell", 12000));
        prestamo2.agregarLibro(new Libro("Fahrenheit 451", "Ray Bradbury", 13000));

        prestamo3.agregarLibro(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", 18000));
        prestamo3.agregarLibro(new Libro("La Odisea", "Homero", 16000));

        prestamo4.agregarLibro(new Libro("Orgullo y Prejuicio", "Jane Austen", 11000));
        prestamo4.agregarLibro(new Libro("Hamlet", "William Shakespeare", 14000));

        prestamo5.agregarLibro(new Libro("Crimen y Castigo", "Fiódor Dostoyevski", 20000));
        prestamo5.agregarLibro(new Libro("La Divina Comedia", "Dante Alighieri", 22000));

        // Mostrar detalles de cada préstamo
        prestamo1.mostrarDetallesDelPrestamo();
        prestamo2.mostrarDetallesDelPrestamo();
        prestamo3.mostrarDetallesDelPrestamo();
        prestamo4.mostrarDetallesDelPrestamo();
        prestamo5.mostrarDetallesDelPrestamo();

        // Comprobar la funcionalidad mostrarEstatus
        System.out.println(prestamo1.getNombre() + ": " + prestamo1.mostrarEstatus());
        System.out.println(prestamo2.getNombre() + ": " + prestamo2.mostrarEstatus());
        System.out.println(prestamo3.getNombre() + ": " + prestamo3.mostrarEstatus());
        System.out.println(prestamo4.getNombre() + ": " + prestamo4.mostrarEstatus());
        System.out.println(prestamo5.getNombre() + ": " + prestamo5.mostrarEstatus());
    }
}