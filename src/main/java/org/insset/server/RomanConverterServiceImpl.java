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
    public String convertDateYears(String nbr) throws IllegalArgumentException {
        if (nbr == null || nbr.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        String[] parts = nbr.split("/");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid date format. Please enter date as DD/MM/YYYY.");
        }

        int day, month, year;
        try {
            day = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
            year = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid date format. Please ensure DD, MM, and YYYY are numbers.");
        }

        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Day must be between 1 and 31.");
        }

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }

        if (year <= 0 || year > 3000) {
            throw new IllegalArgumentException("Year must be between 1 and 3000.");
        }

        String romanDay = convertArabeToRoman(day);
        String romanMonth = convertArabeToRoman(month);
        String romanYear = convertArabeToRoman(year);

        return String.format("%s/%s/%s", romanDay, romanMonth, romanYear);
    }

    @Override
    public Integer convertRomanToArabe(String nbr) throws IllegalArgumentException {
        if (nbr == null || nbr.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int total = 0;
        int prevValue = 0;

        for (int i = nbr.length() - 1; i >= 0; i--) {
            char ch = nbr.charAt(i);
            int value = getRomanValue(ch);

            if (value < 0) {
                throw new IllegalArgumentException("Invalid Roman numeral: " + ch);
            }

            if (value < prevValue) {
                total -= value;
            } else {
                total += value;
            }
            prevValue = value;
        }

        if (total > 3000) {
            throw new IllegalArgumentException("Roman numeral must represent a value between 1 and 3000");
        }

        return total;
    }

    private int getRomanValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return -1;
        }
    }

    @Override
    public String convertArabeToRoman(Integer nbr) throws IllegalArgumentException {
        if (nbr == null || nbr <= 0 || nbr > 3000) {  // Changed to 3000
            throw new IllegalArgumentException("Input must be a positive integer between 1 and 3000");
        }

        StringBuilder roman = new StringBuilder();

        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < values.length; i++) {
            while (nbr >= values[i]) {
                nbr -= values[i];
                roman.append(symbols[i]);
            }
        }

        return roman.toString();
    }

}
