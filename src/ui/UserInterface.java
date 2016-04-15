package ui;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;

public class UserInterface extends JFrame {

    private JPanel mainPanel;
    private JPasswordField passwordField;

    private JLabel hasLowerLabel;
    private JLabel hasUpperLabel;
    private JLabel hasNumberLabel;
    private JLabel hasSymbol;

    private JLabel crackTime;

    private JLabel fileLabel;

    private double calcsPerSec;

    private JButton showHide;
    private char echoChar;

    private final String X = "[✖]";
    private final String CHECK = "[✔]";

    private final Color RED_FG = new Color(221, 12, 0);
    private final Color GREEN_FG = new Color(0, 108, 4);

    private final Color BLUE_BG = new Color(166, 194, 194);
    private final Color RED_BG = new Color(225, 134, 116);
    private final Color GREEN_BG = new Color(149, 194, 138);
    private final Color ORANGE_BG = new Color(217, 159, 68);

    public UserInterface() {
        this.setPreferredSize(new Dimension(500, 235));
        this.setTitle("Password Strength Tester");
//        this.setLayout(new BorderLayout());
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setupMenu();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        setupTopPanel();
        topPanel.add(mainPanel);

        setupBottomPanel();

        JPanel bottomPanel = setupBottomPanel();
        topPanel.add(bottomPanel);

        this.add(topPanel);

        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void setupMenu() {
        JMenuBar menuBar;
        JMenu menu;

        menuBar = new JMenuBar();
        menuBar.setOpaque(false);
        menuBar.setLayout(new BorderLayout());

        menu = new JMenu("Attempts per sec");
        menu.setMaximumSize(menu.getPreferredSize());
        menu.getAccessibleContext().setAccessibleDescription(
                "Set how many password attempts can be made each second");

        menuBar.add(menu, BorderLayout.LINE_START);

        ButtonGroup group = new ButtonGroup();

        JRadioButtonMenuItem nsa = new JRadioButtonMenuItem("NSA (1 trillion)");
        nsa.setActionCommand("1000000000000");
        nsa.addActionListener(menuListener);
        group.add(nsa);
        menu.add(nsa);

        JRadioButtonMenuItem cluster = new JRadioButtonMenuItem("Cluster (350 billion)");
        cluster.setSelected(true);
        calcsPerSec = 350000000000f;
        cluster.setActionCommand("350000000000");
        cluster.addActionListener(menuListener);
        group.add(cluster);
        menu.add(cluster);

        JRadioButtonMenuItem desktop = new JRadioButtonMenuItem("Desktop (500 million)");
        desktop.setActionCommand("500000000");
        desktop.addActionListener(menuListener);
        group.add(desktop);
        menu.add(desktop);

        JRadioButtonMenuItem hand = new JRadioButtonMenuItem("Manual (0.5)");
        hand.setActionCommand("0.5");
        hand.addActionListener(menuListener);
        group.add(hand);
        menu.add(hand);

        showHide = new JButton("Show Password");
        showHide.setOpaque(true);
        showHide.setContentAreaFilled(false);
        showHide.setBorderPainted(false);
        showHide.setFocusable(false);
        showHide.addActionListener(hideListener);
        menuBar.add(showHide, BorderLayout.LINE_END);

        this.setJMenuBar(menuBar);
    }

    private void setupTopPanel() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BLUE_BG);
        mainPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY));

        passwordField = new JPasswordField();
        echoChar = passwordField.getEchoChar();

        passwordField.setColumns(35);
        passwordField.setMinimumSize(new Dimension(300, 20));
        passwordField.addKeyListener(passwordListener);

        JPanel timePanel = new JPanel(new BorderLayout());
        timePanel.setOpaque(false);
        timePanel.setPreferredSize(new Dimension(380, 60));

        Font timeFont = new Font("SansSerif", Font.BOLD, 16);
        crackTime = new JLabel("< Enter Password >", JLabel.CENTER);
        crackTime.setFont(timeFont);

        timePanel.add(new JLabel("It would take about...", JLabel.LEFT), BorderLayout.PAGE_START);
        timePanel.add(crackTime, BorderLayout.CENTER);
        timePanel.add(new JLabel("...to crack your password", JLabel.RIGHT), BorderLayout.PAGE_END);

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

//        c = new GridBagConstraints();
//        c.gridx = 0;
//        c.gridy = 3;
//        c.anchor = GridBagConstraints.LINE_START;
//        mainPanel.add(createReqsPanel(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(timePanel, c);
    }

    private JPanel setupBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BLUE_BG);
//        panel.setPreferredSize(new Dimension(5, 100));
        panel.add(createReqsPanel(), BorderLayout.LINE_START);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        fileLabel = new JLabel();
        Font font = new Font("SansSerif", Font.BOLD, 16);
        fileLabel.setFont(font);
        fileLabel.setForeground(RED_FG);

        rightPanel.add(fileLabel, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.LINE_END);

        return panel;
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

    private ActionListener menuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            calcsPerSec = Double.parseDouble(e.getActionCommand());

            passwordListener.keyReleased(null);
        }
    };

    private ActionListener hideListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (showHide.getText().startsWith("Show")) {
                showHide.setText("Hide Password");
                passwordField.setEchoChar((char) 0);

            } else {
                showHide.setText("Show Password");
                passwordField.setEchoChar(echoChar);
            }
        }
    };

    private KeyListener passwordListener = new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            Model model = new Model(passwordField.getText(), calcsPerSec);

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

            BigDecimal bd = null;
            try {
                bd = model.getCrackTime();
            } catch (NumberFormatException e1) {
                bd = new BigDecimal("3.154E7");
            }

            if (bd.compareTo(new BigDecimal("3.154E7")) >= 0) {
                mainPanel.setBackground(GREEN_BG);
            }

            else if (bd.compareTo(new BigDecimal("1.2E4")) >= 0) {
                mainPanel.setBackground(ORANGE_BG);
            }

            else if (!passwordField.getText().isEmpty()) {
                mainPanel.setBackground(RED_BG);
            } else {
                mainPanel.setBackground(BLUE_BG);
            }

//            crackTime.setText(model.getCrackTime() + " seconds");
            crackTime.setText(model.prettyCrackTime());

            int commonNumber = model.getCommonRange();

            if (commonNumber < 1) {
                fileLabel.setText("");
            } else {
                fileLabel.setText("In top " + commonNumber + " most used passwords");
            }
        }
    };
}
