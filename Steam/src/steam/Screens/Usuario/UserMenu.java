package steam.Screens.Usuario;

import java.awt.*;
import javax.swing.*;
import steam.Screens.Main;

public class UserMenu extends JFrame {

    public UserMenu() {
        initVentana();
        initComponentes();
    }

    private void initVentana() {
        setSize(700, 600);
        setTitle("STEAMTEC | MENU USUARIO");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }//a

    private void initComponentes() {
        panel.setSize(700, 600);
        panel.setLayout(null);

        titulo.setBounds(150, 50, 400, 100);
        titulo.setText("MENU STEAMTEC");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        verCatalogo.setBounds(200, 180, 300, 60);
        verCatalogo.setFont(new Font("Arial", Font.BOLD, 20));
        verCatalogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        verCatalogo.setFocusPainted(false);
        verCatalogo.addActionListener(e -> verCatalogoAction());

        descargar.setBounds(200, 260, 300, 60);
        descargar.setFont(new Font("Arial", Font.BOLD, 20));
        descargar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        descargar.setFocusPainted(false);
        descargar.addActionListener(e -> descargarAction());

        verPerfil.setBounds(200, 340, 300, 60);
        verPerfil.setFont(new Font("Arial", Font.BOLD, 20));
        verPerfil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        verPerfil.setFocusPainted(false);
        verPerfil.addActionListener(e -> verPerfilAction());

        cerrarSesion.setBounds(200, 420, 300, 60);
        cerrarSesion.setFont(new Font("Arial", Font.BOLD, 20));
        cerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cerrarSesion.setFocusPainted(false);
        cerrarSesion.addActionListener(e -> cerrarSesionAction());

        panel.add(titulo);
        panel.add(verCatalogo);
        panel.add(descargar);
        panel.add(verPerfil);
        panel.add(cerrarSesion);
        add(panel);
    }
    //asaas 
    private void verCatalogoAction() {
        // new Catalogo().setVisible(true);
    }

    private void descargarAction() {
        // new Descargas().setVisible(true);
    }

    private void verPerfilAction() {
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
    private final JButton verCatalogo = new JButton("VER CATALOGO");
    private final JButton descargar = new JButton("DESCARGAR");
    private final JButton verPerfil = new JButton("VER PERFIL");
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
        new UserMenu().setVisible(true);
    }
}
