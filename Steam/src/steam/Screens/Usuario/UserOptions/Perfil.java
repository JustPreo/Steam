package steam.Screens.Usuario.UserOptions;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import steam.Steam;

public class Perfil extends JFrame {

    private final int userCode;
    private final Steam steam;

    public Perfil(int userCode) {
        this.userCode = userCode;
        this.steam = new Steam(); // Instancia para manejar los archivos binarios
        initVentana();
        initComponentes();
        cargarDatosPerfil();
    }

    private void initVentana() {
        setSize(500, 550);
        setTitle("STEAMTEC | MI PERFIL");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }

    private void initComponentes() {
        panel.setSize(500, 550);
        panel.setLayout(null);

        // Titulo
        titulo.setBounds(50, 20, 400, 50);
        titulo.setText("MI PERFIL DE JUGADOR");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo);

        // Labels para mostrar la informacion
        int yPos = 100;
        int yGap = 40;
        Font labelFont = new Font("Arial", Font.BOLD, 16);

        // Username
        usernameLabel.setBounds(50, yPos, 400, 30);
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel);
        yPos += yGap;

        // Nombre
        nombreLabel.setBounds(50, yPos, 400, 30);
        nombreLabel.setFont(labelFont);
        nombreLabel.setForeground(Color.WHITE);
        panel.add(nombreLabel);
        yPos += yGap;

        // Codigo
        codigoLabel.setBounds(50, yPos, 400, 30);
        codigoLabel.setFont(labelFont);
        codigoLabel.setForeground(Color.WHITE);
        panel.add(codigoLabel);
        yPos += yGap;
        
        // Nacimiento
        nacimientoLabel.setBounds(50, yPos, 400, 30);
        nacimientoLabel.setFont(labelFont);
        nacimientoLabel.setForeground(Color.WHITE);
        panel.add(nacimientoLabel);
        yPos += yGap;

        // Descargas
        descargasLabel.setBounds(50, yPos, 400, 30);
        descargasLabel.setFont(labelFont);
        descargasLabel.setForeground(Color.WHITE);
        panel.add(descargasLabel);
        yPos += yGap;

        // Tipo Usuario
        tipoUsuarioLabel.setBounds(50, yPos, 400, 30);
        tipoUsuarioLabel.setFont(labelFont);
        tipoUsuarioLabel.setForeground(Color.WHITE);
        panel.add(tipoUsuarioLabel);
        yPos += yGap;

        // Estado
        estadoLabel.setBounds(50, yPos, 400, 30);
        estadoLabel.setFont(labelFont);
        estadoLabel.setForeground(Color.WHITE);
        panel.add(estadoLabel);
        yPos += yGap;

        // Boton de volver
        volver.setBounds(150, 420, 200, 50);
        volver.setFont(new Font("Arial", Font.BOLD, 18));
        volver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        volver.setFocusPainted(false);
        volver.addActionListener(e -> dispose()); // Cierra la ventana actual
        panel.add(volver);

        add(panel);
    }

    private void cargarDatosPerfil() {
        try {
            // Se posiciona en el registro del usuario correspondiente
            if (steam.seekUserCode(userCode)) {
                // Lee los datos en el orden exacto en que se escribieron
                /*int code = steam.usuarios.readInt();
                String username = steam.usuarios.readUTF();
                steam.usuarios.readUTF(); // Omitir password por seguridad
                String nombre = steam.usuarios.readUTF();
                long nacimientoMillis = steam.usuarios.readLong();
                int contadorDownloads = steam.usuarios.readInt();
                steam.usuarios.readUTF(); // Omitir path
                String tipoUsuario = steam.usuarios.readUTF();
                boolean estado = steam.usuarios.readBoolean();

                // Formatear fecha
                SimpleDateFormat sdf = new SimpleDateFormat("dd / MMMM / yyyy");
                String fechaNacimiento = sdf.format(new Date(nacimientoMillis));

                // Asignar datos a los labels
                usernameLabel.setText("Username: " + username);
                nombreLabel.setText("Nombre Completo: " + nombre);
                codigoLabel.setText("Código de Jugador: " + code);
                nacimientoLabel.setText("Fecha de Nacimiento: " + fechaNacimiento);
                descargasLabel.setText("Juegos Descargados: " + contadorDownloads);
                tipoUsuarioLabel.setText("Tipo de Cuenta: " + tipoUsuario);
                estadoLabel.setText("Estado: " + (estado ? "ACTIVO" : "INACTIVO"));*/

            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar el perfil del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al leer los datos del perfil.", "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private final JLabel titulo = new JLabel();
    private final JLabel usernameLabel = new JLabel("Cargando...");
    private final JLabel nombreLabel = new JLabel("Cargando...");
    private final JLabel codigoLabel = new JLabel("Cargando...");
    private final JLabel nacimientoLabel = new JLabel("Cargando...");
    private final JLabel descargasLabel = new JLabel("Cargando...");
    private final JLabel tipoUsuarioLabel = new JLabel("Cargando...");
    private final JLabel estadoLabel = new JLabel("Cargando...");
    private final JButton volver = new JButton("VOLVER AL MENU");

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
}

