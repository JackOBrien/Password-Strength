package ui;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInterface extends JFrame {

    private JPanel mainPanel;
    private JTextField passwordField;

    private JLabel hasLowerLabel;
    private JLabel hasUpperLabel;
    private JLabel hasNumberLabel;
    private JLabel hasSymbol;

    private JLabel crackTime;

    private final String X = "[✖]";
    private final String CHECK = "[✔]";

    private final Color RED = Color.RED;
    private final Color GREEN = new Color(37, 158, 50);

    public UserInterface() {
//        this.setPreferredSize(new Dimension(700, 500));
        this.setTitle("Password Strength Tester");

        setupPanel();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void setupPanel() {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

//        passwordField = new JPasswordField();
        passwordField = new JTextField();

        passwordField.setColumns(25);
        passwordField.addKeyListener(passwordListener);

        JPanel timePanel = new JPanel(new BorderLayout());
        timePanel.setPreferredSize(new Dimension(400, 30));
        crackTime = new JLabel("Test Text");
        timePanel.add(crackTime, BorderLayout.CENTER);

        // -- Add components to the main panel --
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(passwordField, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(createReqsPanel(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(timePanel, c);

        this.add(mainPanel);
    }

    private JPanel createReqsPanel() {
        JPanel requirementsPanel = new JPanel();
        requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.Y_AXIS));

        hasLowerLabel = new JLabel(X + " No Lower Case");
        hasLowerLabel.setForeground(RED);

        hasUpperLabel = new JLabel(X + " No Upper Case");
        hasUpperLabel.setForeground(RED);

        hasNumberLabel = new JLabel(X + " No Number");
        hasNumberLabel.setForeground(RED);

        hasSymbol = new JLabel(X + " No Special Characters");
        hasSymbol.setForeground(RED);

        requirementsPanel.add(hasLowerLabel);
        requirementsPanel.add(hasUpperLabel);
        requirementsPanel.add(hasNumberLabel);
        requirementsPanel.add(hasSymbol);

        return requirementsPanel;
    }

    private KeyListener passwordListener = new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            Model model = new Model(passwordField.getText());

            if (model.hasLowerChar()) {
                hasLowerLabel.setText(CHECK + " Has Lower Case");
                hasLowerLabel.setForeground(GREEN);
            } else {
                hasLowerLabel.setText(X + " No Lower Case");
                hasLowerLabel.setForeground(RED);
            }

            if (model.hasUpperChar()) {
                hasUpperLabel.setText(CHECK + " Has Upper Case");
                hasUpperLabel.setForeground(GREEN);
            } else {
                hasUpperLabel.setText(X + " No Upper Case");
                hasUpperLabel.setForeground(RED);
            }

            if (model.hasNumber()) {
                hasNumberLabel.setText(CHECK + " Has Number");
                hasNumberLabel.setForeground(GREEN);
            } else {
                hasNumberLabel.setText(X + " No Number");
                hasNumberLabel.setForeground(RED);
            }

            if (model.hasSymbol()) {
                hasSymbol.setText(CHECK + " Has Special Character");
                hasSymbol.setForeground(GREEN);
            } else {
                hasSymbol.setText(X + " No Special Characters");
                hasSymbol.setForeground(RED);
            }
        }
    };
}
