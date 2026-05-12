package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip) — pot exista duplicate de id
        //    Stochează-le toate într-un ArrayList<Tranzactie> (cu duplicate, ordine inserare)
        //
        // 2. Procesează comenzile din stdin până la EOF:
        //
        //   UNIQUE_IDS      → LinkedHashSet<Integer> cu id-urile în ordinea primei apariții
        //                     afișează: "IDs unice (N): [1, 2, 3, ...]"
        //
        //   MONTHLY_REPORT  → TreeMap<String, ...> grupat pe yyyy-MM (substring 0-7 din data)
        //                     pentru fiecare lună, sumele CREDIT și DEBIT
        //                     format: "yyyy-MM: CREDIT X.XX RON, DEBIT Y.YY RON"
        //
        //   TOP n           → primele n tranzacții după suma descrescătoare (nu modifică lista)
        //                     afișează "Top n:" urmat de n linii
        //
        //   SORT_ASC        → Collections.sort cu suma crescătoare; afișează lista sortată
        //   SORT_DESC       → Collections.sort cu suma descrescătoare; afișează lista sortată
        //   REVERSE         → Collections.reverse; afișează lista
        //   MIN_MAX         → Collections.min/max după suma
        //                     "MIN: [id] data tip: suma RON"
        //                     "MAX: [id] data tip: suma RON"
        //
        //   CME_DEMO        → încearcă for(t : lista) lista.remove(t) în try-catch
        //                     afișează "ConcurrentModificationException prins: modificare in iteratie detectata."
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON

        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);

        int n = sc.nextInt();

        ArrayList<Tranzactie> lista = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = sc.nextInt();
            double suma = sc.nextDouble();
            String data = sc.next();
            TipTranzactie tip = TipTranzactie.valueOf(sc.next());

            lista.add(new Tranzactie(id, suma, data, tip));
        }

        Comparator<Tranzactie> dupaSuma =
                Comparator.comparingDouble(Tranzactie::getSuma);

        while (sc.hasNext()) {
            String comanda = sc.next();

            if (comanda.equals("UNIQUE_IDS")) {
                LinkedHashSet<Integer> ids = new LinkedHashSet<>();

                for (Tranzactie t : lista) {
                    ids.add(t.getId());
                }

                System.out.println("IDs unice (" + ids.size() + "): " + ids);

            } else if (comanda.equals("MONTHLY_REPORT")) {
                TreeMap<String, double[]> raport = new TreeMap<>();

                for (Tranzactie t : lista) {
                    String luna = t.getData().substring(0, 7);
                    raport.putIfAbsent(luna, new double[2]);

                    if (t.getTip() == TipTranzactie.CREDIT) {
                        raport.get(luna)[0] += t.getSuma();
                    } else {
                        raport.get(luna)[1] += t.getSuma();
                    }
                }

                for (Map.Entry<String, double[]> entry : raport.entrySet()) {
                    double[] sume = entry.getValue();

                    System.out.printf(
                            Locale.US,
                            "%s: CREDIT %.2f RON, DEBIT %.2f RON%n",
                            entry.getKey(),
                            sume[0],
                            sume[1]
                    );
                }

            } else if (comanda.equals("TOP")) {
                int topN = sc.nextInt();

                ArrayList<Tranzactie> copie = new ArrayList<>(lista);
                copie.sort(dupaSuma.reversed());

                System.out.println("Top " + topN + ":");

                for (int i = 0; i < topN && i < copie.size(); i++) {
                    System.out.println(copie.get(i));
                }

            } else if (comanda.equals("SORT_ASC")) {
                Collections.sort(lista, dupaSuma);
                afiseazaLista(lista);

            } else if (comanda.equals("SORT_DESC")) {
                Collections.sort(lista, dupaSuma.reversed());
                afiseazaLista(lista);

            } else if (comanda.equals("REVERSE")) {
                Collections.reverse(lista);
                afiseazaLista(lista);

            } else if (comanda.equals("MIN_MAX")) {
                Tranzactie min = Collections.min(lista, dupaSuma);
                Tranzactie max = Collections.max(lista, dupaSuma);

                System.out.println("MIN: " + min);
                System.out.println("MAX: " + max);

            } else if (comanda.equals("CME_DEMO")) {
                try {
                    for (Tranzactie t : lista) {
                        lista.remove(t);
                    }
                } catch (ConcurrentModificationException e) {
                    System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                }
            }
        }
    }

    private static void afiseazaLista(List<Tranzactie> lista) {
        for (Tranzactie t : lista) {
            System.out.println(t);
        }
    }
}