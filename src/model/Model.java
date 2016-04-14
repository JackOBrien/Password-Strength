package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

public class Model {

    private String password;
    private int characterSet;
    private double calcsPerSecond;

    private final int LOWER_CHARSET = 26;
    private final int UPPER_CHARSET = 26;
    private final int NUMBER_CHARSET = 10;
    private final int SYMBOL_CHARSET = 35;

    private final String PASSWORD_FILE = "src/model/10k_most_common.txt";

    public Model(String password) {
        this.password = password;
        characterSet = 0;
        calcsPerSecond = 4000000000f;
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

        return new BigDecimal(Math.pow(characterSet, password.length())/calcsPerSecond);
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
        return !password.matches("[A-Za-z0-9 ]*");
    }

    private boolean searchFile() throws FileNotFoundException {
        File file = new File(PASSWORD_FILE);
        final Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            final String lineFromFile = scanner.nextLine();
            if(lineFromFile.equals(password)) {
                return true;
            }
        }

        return false;
    }

    public String prettyCrackTime() {

        if (password.length() < 1) return "Enter Password";

        boolean inFile = false;

        try {
            inFile = searchFile();
        } catch (FileNotFoundException e) {
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

        if (time.compareTo(new BigDecimal(0.000999)) <= 0) {
            time = time.multiply(new BigDecimal(1000));
            time = time.setScale(6, BigDecimal.ROUND_HALF_UP);
            unit = " millisecond";
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

        else {
            BigDecimal divisor = new BigDecimal(3.145 * Math.pow(10, 7));
            time = time.divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
            time = time.setScale(2, BigDecimal.ROUND_HALF_UP);
            String str = NumberFormat.getNumberInstance().format(time.setScale(2, BigDecimal.ROUND_HALF_UP));
//            String str = String.format("%,d", time.setScale(2, BigDecimal.ROUND_HALF_UP));
            unit = " years";
        }

        NumberFormat df = DecimalFormat.getNumberInstance();
        df.setMaximumFractionDigits(6);
        df.setRoundingMode(RoundingMode.HALF_UP);

        String formatted = "";

        try {
            formatted = df.format(time);
        } catch (NumberFormatException e) {
            return "Infinite";
        }
        return formatted + unit;
    }
}
