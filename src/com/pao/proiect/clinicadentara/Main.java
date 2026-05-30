package com.pao.proiect.clinicadentara;

import com.pao.proiect.clinicadentara.model.*;
import com.pao.proiect.clinicadentara.repository.*;
import com.pao.proiect.clinicadentara.service.AuditService;
import com.pao.proiect.clinicadentara.service.ClinicaJdbcService;
import com.pao.proiect.clinicadentara.util.SchemaInitializer;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        try {
            SchemaInitializer.initialize();

            AuditService auditService = AuditService.getInstance();
            ClinicaJdbcService clinicaJdbcService = ClinicaJdbcService.getInstance();

            PacientRepository pacientRepository = new PacientRepository();
            MedicRepository medicRepository = new MedicRepository();
            CabinetRepository cabinetRepository = new CabinetRepository();
            ProgramareRepository programareRepository = new ProgramareRepository();

            Pacient pacient = new Pacient(
                    1,
                    "Popescu",
                    "Ion",
                    "+40700000000",
                    "ion@gmail.com",
                    LocalDate.of(2000, 5, 10),
                    "Bucuresti",
                    "+40711111111",
                    "M"
            );

            Medic medic = new Medic(
                    1,
                    "Ionescu",
                    "Ana",
                    "+40722222222",
                    "ana@gmail.com",
                    LocalDate.of(1980, 2, 15),
                    "Bucuresti",
                    9000,
                    "Stomatologie"
            );

            Cabinet cabinet = new Cabinet(1, 101, 1, 2);

            Programare programare = new Programare(
                    1,
                    pacient,
                    medic,
                    cabinet,
                    LocalDateTime.of(2026, 6, 10, 14, 30)
            );

            pacientRepository.save(pacient);
            auditService.logAction("adauga_pacient");

            medicRepository.save(medic);
            auditService.logAction("adauga_medic");

            cabinetRepository.save(cabinet);
            auditService.logAction("adauga_cabinet");

            programareRepository.save(programare);
            auditService.logAction("adauga_programare");

            System.out.println("\n=== CRUD: Pacienti ===");
            pacientRepository.findAll().forEach(System.out::println);
            auditService.logAction("listeaza_pacienti");

            System.out.println("\n=== CRUD: Medici ===");
            medicRepository.findAll().forEach(System.out::println);
            auditService.logAction("listeaza_medici");

            System.out.println("\n=== CRUD: Cabinete ===");
            cabinetRepository.findAll().forEach(System.out::println);
            auditService.logAction("listeaza_cabinete");

            System.out.println("\n=== CRUD: Programari ===");
            programareRepository.findAll().forEach(System.out::println);
            auditService.logAction("listeaza_programari");

            System.out.println("\n=== Cautare pacient dupa id ===");
            pacientRepository.findById(1).ifPresent(System.out::println);
            auditService.logAction("cauta_pacient_dupa_id");

            System.out.println("\n=== Update pacient ===");
            Pacient pacientActualizat = new Pacient(
                    1,
                    "Popescu",
                    "Ionel",
                    "+40700000000",
                    "ionel@gmail.com",
                    LocalDate.of(2000, 5, 10),
                    "Bucuresti",
                    "+40711111111",
                    "M"
            );
            pacientRepository.update(pacientActualizat);
            pacientRepository.findById(1).ifPresent(System.out::println);
            auditService.logAction("actualizeaza_pacient");

            System.out.println("\n=== Tranzactie JDBC: creare programare noua ===");
            clinicaJdbcService.programeazaPacientCuTranzactie(
                    2,
                    1,
                    1,
                    1,
                    "2026-06-12T10:00"
            );
            auditService.logAction("programare_tranzactie");

            System.out.println("\n=== JOIN 1: Programari complete ===");
            clinicaJdbcService.raportProgramariComplete()
                    .forEach(System.out::println);
            auditService.logAction("raport_programari_complete");

            System.out.println("\n=== JOIN 2: Programari pentru pacient ===");
            clinicaJdbcService.raportProgramariPentruPacient(1)
                    .forEach(System.out::println);
            auditService.logAction("raport_programari_pacient");

            System.out.println("\n=== JOIN 3: Numar programari per medic ===");
            clinicaJdbcService.raportNumarProgramariPerMedic()
                    .forEach(System.out::println);
            auditService.logAction("raport_programari_medic");

            System.out.println("\nTest Etapa II terminat cu succes.");

        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
            e.printStackTrace();
        }
    }
}