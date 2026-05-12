package com.pao.laboratory09.exercise3;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Vezi Readme.md pentru cerințe
        CoadaTranzactii coada = new CoadaTranzactii();

        ATMThread atm1 = new ATMThread(1, coada);
        ATMThread atm2 = new ATMThread(2, coada);
        ATMThread atm3 = new ATMThread(3, coada);

        ProcessorThread processor = new ProcessorThread(coada);
        Thread processorFir = new Thread(processor);

        processorFir.start();

        atm1.start();
        atm2.start();
        atm3.start();

        atm1.join();
        atm2.join();
        atm3.join();

        while (!coada.esteGoala()) {
            Thread.sleep(20);
        }

        processor.activ = false;

        synchronized (coada) {
            coada.notifyAll();
        }

        processorFir.join();

        System.out.println("Toate tranzactiile procesate. Total: " + processor.totalProcesate);
    }
}

class Tranzactie {
    int id;
    double suma;
    String data;

    public Tranzactie(int id, double suma, String data) {
        this.id = id;
        this.suma = suma;
        this.data = data;
    }
}

class CoadaTranzactii {
    private final Queue<Tranzactie> coada = new LinkedList<>();
    private final int CAPACITATE = 5;

    public synchronized void adauga(Tranzactie t, int atmId) throws InterruptedException {
        while (coada.size() == CAPACITATE) {
            System.out.println("[ATM-" + atmId + "] astept loc...");
            wait();
        }

        coada.add(t);
        notifyAll();
    }

    public synchronized Tranzactie extrage() throws InterruptedException {
        while (coada.isEmpty()) {
            wait();
        }

        Tranzactie t = coada.poll();
        notifyAll();

        return t;
    }

    public synchronized boolean esteGoala() {
        return coada.isEmpty();
    }
}

class ATMThread extends Thread {
    private final int atmId;
    private final CoadaTranzactii coada;

    public ATMThread(int atmId, CoadaTranzactii coada) {
        this.atmId = atmId;
        this.coada = coada;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 4; i++) {
                int id = (atmId - 1) * 4 + i;
                double suma = 100 * id;
                String data = "2024-01-" + String.format("%02d", id);

                Tranzactie t = new Tranzactie(id, suma, data);

                System.out.printf("[ATM-%d] trimite: Tranzactie #%d %.2f RON%n",
                        atmId, t.id, t.suma);

                coada.adauga(t, atmId);

                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class ProcessorThread implements Runnable {
    private final CoadaTranzactii coada;

    public volatile boolean activ = true;
    public int totalProcesate = 0;

    public ProcessorThread(CoadaTranzactii coada) {
        this.coada = coada;
    }

    @Override
    public void run() {
        while (activ || !coada.esteGoala()) {
            try {
                Tranzactie t = coada.extrage();

                Thread.sleep(80);

                System.out.printf("[Processor] Factura #%d - %.2f RON | %s%n",
                        t.id, t.suma, t.data);

                totalProcesate++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}