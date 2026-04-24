package com.pao.laboratory07.exercise3;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Vezi Readme.md pentru cerințe
        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine().trim());
        List<Comanda> comenzi = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String line = sc.nextLine().trim();
            String[] tokens = line.split(" ");

            switch (tokens[0]) {
                case "STANDARD" -> {
                    String nume = tokens[1];
                    double pret = Double.parseDouble(tokens[2]);
                    String client = tokens[3];
                    comenzi.add(new ComandaStandard(nume, client, pret));
                }
                case "DISCOUNTED" -> {
                    String nume = tokens[1];
                    double pret = Double.parseDouble(tokens[2]);
                    int discount = Integer.parseInt(tokens[3]);
                    String client = tokens[4];
                    comenzi.add(new ComandaRedusa(nume, client, pret, discount));
                }
                case "GIFT" -> {
                    String nume = tokens[1];
                    String client = tokens[2];
                    comenzi.add(new ComandaGratuita(nume, client));
                }
                default -> throw new InvalidCommandException("Tip de comanda invalid: " + tokens[0]);
            }
        }

        for (Comanda c : comenzi) {
            System.out.println(c.descriere());
        }

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.equals("QUIT")) {
                break;
            } else if (line.equals("STATS")) {
                afiseazaStats(comenzi);
            } else if (line.startsWith("FILTER")) {
                String[] parts = line.split(" ");
                double threshold = Double.parseDouble(parts[1]);
                afiseazaFilter(comenzi, threshold);
            } else if (line.equals("SORT")) {
                afiseazaSort(comenzi);
            } else if (line.equals("SPECIAL")) {
                afiseazaSpecial(comenzi);
            } else {
                throw new InvalidCommandException("Comanda invalida: " + line);
            }
        }
    }

    private static void afiseazaStats(List<Comanda> comenzi) {
        System.out.println();
        System.out.println("--- STATS ---");

        Map<String, Double> medii = comenzi.stream()
                .collect(Collectors.groupingBy(
                        Comanda::tipComanda,
                        LinkedHashMap::new,
                        Collectors.averagingDouble(Comanda::pretFinal)
                ));

        if (medii.containsKey("STANDARD")) {
            System.out.printf("STANDARD: medie = %.2f lei%n", medii.get("STANDARD"));
        }
        if (medii.containsKey("DISCOUNTED")) {
            System.out.printf("DISCOUNTED: medie = %.2f lei%n", medii.get("DISCOUNTED"));
        }
        if (medii.containsKey("GIFT")) {
            System.out.printf("GIFT: medie = %.2f lei%n", medii.get("GIFT"));
        }
    }

    private static void afiseazaFilter(List<Comanda> comenzi, double threshold) {
        System.out.println();
        System.out.printf("--- FILTER (>= %.2f) ---%n", threshold);

        List<Comanda> filtrate = comenzi.stream()
                .filter(c -> c.pretFinal() >= threshold)
                .toList();

        for (Comanda c : filtrate) {
            System.out.println(c.descriereScurta());
        }
    }

    private static void afiseazaSort(List<Comanda> comenzi) {
        System.out.println();
        System.out.println("--- SORT (by client, then by pret) ---");

        List<Comanda> sortate = comenzi.stream()
                .sorted(Comparator.comparing(Comanda::getClient)
                        .thenComparing(Comanda::pretFinal))
                .toList();

        for (Comanda c : sortate) {
            System.out.println(c.descriereScurta());
        }
    }

    private static void afiseazaSpecial(List<Comanda> comenzi) {
        System.out.println();
        System.out.println("--- SPECIAL (discount > 15%) ---");

        List<ComandaRedusa> speciale = comenzi.stream()
                .filter(c -> c instanceof ComandaRedusa)
                .map(c -> (ComandaRedusa) c)
                .filter(c -> c.getDiscountProcent() > 15)
                .toList();

        for (ComandaRedusa c : speciale) {
            System.out.println(c.descriereScurta());
        }
    }
}