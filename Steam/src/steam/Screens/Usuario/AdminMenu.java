package steam.Screens.Usuario;

import java.awt.*;
import javax.swing.*;
import steam.Screens.Main;
import steam.Steam;

public class AdminMenu extends JFrame {

    public AdminMenu() {
        initVentana();
        initComponentes();
    }

    private void initVentana() {
        setSize(700, 600);
        setTitle("STEAMTEC | MENU ADMINISTRADOR");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }//assdsd

    private void initComponentes() {
        panel.setSize(700, 600);
        panel.setLayout(null);

        titulo.setBounds(60, 50, 600, 100);
        titulo.setText("MENU ADMINISTRADOR");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        gestionJuegos.setBounds(200, 180, 300, 60);
        gestionJuegos.setFont(new Font("Arial", Font.BOLD, 20));
        gestionJuegos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gestionJuegos.setFocusPainted(false);
        gestionJuegos.addActionListener(e -> gestionJuegosAction());

        gestionJugadores.setBounds(200, 260, 300, 60);
        gestionJugadores.setFont(new Font("Arial", Font.BOLD, 20));
        gestionJugadores.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gestionJugadores.setFocusPainted(false);
        gestionJugadores.addActionListener(e -> gestionJugadoresAction());

        reportes.setBounds(200, 340, 300, 60);
        reportes.setFont(new Font("Arial", Font.BOLD, 20));
        reportes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        reportes.setFocusPainted(false);
        reportes.addActionListener(e -> reportesAction());

        cerrarSesion.setBounds(200, 420, 300, 60);
        cerrarSesion.setFont(new Font("Arial", Font.BOLD, 20));
        cerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cerrarSesion.setFocusPainted(false);
        cerrarSesion.addActionListener(e -> cerrarSesionAction());

        panel.add(titulo);
        panel.add(gestionJuegos);
        panel.add(gestionJugadores);
        panel.add(reportes);
        panel.add(cerrarSesion);
        add(panel);
    }
    //asaas 
    private void gestionJuegosAction() {
        new GestionJuegos(new Steam()).setVisible(true);
        dispose();
    }

    private void gestionJugadoresAction() {
        // new Descargas().setVisible(true);
    }

    private void reportesAction() {
        // new Perfil().setVisible(true);
    }

    private void cerrarSesionAction() {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Esta seguro que desea cerrar sesion?",
                "CERRAR SESION",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "Sesion cerrada exitosamente!",
                    "PROCESO EXITOSO",
                    JOptionPane.INFORMATION_MESSAGE);
            new Main().setVisible(true);
            dispose();
        }
    }

    private final JLabel titulo = new JLabel();
    private final JButton gestionJuegos = new JButton("GESTION DE JUEGOS");
    private final JButton gestionJugadores = new JButton("GESTION DE JUGADORES");
    private final JButton reportes = new JButton("REPORTES");
    private final JButton cerrarSesion = new JButton("CERRAR SESION");

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
        new AdminMenu().setVisible(true);
    }
}
