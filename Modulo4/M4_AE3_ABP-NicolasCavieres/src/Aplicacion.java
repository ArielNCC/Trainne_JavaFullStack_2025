//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import clases.AdivinaNumero;

public class Aplicacion {
    public static void main(String[] args) {
        int numeroAleatorio = (int)(Math.random() * 100) + 1;
        AdivinaNumero.iniciarJuego(numeroAleatorio);
    }
}