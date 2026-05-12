package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data contSursa contDestinatie tip)
        // 2. Setează câmpul note = "procesat" pe fiecare tranzacție înainte de serializare
        // 3. Serializează lista de tranzacții în OUTPUT_FILE cu ObjectOutputStream (try-with-resources)
        // 4. Deserializează lista din OUTPUT_FILE cu ObjectInputStream (try-with-resources)
        // 5. Procesează comenzile din stdin până la EOF:
        //    - LIST          → afișează toate tranzacțiile, câte una pe linie
        //    - FILTER yyyy-MM → afișează tranzacțiile cu data care începe cu yyyy-MM
        //                       sau "Niciun rezultat." dacă nu există
        //    - NOTE id        → afișează "NOTE[id]: <valoarea câmpului note>"
        //                       sau "NOTE[id]: not found" dacă id-ul nu există
        //
        // Format linie tranzacție:
        //   [id] data tip: suma RON | contSursa -> contDestinatie
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON | RO01SRC1 -> RO01DST1



        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);

        int n = sc.nextInt();

        List<Tranzactie> tranzactii = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = sc.nextInt();
            double suma = sc.nextDouble();
            String data = sc.next();
            String contSursa = sc.next();
            String contDestinatie = sc.next();
            TipTranzactie tip = TipTranzactie.valueOf(sc.next());

            Tranzactie t = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);
            t.note = "procesat";
            tranzactii.add(t);
        }

        File file = new File(OUTPUT_FILE);
        file.getParentFile().mkdirs();

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(tranzactii);
        }

        List<Tranzactie> tranzactiiCitite;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            tranzactiiCitite = (List<Tranzactie>) in.readObject();
        }

        while (sc.hasNext()) {
            String comanda = sc.next();

            if (comanda.equals("LIST")) {
                for (Tranzactie t : tranzactiiCitite) {
                    afiseazaTranzactie(t);
                }
            } else if (comanda.equals("FILTER")) {
                String luna = sc.next();
                boolean gasit = false;

                for (Tranzactie t : tranzactiiCitite) {
                    if (t.data.startsWith(luna)) {
                        afiseazaTranzactie(t);
                        gasit = true;
                    }
                }

                if (!gasit) {
                    System.out.println("Niciun rezultat.");
                }
            } else if (comanda.equals("NOTE")) {
                int idCautat = sc.nextInt();
                boolean gasit = false;

                for (Tranzactie t : tranzactiiCitite) {
                    if (t.id == idCautat) {
                        System.out.println("NOTE[" + idCautat + "]: " + t.note);
                        gasit = true;
                        break;
                    }
                }

                if (!gasit) {
                    System.out.println("NOTE[" + idCautat + "]: not found");
                }
            }
        }
    }

    private static void afiseazaTranzactie(Tranzactie t) {
        System.out.printf(
                Locale.US,
                "[%d] %s %s: %.2f RON | %s -> %s%n",
                t.id,
                t.data,
                t.tip,
                t.suma,
                t.contSursa,
                t.contDestinatie
        );
    }
}