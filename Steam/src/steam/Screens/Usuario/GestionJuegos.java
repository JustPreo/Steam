package steam.Screens.Usuario;

import steam.Steam;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GestionJuegos extends JFrame {

    private final Steam steam;

    public GestionJuegos(Steam steam) {
        this.steam = steam;
        initVentana();
        initComponentes();
    }

    private void initVentana() {
        setSize(700, 600);
        setTitle("STEAMTEC | GESTION DE JUEGOS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }

    private void initComponentes() {
        panel.setSize(700, 600);
        panel.setLayout(null);

        titulo.setBounds(60, 50, 600, 100);
        titulo.setText("GESTION DE JUEGOS");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        agregar.setBounds(200, 180, 300, 60);
        agregar.setFont(new Font("Arial", Font.BOLD, 20));
        agregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        agregar.setFocusPainted(false);
        agregar.addActionListener(e -> agregarJuegoAction());

        actualizarPrecio.setBounds(200, 260, 300, 60);
        actualizarPrecio.setFont(new Font("Arial", Font.BOLD, 20));
        actualizarPrecio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actualizarPrecio.setFocusPainted(false);
        actualizarPrecio.addActionListener(e -> actualizarPrecioAction());

        listar.setBounds(200, 340, 300, 60);
        listar.setFont(new Font("Arial", Font.BOLD, 20));
        listar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        listar.setFocusPainted(false);
        listar.addActionListener(e -> listarJuegosAction());

        panel.add(titulo);
        panel.add(agregar);
        panel.add(actualizarPrecio);
        panel.add(listar);
        add(panel);
    }

    private void agregarJuegoAction() {
        try {
            String tituloJuego = JOptionPane.showInputDialog(this, "Titulo del juego:");
            String genero = JOptionPane.showInputDialog(this, "Genero:");
            char os = JOptionPane.showInputDialog(this, "Sistema Operativo (W/L)").toUpperCase().charAt(0);
            int edad = Integer.parseInt(JOptionPane.showInputDialog(this, "Edad minima:"));
            double precio = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio:"));
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar Imagen del Juego");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(
                    new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif", "bmp"));

            int result = fileChooser.showOpenDialog(this); // Abrir diálogo
            String path = null;
            if (result == JFileChooser.APPROVE_OPTION) {
                path = fileChooser.getSelectedFile().getAbsolutePath();
            } else {
                JOptionPane.showMessageDialog(this, "No se seleccionó imagen, operación cancelada");
                return; // salir de la acción
            }

            if (steam.addGame(tituloJuego, genero, os, edad, precio, path)) {
                JOptionPane.showMessageDialog(this, "Juego agregado correctamente!");
            } else {
                JOptionPane.showMessageDialog(this, "Error: Juego ya existe");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar juego: " + ex.getMessage());
        }
        catch (Exception e)
        {
        JOptionPane.showMessageDialog(this, "Error al agregar juego: " + e.getMessage());
        }
    }

    private void actualizarPrecioAction() {
        try {
            int code = Integer.parseInt(JOptionPane.showInputDialog(this, "Codigo del juego:"));
            double precio = Double.parseDouble(JOptionPane.showInputDialog(this, "Nuevo precio:"));
            if (steam.updatePriceFor(code, precio)) {
                JOptionPane.showMessageDialog(this, "Precio actualizado correctamente!");
            } else {
                JOptionPane.showMessageDialog(this, "Juego no encontrado");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void listarJuegosAction() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("JUEGOS REGISTRADOS:\n");
            steam.games.seek(0);
            while (steam.games.getFilePointer() < steam.games.length()) {
                int code = steam.games.readInt();
                String titulo = steam.games.readUTF();
                String genero = steam.games.readUTF();
                char os = steam.games.readChar();
                int edad = steam.games.readInt();
                double precio = steam.games.readDouble();
                int downloads = steam.games.readInt();
                String imgPath = steam.games.readUTF();
                sb.append(code).append(" | ").append(titulo).append(" | ").append(genero)
                        .append(" | ").append(os).append(" | ").append(edad)
                        .append(" | $").append(precio).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al listar juegos: " + ex.getMessage());
        }
    }

    private final JLabel titulo = new JLabel();
    private final JButton agregar = new JButton("AGREGAR JUEGO");
    private final JButton actualizarPrecio = new JButton("ACTUALIZAR PRECIO");
    private final JButton listar = new JButton("LISTAR JUEGOS");

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
        new GestionJuegos(new Steam()).setVisible(true);
    }

}
