package ui;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;

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

    private final Color RED_FG = Color.RED;
    private final Color GREEN_FG = new Color(37, 158, 50);

    private final Color BLUE_BG = new Color(166, 194, 194);
    private final Color RED_BG = new Color(225, 134, 116);
    private final Color GREEN_BG = new Color(149, 194, 138);

    public UserInterface() {
        this.setPreferredSize(new Dimension(500, 220));
        this.setTitle("Password Strength Tester");


        setupPanel();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void setupPanel() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BLUE_BG);

        passwordField = new JPasswordField();
        //passwordField = new JTextField();


        passwordField.setColumns(35);
        passwordField.setMinimumSize(new Dimension(300, 20));
        passwordField.addKeyListener(passwordListener);

        JPanel timePanel = new JPanel(new BorderLayout());
        timePanel.setOpaque(false);
//        timePanel.setPreferredSize(new Dimension(500, 40));
        crackTime = new JLabel("Test Text", JLabel.CENTER);
        timePanel.add(crackTime, BorderLayout.CENTER);

        JLabel title = new JLabel("How Strong is Your Password?", JLabel.CENTER);
        Font font = new Font("SansSerif", Font.ITALIC | Font.BOLD, 22);
        title.setFont(font);

        // -- Add components to the main panel --
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(title, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(passwordField, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(createReqsPanel(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(timePanel, c);

        this.add(mainPanel);
    }

    private JPanel createReqsPanel() {
        JPanel requirementsPanel = new JPanel();
        requirementsPanel.setOpaque(false);
        requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.Y_AXIS));

        hasLowerLabel = new JLabel(X + " No Lower Case");
        hasLowerLabel.setForeground(RED_FG);

        hasUpperLabel = new JLabel(X + " No Upper Case");
        hasUpperLabel.setForeground(RED_FG);

        hasNumberLabel = new JLabel(X + " No Number");
        hasNumberLabel.setForeground(RED_FG);

        hasSymbol = new JLabel(X + " No Special Characters");
        hasSymbol.setForeground(RED_FG);

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
                hasLowerLabel.setForeground(GREEN_FG);
            } else {
                hasLowerLabel.setText(X + " No Lower Case");
                hasLowerLabel.setForeground(RED_FG);
            }

            if (model.hasUpperChar()) {
                hasUpperLabel.setText(CHECK + " Has Upper Case");
                hasUpperLabel.setForeground(GREEN_FG);
            } else {
                hasUpperLabel.setText(X + " No Upper Case");
                hasUpperLabel.setForeground(RED_FG);
            }

            if (model.hasNumber()) {
                hasNumberLabel.setText(CHECK + " Has Number");
                hasNumberLabel.setForeground(GREEN_FG);
            } else {
                hasNumberLabel.setText(X + " No Number");
                hasNumberLabel.setForeground(RED_FG);
            }

            if (model.hasSymbol()) {
                hasSymbol.setText(CHECK + " Has Special Character");
                hasSymbol.setForeground(GREEN_FG);
            } else {
                hasSymbol.setText(X + " No Special Characters");
                hasSymbol.setForeground(RED_FG);
            }

            String text = model.prettyCrackTime();

            if (text.endsWith("years")) {
                mainPanel.setBackground(GREEN_BG);
            } else if (!passwordField.getText().isEmpty()) {
                mainPanel.setBackground(RED_BG);
            } else {
                mainPanel.setBackground(BLUE_BG);
            }

//            crackTime.setText(model.getCrackTime() + " seconds");
            crackTime.setText(model.prettyCrackTime());
        }
    };
}
