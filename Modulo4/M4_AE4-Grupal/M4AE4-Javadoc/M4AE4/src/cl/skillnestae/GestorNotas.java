package cl.skillnestae;
import java.util.Scanner;

/**
 * Aplicación de consola "GestorNotas".
 * Solicita nombre, 3 calificaciones, calcula el promedio
 * y muestra si el estudiante aprueba o reprueba.
 *  * Clase GestorNotas.
 * <p>
 * Aplicación de consola que solicita el nombre de un estudiante
 * y tres calificaciones, luego calcula el promedio y determina 
 * si el estudiante aprueba o reprueba.
 * </p>
 * 
 * <b>Flujo del programa:</b>
 * <ol>
 *   <li>Solicita nombre del estudiante</li>
 *   <li>Solicita tres notas (tipo double)</li>
 *   <li>Calcula el promedio con {@link #calcularPromedio(double, double, double)}</li>
 *   <li>Determina el estado con {@link #estadoAlumno(double)}</li>
 *   <li>Muestra los resultados en consola</li>
 * </ol>
 *  */

public class GestorNotas {
	public static final double MIN_APROBACION = 60.0;
	
public static void main(String []args) {
	Scanner sc = new Scanner(System.in);
	
	System.out.println("=== Bienvenido al Gestor de notas ===\\n");
	System.out.println("Ingrese el nombre de la/el estudiante: ");
	
	String nombre = sc.nextLine();
	
	System.out.println("Ingrese la primera nota: ");
	double n1 = sc.nextDouble();
	
	System.out.println("Ingrese la segunda nota: ");
	double n2 = sc.nextDouble();
	
	System.out.println("Ingrese la tercera nota: ");
	double n3 = sc.nextDouble();
	
	double promedio = calcularPromedio (n1, n2, n3);
	String estado = estadoAlumno(promedio);
	
	System.out.println("\n---------- A CONTINUACION LOS RESULTADOS -------------- ");
	System.out.printf("Estudiante: %s	Promedio: %.2f	Estado: %s%n",
			nombre, promedio, estado);
	
	sc.close();
	}

public static double calcularPromedio(double n1, double n2, double n3) {
	return (n1+n2+n3) / 3.0;
}
public static String estadoAlumno(double promedio) {
	return (promedio >= MIN_APROBACION ? "Aprobado" : "Reprobado");
	
}
}
