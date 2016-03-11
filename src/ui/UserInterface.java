package ui;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInterface extends JFrame {

    JPanel topPanel;
    JTextField passwordField;

    JLabel hasLowerLabel;
    JLabel hasUpperLabel;
    JLabel hasNumberLabel;
    JLabel hasSymbol;

    public UserInterface() {
        this.setPreferredSize(new Dimension(700, 500));
        this.setTitle("Password Strength Tester");

        setupPanel();


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void setupPanel() {
        topPanel = new JPanel();

//        passwordField = new JPasswordField();
        passwordField = new JTextField();

        passwordField.setColumns(25);
        passwordField.addKeyListener(passwordListener);

        hasLowerLabel = new JLabel("No Lower Case");
        hasUpperLabel = new JLabel("No Upper Case");
        hasNumberLabel = new JLabel("No Number");
        hasSymbol = new JLabel("No Special Characters");

        topPanel.add(passwordField);
        topPanel.add(hasLowerLabel);
        topPanel.add(hasUpperLabel);
        topPanel.add(hasNumberLabel);
        topPanel.add(hasSymbol);
        this.add(topPanel);
    }

    private KeyListener passwordListener = new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            Model model = new Model(passwordField.getText());

            hasLowerLabel.setText(!model.hasLowerChar() ? "No Lower Case" : "Has Lower Case");
            hasUpperLabel.setText(!model.hasUpperChar() ? "No Upper Case" : "Has Upper Case");
            hasNumberLabel.setText(!model.hasNumber() ? "No Number" : "Has Number");
            hasSymbol.setText(!model.hasSymbol() ? "No Special Characters" : "Has Special Character");

        }
    };
}
