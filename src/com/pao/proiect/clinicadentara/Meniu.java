package com.pao.proiect.clinicadentara;

import com.pao.proiect.clinicadentara.exception.FacturaNegasitaException;
import com.pao.proiect.clinicadentara.exception.PacientNegasitException;
import com.pao.proiect.clinicadentara.exception.ProgramareNegasitaException;
import com.pao.proiect.clinicadentara.model.*;
import com.pao.proiect.clinicadentara.service.FacturaService;
import com.pao.proiect.clinicadentara.service.PacientService;
import com.pao.proiect.clinicadentara.service.ProgramareService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Meniu {
    private Scanner scanner;
    private PacientService pacientService;
    private ProgramareService programareService;
    private FacturaService facturaService;
    private int urmatorulIdPacient;

    public Meniu() {
        scanner = new Scanner(System.in);
        pacientService = PacientService.getInstance();
        programareService = ProgramareService.getInstance();
        facturaService = FacturaService.getInstance();
        urmatorulIdPacient = 1;
    }

    public void porneste() {
        int optiune;

        do {
            afiseazaMeniu();
            System.out.print("Alege optiunea: ");
            optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    adaugaPacientDinTastatura();
                    break;
                case 2:
                    stergePacientDupaId();
                    break;
                case 3:
                    stergePacientDupaNumeComplet();
                    break;
                case 4:
                    cautaPacientDupaNume();
                    break;
                case 5:
                    cautaPacientDupaId();
                    break;
                case 6:
                    pacientService.afiseazaPacienti();
                    break;
                case 7:
                    adaugaProgramareDemo();
                    break;
                case 8:
                    programareService.afiseazaProgramari();
                    break;
                case 9:
                    anuleazaProgramareDinTastatura();
                    break;
                case 10:
                    genereazaFacturaDemo();
                    break;
                case 11:
                    facturaService.afiseazaFacturi();
                    break;
                case 12:
                    cautaFacturaDupaId();
                    break;
                case 13:
                    stergeFacturaDupaId();
                    break;
                case 0:
                    System.out.println("Aplicatia s-a inchis.");
                    break;
                default:
                    System.out.println("Optiune invalida.");
            }

        } while (optiune != 0);
    }

    private void afiseazaMeniu() {
        System.out.println("\n===== MENIU CLINICA DENTARA =====");

        System.out.println("---- PACIENTI ----");
        System.out.println("1. Adauga pacient");
        System.out.println("2. Sterge pacient dupa id");
        System.out.println("3. Sterge pacient dupa nume complet");
        System.out.println("4. Cauta pacient dupa nume");
        System.out.println("5. Cauta pacient dupa id");
        System.out.println("6. Afiseaza toti pacientii");

        System.out.println("---- PROGRAMARI ----");
        System.out.println("7. Adauga programare demo");
        System.out.println("8. Afiseaza programari");
        System.out.println("9. Anuleaza programare");

        System.out.println("---- FACTURI ----");
        System.out.println("10. Genereaza factura demo");
        System.out.println("11. Afiseaza facturi");
        System.out.println("12. Cauta factura dupa id");
        System.out.println("13. Sterge factura dupa id");

        System.out.println("0. Iesire");
    }

    private void adaugaPacientDinTastatura() {
        System.out.print("Nume: ");
        String nume = scanner.nextLine();

        System.out.print("Prenume: ");
        String prenume = scanner.nextLine();

        System.out.print("Telefon: ");
        String telefon = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("An nastere: ");
        int an = scanner.nextInt();

        System.out.print("Luna nastere: ");
        int luna = scanner.nextInt();

        System.out.print("Zi nastere: ");
        int zi = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Adresa: ");
        String adresa = scanner.nextLine();

        System.out.print("Contact urgenta: ");
        String contactUrgenta = scanner.nextLine();

        System.out.print("Gen M/F: ");
        String gen = scanner.nextLine();

        Pacient pacient = new Pacient(
                urmatorulIdPacient,
                nume,
                prenume,
                telefon,
                email,
                LocalDate.of(an, luna, zi),
                adresa,
                contactUrgenta,
                gen
        );

        pacientService.adaugaPacient(pacient);
        urmatorulIdPacient++;

        System.out.println("Pacient adaugat cu succes!");
    }

    private void stergePacientDupaId() {
        System.out.print("Introdu id-ul pacientului de sters: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        pacientService.stergePacient(id);
        System.out.println("Daca pacientul exista, a fost sters.");
    }

    private void stergePacientDupaNumeComplet() {
        System.out.print("Nume: ");
        String nume = scanner.nextLine();

        System.out.print("Prenume: ");
        String prenume = scanner.nextLine();

        pacientService.stergePacientDupaNumeComplet(nume, prenume);
    }

    private void cautaPacientDupaNume() {
        System.out.print("Introdu numele pacientului: ");
        String nume = scanner.nextLine();

        Pacient pacient = pacientService.cautaPacientDupaNume(nume);

        if (pacient == null) {
            System.out.println("Pacientul nu a fost gasit.");
        } else {
            System.out.println(pacient);
        }
    }

    private void cautaPacientDupaId() {
        System.out.print("ID pacient: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Pacient pacient = pacientService.cautaPacientDupaId(id);
            System.out.println(pacient);
        } catch (PacientNegasitException e) {
            System.out.println(e.getMessage());
        }
    }

    private void adaugaProgramareDemo() {
        if (pacientService.getTotiPacientii().isEmpty()) {
            System.out.println("Nu exista pacienti. Adauga mai intai un pacient.");
            return;
        }

        Pacient pacient = pacientService.getTotiPacientii().get(0);

        Medic medic = new Medic(
                1, "Doctor", "Demo",
                "+40700000000", "demo@gmail.com",
                LocalDate.of(1980, 1, 1),
                "Bucuresti", 8000, "Stomatologie"
        );

        Cabinet cabinet = new Cabinet(1, 101, 1, 2);

        CodProgramare cod = new CodProgramare("PROG" + (int) (Math.random() * 1000));
        System.out.println("Cod programare generat: " + cod);

        Programare programare = new Programare(
                (int) (Math.random() * 1000),
                pacient,
                medic,
                cabinet,
                LocalDateTime.now().plusDays(1)
        );

        programareService.adaugaProgramare(programare);
        System.out.println("Programare adaugata!");
    }

    private void anuleazaProgramareDinTastatura() {
        System.out.print("ID programare: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            programareService.anuleazaProgramare(id);
            System.out.println("Programare anulata.");
        } catch (ProgramareNegasitaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void genereazaFacturaDemo() {
        if (pacientService.getTotiPacientii().isEmpty()) {
            System.out.println("Nu exista pacienti. Adauga mai intai un pacient.");
            return;
        }

        Pacient pacient = pacientService.getTotiPacientii().get(0);

        Tratament tratament = new Tratament(1, "Detartraj", 250, 1);

        Medic medic = new Medic(
                1, "Doctor", "Demo",
                "+40700000000", "demo@gmail.com",
                LocalDate.of(1980, 1, 1),
                "Bucuresti", 8000, "Stomatologie"
        );

        Procedura procedura = new Procedura(
                1,
                pacient,
                medic,
                tratament,
                LocalDate.now()
        );

        List<Procedura> listaProceduri = new ArrayList<>();
        listaProceduri.add(procedura);

        Factura factura = facturaService.genereazaFactura(
                (int) (Math.random() * 1000),
                pacient,
                listaProceduri
        );

        System.out.println("Factura generata:");
        System.out.println(factura);
    }

    private void cautaFacturaDupaId() {
        System.out.print("ID factura: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Factura factura = facturaService.cautaFacturaDupaId(id);
            System.out.println(factura);
        } catch (FacturaNegasitaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void stergeFacturaDupaId() {
        System.out.print("ID factura: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            facturaService.stergeFactura(id);
            System.out.println("Factura a fost stearsa.");
        } catch (FacturaNegasitaException e) {
            System.out.println(e.getMessage());
        }
    }
}