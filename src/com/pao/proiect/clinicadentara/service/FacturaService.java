package com.pao.proiect.clinicadentara.service;

import com.pao.proiect.clinicadentara.exception.FacturaNegasitaException;
import com.pao.proiect.clinicadentara.model.Factura;
import com.pao.proiect.clinicadentara.model.Pacient;
import com.pao.proiect.clinicadentara.model.Procedura;

import java.util.ArrayList;
import java.util.List;

public class FacturaService {
    private static FacturaService instance;

    private List<Factura> facturi;

    private FacturaService() {
        facturi = new ArrayList<>();
    }

    public static FacturaService getInstance() {
        if (instance == null) {
            instance = new FacturaService();
        }
        return instance;
    }

    public Factura genereazaFactura(int idFactura, Pacient pacient, List<Procedura> proceduri) {
        if (pacient == null || proceduri == null || proceduri.isEmpty()) {
            return null;
        }

        Factura factura = new Factura(idFactura, pacient, proceduri);
        facturi.add(factura);
        return factura;
    }

    public Factura cautaFacturaDupaId(int idFactura) throws FacturaNegasitaException {
        for (Factura factura : facturi) {
            if (factura.getIdFactura() == idFactura) {
                return factura;
            }
        }

        throw new FacturaNegasitaException("Factura nu exista!");
    }

    public void stergeFactura(int idFactura) throws FacturaNegasitaException {
        Factura factura = cautaFacturaDupaId(idFactura);
        facturi.remove(factura);
    }

    public void afiseazaFacturi() {
        if (facturi.isEmpty()) {
            System.out.println("Nu exista facturi.");
            return;
        }

        for (Factura factura : facturi) {
            System.out.println(factura);
        }
    }

    public List<Factura> getToateFacturile() {
        return facturi;
    }
}