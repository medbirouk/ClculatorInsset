package org.insset.client.calculator.roman;

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

import org.insset.client.calculator.roman.CalculatorRomainPresenter.MainUiBinder;
import org.insset.client.message.dialogbox.DialogBoxInssetPresenter;
import org.insset.client.service.RomanConverterService;
import org.insset.client.service.RomanConverterServiceAsync;
import org.insset.shared.FieldVerifier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author talend
 */
public class CalculatorRomainPresenter extends Composite {

    @UiField
    public ResetButton boutonClearR;
    @UiField
    public SubmitButton boutonConvertRToA;
    @UiField
    public ResetButton boutonClearA;
    @UiField
    public SubmitButton boutonConvertAToR;
    @UiField
    public ResetButton boutonClearD;
    @UiField
    public SubmitButton boutonConvertD;
    @UiField
    public TextBox valR;
    @UiField
    public TextBox valA;
    @UiField
    public TextBox valD;
    @UiField
    public Label errorLabelRToA;
    @UiField
    public Label errorLabelAToR;
    @UiField
    public Label errorLabelD;

    interface MainUiBinder extends UiBinder<HTMLPanel, CalculatorRomainPresenter> {
    }

    private static MainUiBinder ourUiBinder = GWT.create(MainUiBinder.class);
    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final RomanConverterServiceAsync service = GWT.create(RomanConverterService.class);

    /**
     * Constructeur
     */
    public CalculatorRomainPresenter() {
        initWidget(ourUiBinder.createAndBindUi(this));
        initHandler();
    }

    /**
     * Init des handler
     */
    private void initHandler() {
        boutonClearR.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                valR.setText("");
                errorLabelRToA.setText("");
            }
        });
        boutonConvertRToA.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                convertRomanToArabe();
            }

        });
        boutonClearA.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                valA.setText("");
                errorLabelAToR.setText("");
            }
        });
        boutonConvertAToR.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                convertArabeToRoman();
            }

        });
        boutonClearD.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                valD.setText("");
                errorLabelD.setText("");
            }
        });
        boutonConvertD.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                convertDate();
            }

        });
    }

    /**
     * call server
     */
    private void convertRomanToArabe() {
        if (!FieldVerifier.isValidRoman(valR.getText())) {
            errorLabelRToA.addStyleName("serverResponseLabelError");
            errorLabelRToA.setText("La valeur romaine doit être entre I et MMM (1 à 3000)");  // Error message in French
            return;
        }
        service.convertRomanToArabe(valR.getText(), new AsyncCallback<Integer>() {
            public void onFailure(Throwable caught) {
                String errorMessage = caught.getMessage() != null ? caught.getMessage() : "An unknown error occurred";
                errorLabelRToA.addStyleName("serverResponseLabelError");
                errorLabelRToA.setText(errorMessage);
            }

            public void onSuccess(Integer result) {
                errorLabelRToA.setText(" ");
                new DialogBoxInssetPresenter("Conversion Roman to Arabe", valR.getText(), String.valueOf(result));
            }
        });
    }

    /**
     * call server
     */
    private void convertArabeToRoman() {
        Integer value = null;
        try {
            value = Integer.parseInt(valA.getText());
        } catch (NumberFormatException e) {
            errorLabelAToR.addStyleName("serverResponseLabelError");
            errorLabelAToR.setText("Format incorrect");
            return;
        }

        if (value <= 0 || value > 3000) {  // Check if the value is within range
            errorLabelAToR.addStyleName("serverResponseLabelError");
            errorLabelAToR.setText("La valeur doit être entre 1 et 3000");  // Error message in French
            return;
        }

        service.convertArabeToRoman(value, new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                String errorMessage = caught.getMessage() != null ? caught.getMessage() : "An unknown error occurred";
                errorLabelAToR.addStyleName("serverResponseLabelError");
                errorLabelAToR.setText(errorMessage);
            }

            public void onSuccess(String result) {
                errorLabelAToR.setText(" ");
                new DialogBoxInssetPresenter("Conversion Arabe to Roman", valA.getText(), result);
            }
        });
    }

    /**
     * call server
     */
    private void convertDate() {
        if (!FieldVerifier.isValidDate(valD.getText())) {
            errorLabelD.addStyleName("serverResponseLabelError");
            errorLabelD.setText("La date doit être au format JJ/MM/AAAA avec une année entre 1 et 3000");  // Error message in French
            return;
        }

        service.convertDateYears(valD.getText(), new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                String errorMessage = caught.getMessage() != null ? caught.getMessage() : "An unknown error occurred";
                errorLabelD.addStyleName("serverResponseLabelError");
                errorLabelD.setText(errorMessage);
            }

            public void onSuccess(String result) {
                errorLabelD.setText(" ");
                new DialogBoxInssetPresenter("Conversion Date", valD.getText(), result);
            }
        });
    }

}
