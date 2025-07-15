import java.util.List;
import java.util.Scanner;

public class ConversorApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        CodigosMonedaResponse respuesta = null;
        String apiKey;
        String ingresoTeclado;
        boolean conversorFlag = true;
        String monedaBase = "";
        String monedaObjetivo = "";
        double cantidadAConvertir = (double) 0;

        System.out.println("\n**************************************************************");
        System.out.println("SISTEMA DE CONVERSIÓN DE MONEDA");
        System.out.println("Escribe 'salir' en cualquier momento para terminar el programa");
        System.out.println("**************************************************************\n");

        while(true){
            System.out.print("Ingresa tu apiKey: ");
            apiKey = input.nextLine();

            if (apiKey.equalsIgnoreCase("salir")) {
                respuesta = null;
                break;
            }

            respuesta = ConsultaTazaDeCambio.obtenerCodigosMoneda(apiKey);

            if (!"success".equals(respuesta.getResult())) {
                System.out.println("La API key ingresada no es válida");
            } else {
                break;
            }
        }

        if(respuesta != null){
            System.out.println(respuesta.toString());

        }

        while(respuesta != null && conversorFlag) {
            while (monedaBase.isEmpty() && conversorFlag) {
                System.out.print("Ingresa tu moneda base: ");
                ingresoTeclado = input.nextLine().toUpperCase();

                if (ingresoTeclado.equalsIgnoreCase("salir")) {
                    conversorFlag = false;
                    break;
                }

                if (ingresoTeclado.length() != 3) {
                    System.out.println("El código debe tener exactamente 3 letras");
                    continue;
                }

                boolean existe = false;
                for (List<String> codigo : respuesta.getSupported_codes()) {
                    if (codigo.getFirst().equalsIgnoreCase(ingresoTeclado)) {
                        existe = true;
                        break;
                    }
                }

                if (existe) {
                    monedaBase = ingresoTeclado;
                    break;
                } else {
                    System.out.println("Moneda no reconocida. Intenta nuevamente.");
                }
            }

            while (monedaObjetivo.isEmpty() && conversorFlag) {
                System.out.print("Ingresa tu moneda objetivo: ");
                ingresoTeclado = input.nextLine().toUpperCase();

                if (ingresoTeclado.equalsIgnoreCase("salir")) {
                    conversorFlag = false;
                    break;
                }

                if (ingresoTeclado.length() != 3) {
                    System.out.println("El código debe tener exactamente 3 letras");
                    continue;
                }

                boolean existe = false;
                for (List<String> codigo : respuesta.getSupported_codes()) {
                    if (codigo.getFirst().equalsIgnoreCase(ingresoTeclado)) {
                        existe = true;
                        break;
                    }
                }

                if (existe) {
                    monedaObjetivo = ingresoTeclado;
                    break;
                } else {
                    System.out.println("Moneda no reconocida. Intenta nuevamente.");
                }
            }

            while (cantidadAConvertir == 0 && conversorFlag) {
                System.out.print("Ingresa la cantidad a convertir: ");
                ingresoTeclado = input.nextLine();

                if (ingresoTeclado.equalsIgnoreCase("salir")) {
                    conversorFlag = false;
                    break;
                }

                try {
                    cantidadAConvertir = Double.parseDouble(ingresoTeclado);
                    if (cantidadAConvertir <= 0) {
                        System.out.println("La cantidad debe ser mayor que cero.");
                        cantidadAConvertir = (double) 0;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Ingresa un número o escribe 'salir'.");
                }
            }

            if (conversorFlag) {
                double tasa = ConsultaTazaDeCambio.obtenerConversionRate(apiKey, monedaBase, monedaObjetivo);
                double resultadoFinal = cantidadAConvertir * tasa;

                System.out.println("\n**************************************************************");
                System.out.printf("%.2f %s equivalen a %.2f %s%n", cantidadAConvertir, monedaBase, resultadoFinal, monedaObjetivo);
                System.out.println("**************************************************************\n");

                System.out.print("¿Querés hacer otra conversión? (si/no)\n");
                ingresoTeclado = input.nextLine().toUpperCase();

                if (ingresoTeclado.equalsIgnoreCase("salir") || ingresoTeclado.equalsIgnoreCase("no")) {
                    break;
                } else {
                    monedaBase = "";
                    monedaObjetivo = "";
                    cantidadAConvertir = (double) 0;
                }
            }


        }

        input.close();
        System.out.println("\n**************************************************************");
        System.out.println("SISTEMA DE CONVERSIÓN DE MONEDA");
        System.out.println("Gracias por usar nuestro sistema de conversion de moneda");
        System.out.println("**************************************************************\n");
    }
}
