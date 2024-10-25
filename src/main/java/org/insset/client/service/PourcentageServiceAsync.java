package org.insset.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.insset.shared.CalculPourcentageResult;

public interface PourcentageServiceAsync {

    void calculerPourcentage(double prixDepart, double pourcentage, AsyncCallback<CalculPourcentageResult> callback);

    void calculerPrixDepart(double prixFinal, double pourcentage, AsyncCallback<Double> callback);
    void effectuerDivision(int premierNombre, int deuxiemeNombre, AsyncCallback<Double> callback);
}
