/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.insset.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.insset.client.service.RomanConverterService;

/**
 *
 * @author user
 */
@SuppressWarnings("serial")
public class RomanConverterServiceImpl extends RemoteServiceServlet implements
        RomanConverterService {

    @Override
    public String convertDateYears(String date) throws IllegalArgumentException {
        // Step 1: Split the date into day, month, year (French format: DD/MM/YYYY)
        String[] dateParts = date.split("/");

        // Validate the input format
        if (dateParts.length != 3) {
            throw new IllegalArgumentException("Format de date invalide. Utilisez JJ/MM/AAAA");
        }

        try {
            // Parse day, month, and year from the input
            int day = Integer.parseInt(dateParts[0]);   // Day (JJ)
            int month = Integer.parseInt(dateParts[1]); // Month (MM)
            int year = Integer.parseInt(dateParts[2]);  // Year (AAAA)

            // Step 2: Convert each part to Roman numerals
            String romanDay = convertArabeToRoman(day);
            String romanMonth = convertArabeToRoman(month);
            String romanYear = convertArabeToRoman(year);

            // Step 3: Return the formatted date in Roman numeral format (JJ/MM/AAAA)
            return romanDay + "/" + romanMonth + "/" + romanYear;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Les nombres dans la date sont invalides.");
        }
    }

    @Override
    public Integer convertRomanToArabe(String nbr) throws IllegalArgumentException {
        //Implement your code
        return 3;
    }

    @Override
    public String convertArabeToRoman(Integer nbr) throws IllegalArgumentException {
        //Implement your code
        return new String("XVXX");
    }

}
