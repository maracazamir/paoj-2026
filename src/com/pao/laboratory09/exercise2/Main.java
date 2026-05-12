package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip)
        // 2. Scrie toate înregistrările în OUTPUT_FILE cu DataOutputStream (format binar, RECORD_SIZE=32 bytes/înreg.)
        //    - bytes 0-3:   id (int, little-endian via ByteBuffer)
        //    - bytes 4-11:  suma (double, little-endian via ByteBuffer)
        //    - bytes 12-21: data (String, 10 chars ASCII, paddat cu spații la dreapta)
        //    - byte 22:     tip (0=CREDIT, 1=DEBIT)
        //    - byte 23:     status (0=PENDING, 1=PROCESSED, 2=REJECTED)
        //    - bytes 24-31: padding (zerouri)
        // 3. Procesează comenzile din stdin până la EOF cu RandomAccessFile:
        //    - READ idx       → seek(idx * RECORD_SIZE), citește și afișează înregistrarea
        //    - UPDATE idx ST  → seek(idx * RECORD_SIZE + 23), scrie noul status (0/1/2)
        //                       afișează "Updated [idx]: STATUS"
        //    - PRINT_ALL      → citește și afișează toate înregistrările
        //
        // Format linie output:
        //   [idx] id=<id> data=<data> tip=<CREDIT|DEBIT> suma=<suma:.2f> RON status=<STATUS>

        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);

        int n = sc.nextInt();

        File file = new File(OUTPUT_FILE);
        file.getParentFile().mkdirs();

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < n; i++) {
                int id = sc.nextInt();
                double suma = sc.nextDouble();
                String data = sc.next();
                TipTranzactie tip = TipTranzactie.valueOf(sc.next());

                writeRecord(out, id, suma, data, tip);
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (sc.hasNext()) {
                String command = sc.next();

                if (command.equals("READ")) {
                    int idx = sc.nextInt();
                    printRecord(raf, idx);
                } else if (command.equals("UPDATE")) {
                    int idx = sc.nextInt();
                    String status = sc.next();

                    raf.seek((long) idx * RECORD_SIZE + 23);
                    raf.write(statusToByte(status));

                    System.out.println("Updated [" + idx + "]: " + status);
                } else if (command.equals("PRINT_ALL")) {
                    for (int i = 0; i < n; i++) {
                        printRecord(raf, i);
                    }
                }
            }
        }
    }

    private static void writeRecord(DataOutputStream out, int id, double suma,
                                    String data, TipTranzactie tip) throws IOException {
        out.write(ByteBuffer.allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(id)
                .array());

        out.write(ByteBuffer.allocate(8)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putDouble(suma)
                .array());

        byte[] dataBytes = new byte[10];
        Arrays.fill(dataBytes, (byte) ' ');

        byte[] originalData = data.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(originalData, 0, dataBytes, 0, Math.min(originalData.length, 10));
        out.write(dataBytes);

        out.writeByte(tip == TipTranzactie.CREDIT ? 0 : 1);

        out.writeByte(0);

        out.write(new byte[8]);
    }

    private static void printRecord(RandomAccessFile raf, int idx) throws IOException {
        raf.seek((long) idx * RECORD_SIZE);

        byte[] record = new byte[RECORD_SIZE];
        raf.readFully(record);

        ByteBuffer buffer = ByteBuffer.wrap(record).order(ByteOrder.LITTLE_ENDIAN);

        int id = buffer.getInt();
        double suma = buffer.getDouble();

        byte[] dataBytes = new byte[10];
        buffer.get(dataBytes);
        String data = new String(dataBytes, StandardCharsets.US_ASCII).trim();

        byte tipByte = buffer.get();
        byte statusByte = buffer.get();

        TipTranzactie tip = tipByte == 0 ? TipTranzactie.CREDIT : TipTranzactie.DEBIT;
        String status = byteToStatus(statusByte);

        System.out.printf(
                Locale.US,
                "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s%n",
                idx,
                id,
                data,
                tip,
                suma,
                status
        );
    }

    private static byte statusToByte(String status) {
        return switch (status) {
            case "PENDING" -> 0;
            case "PROCESSED" -> 1;
            case "REJECTED" -> 2;
            default -> 0;
        };
    }

    private static String byteToStatus(byte status) {
        return switch (status) {
            case 0 -> "PENDING";
            case 1 -> "PROCESSED";
            case 2 -> "REJECTED";
            default -> "UNKNOWN";
        };
    }
}