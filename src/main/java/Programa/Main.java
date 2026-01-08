package Programa;

import Consola.*;
import java.util.Scanner;

public class Main {
    
    public static void main (String[] args){
        
        int opcionUsuario;
        boolean bandera = true;

        ConsolaUI instanciaPrograma = new ConsolaUI();
        
        Scanner entrada = new Scanner(System.in);

        //Cabecera del programa
        System.out.println("------------------------------------------------");
        System.out.println("|              MAPAS DE KARNAUGH               |");
        System.out.println("------------------------------------------------");
        System.out.println("Bienvenido al Simplificador de Mapas de Karnaugh");
        
        //Bucle de ejecución
        while (bandera){
        System.out.println("------------------------------------------------");
        System.out.println("1. Simplificar una expresion.");
        System.out.println("2. Salir del sistema.");
        System.out.print("Seleccione una opcion: ");
        opcionUsuario = entrada.nextInt();
        
            switch (opcionUsuario){
                case 1:
                    instanciaPrograma.iniciar();
                    break;
                case 2:
                    System.out.println("CERRANDO...");
                    bandera = false;
                    break;
                default:
                    System.out.println("Opcion invalida, intente nuevamente.");
            }
        
        }
        
        entrada.close();
        System.out.println("Gracias por preferirnos.");
          
    }
        
}
