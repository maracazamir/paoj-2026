package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;




public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește comanda din stdin: PRINT, SHALLOW <nume> sau DEEP <nume>
        // 3. Execută comanda:
        //    - PRINT → afișează toți studenții
        //    - SHALLOW <nume> → shallow clone + modifică orașul clonei la "MODIFICAT" + afișează
        //    - DEEP <nume> → deep clone + modifică orașul clonei la "MODIFICAT" + afișează


        List<Student> studenti = citesteStudenti();

        Scanner scanner = new Scanner(System.in);
        String comanda = scanner.nextLine();

        if (comanda.equals("PRINT")) {
            for (Student student : studenti) {
                System.out.println(student);
            }
        } else if (comanda.startsWith("SHALLOW")) {
            String nume = comanda.split(" ", 2)[1];
            Student student = cautaStudent(studenti, nume);

            if (student != null) {
                try {
                    Student clona = student.shallowClone();
                    clona.getAdresa().setOras("MODIFICAT");

                    System.out.println("Original: " + student);
                    System.out.println("Clona: " + clona);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        } else if (comanda.startsWith("DEEP")) {
            String nume = comanda.split(" ", 2)[1];
            Student student = cautaStudent(studenti, nume);

            if (student != null) {
                try {
                    Student clona = student.deepClone();
                    clona.getAdresa().setOras("MODIFICAT");

                    System.out.println("Original: " + student);
                    System.out.println("Clona: " + clona);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }

        scanner.close();
    }

    private static List<Student> citesteStudenti() {
        List<Student> studenti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linie;

            while ((linie = br.readLine()) != null) {
                if (linie.trim().isEmpty()) {
                    continue;
                }

                String[] parts = linie.split(",");

                if (parts.length < 4) {
                    continue;
                }

                String nume = parts[0].trim();
                int varsta = Integer.parseInt(parts[1].trim());
                String oras = parts[2].trim();
                String strada = parts[3].trim();

                Adresa adresa = new Adresa(oras, strada);
                Student student = new Student(nume, varsta, adresa);

                studenti.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return studenti;
    }

    private static Student cautaStudent(List<Student> studenti, String nume) {
        for (Student student : studenti) {
            if (student.getNume().equals(nume)) {
                return student;
            }
        }

        return null;
    }
}