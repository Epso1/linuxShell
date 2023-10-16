package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Esta clase representa un Shell Linux simple en Java.
 * @author Cesar Alvaro
 * @version 1.0
 */

public class Shell {
    private static Command lastCommand = null;

    /**
     * Método principal que inicia el Shell y espera comandos del usuario.
     *
     * @param args Argumentos de línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        boolean salir = false;
        System.out.println("Bienvenido al Shell simple de Linux");
        while (!salir) {
            System.out.print("> ");
            String input = readInput();
            if (input.equals("exit")) {
                System.out.println("Saliendo del shell.");
                salir = true;
            } else if (input.equals("last-command")) {
                printLastCommandInfo();
            } else {
                runCommand(input);
            }
        }
    }

    /**
     * Lee la entrada del usuario desde la consola.
     *
     * @return La cadena de entrada del usuario.
     */
    private static String readInput() {
        String input = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    /**
     * Ejecuta un comando ingresado por el usuario.
     *
     * @param input La cadena que representa el comando a ejecutar.
     */
    private static void runCommand(String input) {
        Command command = new Command(input);
        String output = command.execute();
        System.out.println(output);
        lastCommand = command;
    }

    /**
     * Imprime información sobre el último comando ejecutado.
     */
    private static void printLastCommandInfo() {
        if (lastCommand != null) {
            System.out.println(lastCommand);
        } else {
            System.out.println("No se ha ejecutado ningún comando todavía.");
        }
    }
}
