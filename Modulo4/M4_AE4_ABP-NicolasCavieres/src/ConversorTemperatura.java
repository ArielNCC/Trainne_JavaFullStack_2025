//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

/**
 * La clase ConversorTemperatura permite convertir una temperatura
 * No se usan 2 variables (F y C) para ver como cambia el valor de temperatura
 * ingresada en grados Celsius a grados Fahrenheit.
 * Utiliza la fórmula: F = (C × 9/5) + 32
 *
 * @author Nicolás
 */
public class ConversorTemperatura {

    /**
     * Método principal que ejecuta la conversión de temperatura.
     * Solicita al usuario una entrada en grados Celsius, realiza la conversión
     * y muestra el resultado en consola.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la temperatura en grados Celsius: ");
        double temperatura = scanner.nextDouble();
        System.out.println(); //Salto de linea para leer el valor de la variable temperatura
        // Punto de interrupción sugerido aquí para depuración
        temperatura = (temperatura * 9 / 5) + 32;
        System.out.println(); //Salto de linea para leer el valor de la variable temperatura

        System.out.printf("La temperatura en Fahrenheit es: %.2f°F%n", temperatura);

        scanner.close(); //Cerramos scanner para ahorrar recursos
    }
}
