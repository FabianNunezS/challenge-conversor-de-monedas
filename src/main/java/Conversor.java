import com.google.gson.JsonObject;

import java.util.Scanner;

public class Conversor {

    public static void iniciar() {

        Scanner scanner = new Scanner(System.in);
        JsonObject tasas = ApiExchange.obtenerTasas();
        if (tasas == null) {
            System.out.println("Error: No se pudieron obtener las tasas de cambio.");
            System.out.println("Verifica tu API Key o la conexión con la API.");
            return;
        }

        int opcion = 0;

        while (opcion != 7) {
            exibirMenu();
            opcion = scanner.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                System.out.print("Ingrese el monto a convertir: ");
                double monto = scanner.nextDouble();

                String monedaOrigen = obtenerMonedaOrigen(opcion);
                String monedaDestino = obtenerMonedaDestino(opcion);

                double resultado = convertir(monto, tasas, monedaOrigen, monedaDestino);

                System.out.println("Resultado: " + resultado + " " + monedaDestino);
                System.out.println();
            } else if (opcion != 7) {
                System.out.println("Opción inválida\n");
            }
        }

        System.out.println("Gracias por usar el Conversor de Monedas");
    }

    // Menú
    public static void exibirMenu() {
        System.out.println("""
        **********************************************
        Sea bienvenido/a al Conversor de Moneda =)

        1) Dólar ==> Peso argentino
        2) Peso argentino ==> Dólar
        3) Dólar ==> Real brasileño
        4) Real brasileño ==> Dólar
        5) Dólar ==> Peso chileno
        6) Peso chileno ==> Dólar
        7) Salir

        Elija una opción válida:
        **********************************************
        """);
    }

    // Conversión general (ida y vuelta)
    private static double convertir(double monto,
                                    JsonObject tasas,
                                    String monedaOrigen,
                                    String monedaDestino) {

        double tasaOrigen = tasas.get(monedaOrigen).getAsDouble();
        double tasaDestino = tasas.get(monedaDestino).getAsDouble();

        return monto / tasaOrigen * tasaDestino;
    }

    // Moneda origen
    private static String obtenerMonedaOrigen(int opcion) {
        return switch (opcion) {
            case 1, 3, 5 -> "USD";
            case 2 -> "ARS";
            case 4 -> "BRL";
            case 6 -> "CLP";
            default -> "";
        };
    }

    // Moneda destino
    private static String obtenerMonedaDestino(int opcion) {
        return switch (opcion) {
            case 1 -> "ARS";
            case 2 -> "USD";
            case 3 -> "BRL";
            case 4 -> "USD";
            case 5 -> "CLP";
            case 6 -> "USD";
            default -> "";
        };
    }
}

