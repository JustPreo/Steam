package steam.Screens;

import java.awt.*;
import javax.swing.*;

public class Loguin extends JFrame {

    public Loguin() {
        initVentana();
        initComponentes();
    }

    private void initVentana() {
        setSize(700, 600);
        setTitle("STEAMTEC | LOGIN");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }

    private void initComponentes() {
        panel.setSize(700, 600);
        panel.setLayout(null);

        titulo.setBounds(150, 50, 400, 100);
        titulo.setText("INICIAR SESION");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        userText.setBounds(200, 150, 300, 30);
        userText.setText("USUARIO:");
        userText.setFont(new Font("Arial", Font.BOLD, 18));
        userText.setForeground(Color.WHITE);

        usuario.setBounds(200, 185, 300, 40);
        usuario.setFont(new Font("Arial", Font.PLAIN, 16));
        usuario.setBackground(new Color(255, 255, 255, 200));
        usuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        passText.setBounds(200, 250, 300, 30);
        passText.setText("CONTRASEÑA:");
        passText.setFont(new Font("Arial", Font.BOLD, 18));
        passText.setForeground(Color.WHITE);

        password.setBounds(200, 285, 300, 40);
        password.setFont(new Font("Arial", Font.PLAIN, 16));
        password.setBackground(new Color(255, 255, 255, 200));
        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        aceptar.setBounds(200, 360, 300, 45);
        aceptar.setFont(new Font("Arial", Font.BOLD, 20));
        aceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        aceptar.setForeground(new Color(50, 168, 82));
        aceptar.setFocusPainted(false);
        aceptar.addActionListener(e -> loginAction());

        regresar.setBounds(200, 420, 300, 45);
        regresar.setFont(new Font("Arial", Font.BOLD, 20));
        regresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        regresar.setForeground(Color.RED);
        regresar.setFocusPainted(false);
        regresar.addActionListener(e -> regresarAction());

        panel.add(titulo);
        panel.add(userText);
        panel.add(usuario);
        panel.add(passText);
        panel.add(password);
        panel.add(aceptar);
        panel.add(regresar);
        add(panel);
    }

    private void loginAction() {
        String user = usuario.getText();
        String pass = new String(password.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe de llenar todos los campos!",
                    "ADVERTENCIA",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (user.equals("admin") && pass.equals("admin")) {
            JOptionPane.showMessageDialog(this,
                    "Bienvenido a STEAMTEC <user>!",
                    "INICIO DE SESION EXITOSO",
                    JOptionPane.INFORMATION_MESSAGE);
            // new ventana().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos!",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void regresarAction() {
        new Principal().setVisible(true);
        dispose();
    }

    private final JLabel titulo = new JLabel();
    private final JLabel userText = new JLabel();
    private final JLabel passText = new JLabel();
    private final JTextField usuario = new JTextField();
    private final JPasswordField password = new JPasswordField();
    private final JButton aceptar = new JButton("INICIAR SESION");
    private final JButton regresar = new JButton("REGRESAR");

    private final JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = new Color(15, 32, 65);
            Color color2 = new Color(30, 60, 110);
            GradientPaint gradient = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    };

    public static void main(String[] args) {

        new Loguin().setVisible(true);
    }
}
