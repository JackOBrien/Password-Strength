package model;

public class Model {

    private String password;
    private int characterSet;
    private double calcsPerSecond;

    public Model(String password) {
        this.password = password;
        characterSet = 0;
        calcsPerSecond = 4000000000f;
    }

    public double getCrackTime() {

        return (password.length() * characterSet) / calcsPerSecond;
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

}
