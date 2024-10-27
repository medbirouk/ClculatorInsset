package org.insset.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.insset.shared.CalculPourcentageResult;

@RemoteServiceRelativePath("pourcentage")
public interface PourcentageService extends RemoteService {

    CalculPourcentageResult calculerPourcentage(double prixDepart, double pourcentage) throws IllegalArgumentException;

    Double calculerPrixDepart(double prixFinal, double pourcentage) throws IllegalArgumentException;
    Double effectuerDivision(int premierNombre, int deuxiemeNombre) throws IllegalArgumentException;
}
