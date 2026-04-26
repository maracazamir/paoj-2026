# Proiect POO - Clinica Dentara

## Descriere

Acest proiect reprezinta o aplicatie Java pentru gestionarea unei clinici dentare. Sistemul permite administrarea pacientilor, programarilor, procedurilor medicale si facturilor.

Aplicatia este realizata folosind concepte de programare orientata pe obiecte, precum mostenire, abstractizare, incapsulare, servicii Singleton, colectii si exceptii custom.

---

## Actiuni posibile in sistem

1. Adaugarea unui pacient nou in sistem
2. Stergerea unui pacient dupa id
3. Stergerea unui pacient dupa nume complet
4. Cautarea unui pacient dupa nume
5. Cautarea unui pacient dupa id
6. Afisarea tuturor pacientilor
7. Adaugarea unei programari
8. Afisarea programarilor sortate dupa data
9. Anularea unei programari
10. Generarea unei facturi pentru un pacient
11. Afisarea tuturor facturilor
12. Cautarea unei facturi dupa id
13. Stergerea unei facturi dupa id

---

## Tipuri de obiecte definite in sistem

1. Persoana
2. Pacient
3. Angajat
4. Medic
5. Asistent
6. Cabinet
7. Programare
8. Tratament
9. Procedura
10. Factura
11. CodProgramare

---

## Structura proiectului

```text
com.pao.proiect.clinicadentara
├── model
│   ├── Persoana.java
│   ├── Pacient.java
│   ├── Angajat.java
│   ├── Medic.java
│   ├── Asistent.java
│   ├── Cabinet.java
│   ├── Programare.java
│   ├── Tratament.java
│   ├── Procedura.java
│   ├── Factura.java
│   └── CodProgramare.java
├── service
│   ├── PacientService.java
│   ├── ProgramareService.java
│   └── FacturaService.java
├── exception
│   ├── PacientNegasitException.java
│   ├── ProgramareNegasitaException.java
│   └── FacturaNegasitaException.java
├── Meniu.java
└── Main.java