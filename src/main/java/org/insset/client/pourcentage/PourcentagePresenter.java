package org.insset.client.pourcentage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResetButton;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import org.insset.client.menu.Menu;
import org.insset.client.service.PourcentageService;
import org.insset.client.service.PourcentageServiceAsync;
import org.insset.shared.CalculPourcentageResult;

public class PourcentagePresenter extends Composite {

    @UiField
    public Menu menu;
    @UiField
    public ResetButton clear1, clear2;
    @UiField
    public SubmitButton calculerPrixFinal, calculerPrixDepart;
    @UiField
    public TextBox prixDepart, pourcentageRemise, prixFinal, remiseEffectuee;
    @UiField
    public TextBox prixFinalInput, pourcentageRemiseInput, prixDepartCalculé;
    @UiField
    public Label errorLabel1, errorLabel2;
    @UiField
    public TextBox premierNombre, deuxiemeNombre, resultatDivision;
    @UiField
    public Label errorLabel3;
    @UiField
    public SubmitButton calculerDivision;
    @UiField
    public ResetButton clear3;

    private final PourcentageServiceAsync service = GWT.create(PourcentageService.class);

    interface AddUiBinder extends UiBinder<HTMLPanel, PourcentagePresenter> {
    }

    private static AddUiBinder ourUiBinder = GWT.create(AddUiBinder.class);

    public PourcentagePresenter() {
        initWidget(ourUiBinder.createAndBindUi(this));
        initHandler();
    }

    private void initHandler() {
        clear1.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                prixDepart.setText("");
                pourcentageRemise.setText("");
                prixFinal.setText("");
                remiseEffectuee.setText("");
                errorLabel1.setText("");
            }
        });

        clear2.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                prixFinalInput.setText("");
                pourcentageRemiseInput.setText("");
                prixDepartCalculé.setText("");
                errorLabel2.setText("");
            }
        });

        clear3.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                premierNombre.setText("");
                deuxiemeNombre.setText("");
                resultatDivision.setText("");
                errorLabel3.setText("");
            }
        });

        calculerPrixFinal.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                calculateFinalPrice();
            }
        });

        calculerPrixDepart.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                calculateOriginalPrice();
            }
        });
        calculerDivision.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                performDivision();
            }
        });

    }

    private void calculateFinalPrice() {
        try {
            double depart = Double.parseDouble(prixDepart.getText());
            double percentage = Double.parseDouble(pourcentageRemise.getText());

            service.calculerPourcentage(depart, percentage, new AsyncCallback<CalculPourcentageResult>() {
                @Override
                public void onFailure(Throwable caught) {
                    errorLabel1.setText("Erreur lors du calcul.");
                }

                @Override
                public void onSuccess(CalculPourcentageResult result) {
                    prixFinal.setText(String.valueOf(result.getPrixFinal()));
                    remiseEffectuee.setText(String.valueOf(result.getRemiseEffectuee()));
                }
            });

        } catch (NumberFormatException e) {
            errorLabel1.setText("Veuillez entrer des valeurs numériques valides.");
        }
    }

    private void performDivision() {
        try {
            int number1 = Integer.parseInt(premierNombre.getText());
            int number2 = Integer.parseInt(deuxiemeNombre.getText());

            service.effectuerDivision(number1, number2, new AsyncCallback<Double>() {
                @Override
                public void onFailure(Throwable caught) {
                    if (caught instanceof IllegalArgumentException) {
                        errorLabel3.setText(caught.getMessage());
                    } else {
                        errorLabel3.setText("Erreur lors du calcul.");
                    }
                }

                @Override
                public void onSuccess(Double result) {
                    resultatDivision.setText(String.valueOf(result));
                }
            });

        } catch (NumberFormatException e) {
            errorLabel3.setText("Veuillez entrer des nombres entiers valides.");
        }
    }

    private void calculateOriginalPrice() {
        try {
            double finalPrice = Double.parseDouble(prixFinalInput.getText());
            double percentage = Double.parseDouble(pourcentageRemiseInput.getText());

            service.calculerPrixDepart(finalPrice, percentage, new AsyncCallback<Double>() {
                @Override
                public void onFailure(Throwable caught) {
                    errorLabel2.setText("Erreur lors du calcul.");
                }

                @Override
                public void onSuccess(Double result) {
                    prixDepartCalculé.setText(String.valueOf(result));
                }
            });

        } catch (NumberFormatException e) {
            errorLabel2.setText("Veuillez entrer des valeurs numériques valides.");
        }
    }
}
