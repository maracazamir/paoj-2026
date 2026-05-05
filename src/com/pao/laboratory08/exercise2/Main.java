package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Adresa;
import com.pao.laboratory08.exercise1.Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    private static final String OUTPUT_FILE = "rezultate.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește pragul de vârstă din stdin cu Scanner
        // 3. Filtrează studenții cu varsta >= prag
        // 4. Scrie filtrații în "rezultate.txt" cu BufferedWriter
        // 5. Afișează sumarul la consolă

        System.out.println("TODO: implementează exercițiul 2");
        List<Student> studenti = citesteStudenti();

        Scanner scanner = new Scanner(System.in);
        int prag = scanner.nextInt();

        List<Student> filtrati = new ArrayList<>();

        for (Student student : studenti) {
            if (student.getVarsta() >= prag) {
                filtrati.add(student);
            }
        }

        scrieRezultate(filtrati);

        System.out.println("Filtru: varsta >= " + prag);
        System.out.println("Rezultate: " + filtrati.size() + " studenti");
        System.out.println();

        for (Student student : filtrati) {
            System.out.println(student);
        }

        System.out.println();
        System.out.println("Scris in: rezultate.txt");

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

    private static void scrieRezultate(List<Student> studenti) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            for (Student student : studenti) {
                bw.write(student.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}