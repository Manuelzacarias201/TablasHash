import models.Business;
import models.HashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashTable hashTable1 = new HashTable(1000);
        HashTable hashTable2 = new HashTable(1000);
        Scanner scanner = new Scanner(System.in);
        String line = "";
        String splitBy = ",";
        boolean useHashFunction1 = true;

        long totalInsertionTimeHash1 = 0;
        long totalInsertionTimeHash2 = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader("bussines.csv"));
            while ((line = br.readLine()) != null) {
                String[] businessData = line.split(splitBy);
                Business business = new Business(businessData[0], businessData[1], businessData[2], businessData[3], businessData[4]);

                long startTime = System.nanoTime();
                hashTable1.put(business.getId(), business, true);
                long endTime = System.nanoTime();
                totalInsertionTimeHash1 += (endTime - startTime);

                startTime = System.nanoTime();
                hashTable2.put(business.getId(), business, false);
                endTime = System.nanoTime();
                totalInsertionTimeHash2 += (endTime - startTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Tiempo funcion hash 1: " + totalInsertionTimeHash1 + " ns");
        System.out.println("Tiempo funcion hash 2: " + totalInsertionTimeHash2 + " ns");

        int choice;
        do {
            System.out.println("------------------------------");
            System.out.println("1. Inserte un nuevo Local");
            System.out.println("2. Ver todos los Locales");
            System.out.println("3. Buscar un local por ID");
            System.out.println("4. Salir");
            System.out.println("------------------------------");
            System.out.print("Elige una opción: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String name = scanner.nextLine();
                    System.out.print("Dirección: ");
                    String address = scanner.nextLine();
                    System.out.print("Ciudad: ");
                    String city = scanner.nextLine();
                    System.out.print("Estado: ");
                    String state = scanner.nextLine();
                    Business business = new Business(id, name, address, city, state);

                    long startTime = System.nanoTime();
                    hashTable1.put(id, business, true);
                    long endTime = System.nanoTime();
                    System.out.println("Tiempo funcion hash 1: " + (endTime - startTime) + " ns");

                    startTime = System.nanoTime();
                    hashTable2.put(id, business, false);
                    endTime = System.nanoTime();
                    System.out.println("Tiempo funcion hash 2: " + (endTime - startTime) + " ns");
                    break;
                case 2:
                    System.out.println("HashTable con función hash 1:");
                    hashTable1.displayAll();
                    System.out.println("HashTable con función hash 2:");
                    hashTable2.displayAll();
                    break;
                case 3:
                    System.out.print("ID: ");
                    String searchId = scanner.nextLine();

                    long startTimeGet = System.nanoTime();
                    Business found1 = hashTable1.get(searchId, true);
                    long endTimeGet = System.nanoTime();
                    System.out.println("Tiempo funcion hash 1: " + (endTimeGet - startTimeGet) + " ns");

                    if (found1 != null) {
                        System.out.println("Funcion hash 1: " + found1);
                    } else {
                        System.out.println("Funcion hash 1: Negocio no encontrado");
                    }

                    startTimeGet = System.nanoTime();
                    Business found2 = hashTable2.get(searchId, false);
                    endTimeGet = System.nanoTime();
                    System.out.println("Tiempo funcion hash 2: " + (endTimeGet - startTimeGet) + " ns");

                    if (found2 != null) {
                        System.out.println("Funcion hash 2: " + found2);
                    } else {
                        System.out.println("Funcion hash 2: Negocio no encontrado");
                    }
                    break;
                case 4:
                    System.out.println("Nos vemos luego....");
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo");
                    break;
            }
        } while (choice != 4);

        scanner.close();
    }
}