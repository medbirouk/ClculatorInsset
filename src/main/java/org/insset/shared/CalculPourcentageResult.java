package org.insset.shared;

import java.io.Serializable;

public class CalculPourcentageResult implements Serializable {
    private double prixFinal;
    private double remiseEffectuee;

    public CalculPourcentageResult() {}

    public CalculPourcentageResult(double prixFinal, double remiseEffectuee) {
        this.prixFinal = prixFinal;
        this.remiseEffectuee = remiseEffectuee;
    }

    public double getPrixFinal() {
        return prixFinal;
    }

    public void setPrixFinal(double prixFinal) {
        this.prixFinal = prixFinal;
    }

    public double getRemiseEffectuee() {
        return remiseEffectuee;
    }

    public void setRemiseEffectuee(double remiseEffectuee) {
        this.remiseEffectuee = remiseEffectuee;
    }
}
