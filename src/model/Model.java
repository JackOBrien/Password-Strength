package model;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

public class Model {

    private String password;
    private int characterSet;
    private double calcsPerSecond;

    private int commonNumber;

    private final int LOWER_CHARSET = 26;
    private final int UPPER_CHARSET = 26;
    private final int NUMBER_CHARSET = 10;
    private final int SYMBOL_CHARSET = 35;

    private final String PASSWORD_FILE = "/passwords.txt";

    public Model(String password, double calcsPerSecond) {

        this.password = password;
        characterSet = 0;
        this.calcsPerSecond = calcsPerSecond;
        commonNumber = 0;
    }

    public BigDecimal getCrackTime() throws NumberFormatException {

        if (hasLowerChar()) {
            characterSet += LOWER_CHARSET;
        }

        if (hasUpperChar()) {
            characterSet += UPPER_CHARSET;
        }

        if (hasNumber()) {
            characterSet += NUMBER_CHARSET;
        }

        if (hasSymbol()) {
            characterSet += SYMBOL_CHARSET;
        }

        return new BigDecimal((Math.pow(characterSet, password.length())/calcsPerSecond) / 2);
    }

    public boolean hasLowerChar() {
        return !password.equals(password.toUpperCase());
    }

    public boolean hasUpperChar() {
        return !password.equals(password.toLowerCase());
    }

    public boolean hasNumber() {
        return password.matches(".*\\d.*");
    }

    public boolean hasSymbol() {
        return !password.matches("/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/");
    }

    private boolean searchFile() throws IOException {

        URL url = Model.class.getResource(PASSWORD_FILE);

//        File file = new File(url.openStream());
        final Scanner scanner = new Scanner(url.openStream());

        int count = 1;
        while (scanner.hasNextLine()) {
            final String lineFromFile = scanner.nextLine();
            if(lineFromFile.equals(password)) {
                commonNumber = count;
                return true;
            }
            count ++;
        }

        commonNumber = 0;
        return false;
    }

    public int getCommonRange() {
        if (commonNumber < 1) {
            return 0;
        }

        else if (commonNumber <= 5) {
            return 5;
        }

        else if (commonNumber <= 10) {
            return 10;
        }

        else if (commonNumber <= 20) {
            return 20;
        }

        else if (commonNumber <= 50) {
            return 50;
        }

        else if (commonNumber <= 100) {
            return 100;
        }

        else if (commonNumber <= 500) {
            return 500;
        }

        else if (commonNumber <= 1000) {
            return 1000;
        }

        else if (commonNumber <= 5000) {
            return 5000;
        }

        else {
            return 10000;
        }
    }

    public String prettyCrackTime() {

        if (password.length() < 1) return "< Please Enter Password >";

        boolean inFile = false;

        try {
            inFile = searchFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inFile) {
            return "Instantly";
        }

        BigDecimal time = null;

        try {
            time = getCrackTime();
        } catch (NumberFormatException e) {
            return "Infinite";
        }

        String unit;

        if (time.compareTo(new BigDecimal(0.000001)) <= 0) {
            time = time.multiply(new BigDecimal(1000000000));
            time = time.setScale(6, BigDecimal.ROUND_HALF_UP);
            unit = " nanoseconds";
        }

        else if (time.compareTo(new BigDecimal(1)) <= 0) {
            time = time.multiply(new BigDecimal(1000));
            time = time.setScale(6, BigDecimal.ROUND_HALF_UP);
            unit = " milliseconds";
        }

        else if (time.compareTo(new BigDecimal(3600)) < 0) {
            time = time.setScale(6, BigDecimal.ROUND_HALF_UP);
            unit = " seconds";
        }

        else if (time.compareTo(new BigDecimal(86400)) < 0) {
            BigDecimal divisor = new BigDecimal(3600.0);
            time = time.divide(divisor, 6, BigDecimal.ROUND_HALF_UP);
            time = time.setScale(6, BigDecimal.ROUND_HALF_UP);
            unit = " hours";
        }

        else if (time.compareTo(new BigDecimal(20160 * 60)) <= 0) {
            time = time.divide(new BigDecimal(86400.0), 2, BigDecimal.ROUND_HALF_UP);
            time = time.setScale(2, BigDecimal.ROUND_HALF_UP);
            unit = " days";
        }

        else if (time.compareTo(new BigDecimal(3.145 * Math.pow(10, 7))) <= 0) {
            time = time.divide(new BigDecimal(604800), 2, BigDecimal.ROUND_HALF_UP);
            time = time.setScale(2, BigDecimal.ROUND_HALF_UP);
            unit = " weeks";
        }

        else if (time.compareTo(new BigDecimal(Math.pow(10,35))) >= 0){
            BigDecimal divisor = new BigDecimal(3.145 * Math.pow(10, 7));
            time = time.divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
            time = time.setScale(2, BigDecimal.ROUND_HALF_UP);

            NumberFormat formatter = new DecimalFormat("0.0E0");
            formatter.setRoundingMode(RoundingMode.HALF_UP);
            formatter.setMinimumFractionDigits((time.scale() > 0) ? time.precision() : time.scale());
            formatter.setMaximumFractionDigits(5);
            return formatter.format(time) + " years";
        }

        else {
            BigDecimal divisor = new BigDecimal("3.145E7");
            time = time.divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
            time = time.setScale(2, BigDecimal.ROUND_HALF_UP);
            unit = " years";
        }

        NumberFormat df = DecimalFormat.getNumberInstance();
        df.setMaximumFractionDigits(6);
        df.setRoundingMode(RoundingMode.HALF_UP);

        String formatted;

        try {
            formatted = df.format(time);
        } catch (NumberFormatException e) {
            return "Infinite";
        }
        return formatted + unit;
    }
}
