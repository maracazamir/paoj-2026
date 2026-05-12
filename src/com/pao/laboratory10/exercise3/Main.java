package com.pao.laboratory10.exercise3;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Vezi Readme.md pentru cerințe
        List<Tranzactie> tranzactii = Arrays.asList(
                new Tranzactie(1, 1500.00, "2024-01-15", "CONT_A", TipTranzactie.CREDIT),
                new Tranzactie(2, 750.50, "2024-01-22", "CONT_B", TipTranzactie.DEBIT),
                new Tranzactie(3, 200.00, "2024-02-05", "CONT_A", TipTranzactie.CREDIT),
                new Tranzactie(4, 1200.00, "2024-02-18", "CONT_C", TipTranzactie.DEBIT),
                new Tranzactie(5, 900.00, "2024-03-01", "CONT_B", TipTranzactie.CREDIT),
                new Tranzactie(6, 300.00, "2024-03-10", "CONT_D", TipTranzactie.DEBIT),
                new Tranzactie(7, 2200.00, "2024-01-28", "CONT_A", TipTranzactie.CREDIT),
                new Tranzactie(8, 100.00, "2024-02-20", "CONT_C", TipTranzactie.DEBIT),
                new Tranzactie(9, 1750.00, "2024-03-25", "CONT_E", TipTranzactie.CREDIT),
                new Tranzactie(10, 450.00, "2024-04-03", "CONT_B", TipTranzactie.DEBIT)
        );

        System.out.println("1. Tranzactii CREDIT:");
        tranzactii.stream()
                .filter(t -> t.getTip() == TipTranzactie.CREDIT)
                .forEach(System.out::println);

        System.out.println();

        System.out.println("2. Total procesat:");
        double total = tranzactii.stream()
                .mapToDouble(Tranzactie::getSuma)
                .sum();
        System.out.printf(Locale.US, "Total procesat: %.2f RON%n", total);

        System.out.println();

        System.out.println("3. Total pe luna:");
        Map<String, Double> totalPeLuna = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new,
                        Collectors.summingDouble(Tranzactie::getSuma)
                ));

        totalPeLuna.forEach((luna, suma) ->
                System.out.printf(Locale.US, "%s: %.2f RON%n", luna, suma)
        );

        System.out.println();

        System.out.println("4. Top 3 tranzactii:");
        tranzactii.stream()
                .sorted(Comparator.comparingDouble(Tranzactie::getSuma).reversed())
                .limit(3)
                .forEach(System.out::println);

        System.out.println();

        System.out.println("5. Conturi sursa unice:");
        List<String> conturiUnice = tranzactii.stream()
                .map(Tranzactie::getContSursa)
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Conturi sursa unice: " + conturiUnice);

        System.out.println();

        System.out.println("6. Suma medie:");
        double medie = tranzactii.stream()
                .mapToDouble(Tranzactie::getSuma)
                .average()
                .orElse(0.0);

        System.out.printf(Locale.US, "Suma medie: %.2f RON%n", medie);

        System.out.println();

        System.out.println("7. Extrase de cont lunare:");
        Map<String, List<Tranzactie>> tranzactiiPeLuna = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new,
                        Collectors.toList()
                ));

        tranzactiiPeLuna.forEach((luna, lista) -> {
            double sumaLunara = lista.stream()
                    .mapToDouble(Tranzactie::getSuma)
                    .sum();

            System.out.printf(
                    Locale.US,
                    "EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON%n",
                    luna,
                    lista.size(),
                    sumaLunara
            );
        });
    }
}

enum TipTranzactie {
    CREDIT,
    DEBIT
}

class Tranzactie {
    private int id;
    private double suma;
    private String data;
    private String contSursa;
    private TipTranzactie tip;

    public Tranzactie(int id, double suma, String data, String contSursa, TipTranzactie tip) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.contSursa = contSursa;
        this.tip = tip;
    }

    public double getSuma() {
        return suma;
    }

    public String getData() {
        return data;
    }

    public String getContSursa() {
        return contSursa;
    }

    public TipTranzactie getTip() {
        return tip;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "[%d] %s %s: %.2f RON | contSursa=%s",
                id,
                data,
                tip,
                suma,
                contSursa
        );
    }
}