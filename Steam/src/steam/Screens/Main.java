package steam.Screens;

import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {

    public Main() {
        initVentana();
        initComponentes();
    }

    private void initVentana() {
        setSize(700, 600);
        setTitle("STEAMTEC");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }

    private void initComponentes() {
        panel.setSize(700, 600);
        panel.setLayout(null);

        titulo.setBounds(150, 50, 400, 100);
        titulo.setText("STEAMTEC");
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        // aaaaa
        iniciarSesion.setBounds(250, 200, 200, 50);
        iniciarSesion.setFont(new Font("Arial", Font.BOLD, 20));
        iniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iniciarSesion.setForeground(Color.BLACK);
        iniciarSesion.setFocusPainted(false);
        iniciarSesion.addActionListener(e -> iniciarSesionAction());

        registrarse.setBounds(250, 280, 200, 50);
        registrarse.setFont(new Font("Arial", Font.BOLD, 20));
        registrarse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registrarse.setForeground(Color.BLACK);
        registrarse.setFocusPainted(false);
        registrarse.addActionListener(e -> registrarseAction());

        salir.setBounds(250, 360, 200, 50);
        salir.setFont(new Font("Arial", Font.BOLD, 20));
        salir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        salir.setForeground(Color.BLACK);
        salir.setFocusPainted(false);
        salir.addActionListener(e -> salirAction());

        panel.add(titulo);
        panel.add(iniciarSesion);
        panel.add(registrarse);
        panel.add(salir);
        add(panel);
    }

    private void iniciarSesionAction() {
        new Loguin().setVisible(true);
        dispose();
    }

    private void registrarseAction() {
        new Registro().setVisible(true);
        dispose();
    }

    private void salirAction() {
        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Esta seguro que desea salir?", 
            "CONFIRMAR SALIDA", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Nos vemos inge!", "STEAMTEC", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            System.exit(0);
        }
    }

    private final JLabel titulo = new JLabel();
    private final JButton iniciarSesion = new JButton("INICIAR SESION");
    private final JButton registrarse = new JButton("REGISTRARSE");
    private final JButton salir = new JButton("SALIR");
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
        new Main().setVisible(true);
    }
}
