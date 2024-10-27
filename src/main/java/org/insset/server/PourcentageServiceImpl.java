package org.insset.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.insset.client.service.PourcentageService;
import org.insset.shared.CalculPourcentageResult;

public class PourcentageServiceImpl extends RemoteServiceServlet implements PourcentageService {

    @Override
    public CalculPourcentageResult calculerPourcentage(double prixDepart, double pourcentage) throws IllegalArgumentException {
        if (prixDepart < 0 || pourcentage < 0) {
            throw new IllegalArgumentException("Les valeurs doivent être positives.");
        }

        double prixFinal = prixDepart * (1 - pourcentage / 100);
        double remiseEffectuee = prixDepart - prixFinal;

        return new CalculPourcentageResult(prixFinal, remiseEffectuee);
    }

    @Override
    public Double calculerPrixDepart(double prixFinal, double pourcentage) throws IllegalArgumentException {
        if (prixFinal < 0 || pourcentage < 0) {
            throw new IllegalArgumentException("Les valeurs doivent être positives.");
        }

        return prixFinal / (1 - pourcentage / 100);
    }

    @Override
    public Double effectuerDivision(int premierNombre, int deuxiemeNombre) throws IllegalArgumentException {
        if (deuxiemeNombre == 0) {
            throw new IllegalArgumentException("Division par zéro n'est pas autorisée.");
        }
        return (double) premierNombre / deuxiemeNombre;
    }
}

