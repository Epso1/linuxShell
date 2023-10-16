package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;



/**
 * Esta clase representa un comando que se ejecutará en el shell.
 *
 * @author Cesar Alvaro
 * @version 1.0
 */
class Command {
    private String[] args;
    private String outputRedirect;

    /**
     * Constructor para crear un objeto Command a partir de una cadena de entrada.
     *
     * @param args La cadena que representa el comando, con o sin redirección de salida.
     */
    public Command(String args) {
        if (args.contains(">")) {
            String[] parts = args.split(">");
            String arguments = parts[0].trim();
            outputRedirect = parts[1].trim();
            this.args = arguments.split("\\s+");
        } else {
            this.args = args.split("\\s+");
            outputRedirect = "";
        }
    }

    /**
     * Constructor para crear un objeto Command a partir de un array de argumentos y una cadena de redirección.
     *
     * @param args           Array de argumentos del comando.
     * @param outputRedirect Cadena que representa el nombre del archivo de redirección de salida estándar.
     */
    public Command(String[] args, String outputRedirect) {
        this.args = Arrays.toString(args).split("\\s+");
        this.outputRedirect = outputRedirect;
    }

    /**
     * Ejecuta el comando y devuelve su salida estándar.
     *
     * @return La salida estándar del comando.
     */
    public String execute() {
        String result = "";
        String line = "";
        ProcessBuilder processBuilder = new ProcessBuilder(args);

        try {
            Process process = processBuilder.start();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            System.out.println("Process output:");
            while ((line = br.readLine()) != null)
            {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            String output = readProcessOutput(process);
            result = "Comando ejecutado con éxito\n" +
                    "Comando: " + String.join(" ", args) + "\n" +
                    "Número de parámetros: " + args.length + "\n" +
                    "Parámetros: " + String.join(", ", args) + "\n" +
                    "PID: " + process.pid() + "\n" +
                    "Salida:\n" + output + "\n" +
                    "Código de salida: " + exitCode;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lee la salida del proceso y la devuelve como una cadena.
     *
     * @param process El proceso cuya salida se va a leer.
     * @return La salida del proceso como una cadena.
     *
     */
    private String readProcessOutput(Process process) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return output.toString();
    }

    /**
     * Sobreescribe el método toString()
     * @return La información del proceso: el comando a ejecutar, el número de parámetros y los parámetros concretos.
     */
    @Override
    public String toString() {
        return "Información del comando:\n" +
                "Comando: " + String.join(" ", args) + "\n" +
                "Número de parámetros: " + args.length + "\n" +
                "Parámetros: " + String.join(", ", args) + "\n";
    }
}