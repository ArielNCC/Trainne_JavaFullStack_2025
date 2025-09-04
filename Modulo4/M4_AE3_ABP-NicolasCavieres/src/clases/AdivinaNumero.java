package clases;

import java.util.ArrayList;
import java.util.Scanner;

public class AdivinaNumero {

    public static void iniciarJuego(int numeroAleatorio) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> intentos = new ArrayList<>();

        System.out.print("Por favor ingresa un número entre 1 - 100: ");

        while (true) {
            int numUsuario = scanner.nextInt();
            intentos.add(numUsuario);

            String resultado = compararNumeros(numUsuario, numeroAleatorio);
            System.out.println(resultado);

            if (resultado.contains("adivinar")) {
                System.out.println("Te tomó " + intentos.size() + " veces adivinar el número.");
                for (int intento : intentos) {
                    System.out.print(intento + " ");
                }
                System.out.println();  // salto de línea final
                break;
            }
        }
    }

    public static String compararNumeros(int numUsuario, int numeroAleatorio) {
        if (numUsuario < 1 || numUsuario > 100) {
            return "El número debe estar entre 1 y 100.";
        }

        if (numUsuario < numeroAleatorio) {
            return "Intenta con un número más grande.";
        } else if (numUsuario > numeroAleatorio) {
            return "Intenta con un número más pequeño.";
        } else {
            return "¡Has conseguido adivinar el número!";
        }
    }
}