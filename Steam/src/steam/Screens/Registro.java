package steam.Screens;

import java.awt.*;
import javax.swing.*;
import java.util.Date;
import com.toedter.calendar.JDateChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import steam.Steam;

public class Registro extends JFrame {
    private Steam steamManager;

    public Registro() {
        try {
            steamManager = new Steam();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al inicializar el sistema: " + e.getMessage(),
                "ERROR CRÍTICO", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        initVentana();
        initComponentes();
    }

    private void initVentana() {
        setSize(900, 700);
        setTitle("STEAMTEC | REGISTRO");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }

    private void initComponentes() {
        panel.setSize(900, 700);
        panel.setLayout(null);

        titulo.setBounds(250, 30, 400, 80);
        titulo.setText("CREAR CUENTA");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        userText.setBounds(50, 100, 300, 30);
        userText.setText("USERNAME:");
        userText.setFont(new Font("Arial", Font.BOLD, 18));
        userText.setForeground(Color.WHITE);

        username.setBounds(50, 135, 300, 40);
        username.setFont(new Font("Arial", Font.PLAIN, 16));

        passText.setBounds(50, 190, 300, 30);
        passText.setText("PASSWORD:");
        passText.setFont(new Font("Arial", Font.BOLD, 18));
        passText.setForeground(Color.WHITE);

        password.setBounds(50, 225, 300, 40);
        password.setFont(new Font("Arial", Font.PLAIN, 16));

        nombreText.setBounds(50, 280, 300, 30);
        nombreText.setText("NOMBRE COMPLETO:");
        nombreText.setFont(new Font("Arial", Font.BOLD, 18));
        nombreText.setForeground(Color.WHITE);

        nombre.setBounds(50, 315, 300, 40);
        nombre.setFont(new Font("Arial", Font.PLAIN, 16));

        nacimientoText.setBounds(50, 370, 300, 30);
        nacimientoText.setText("FECHA DE NACIMIENTO:");
        nacimientoText.setFont(new Font("Arial", Font.BOLD, 18));
        nacimientoText.setForeground(Color.WHITE);

        dateChooser.setBounds(50, 405, 300, 40);
        dateChooser.setFont(new Font("Arial", Font.PLAIN, 16));
        dateChooser.setDateFormatString("dd/MM/yyyy");
        
        tipoUserText.setBounds(50, 460, 300, 30);
        tipoUserText.setText("TIPO DE USUARIO:");
        tipoUserText.setFont(new Font("Arial", Font.BOLD, 18));
        tipoUserText.setForeground(Color.white);

        tipoUsuario.setBounds(50, 495, 300, 40);
        tipoUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        
        tipoUsuario.addItem("NORMAL");
        tipoUsuario.addItem("ADMIN");

        imagenPanel.setBounds(493, 160, 270, 270);
        imagenPanel.setBackground(new Color(255, 255, 255, 50));
        imagenPanel.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        imagenPanel.setLayout(new BorderLayout());

        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagenLabel.setVerticalAlignment(SwingConstants.CENTER);
        imagenLabel.setText("<html><center>SIN<br>IMAGEN</center></html>");
        imagenLabel.setFont(new Font("Arial", Font.BOLD, 16));
        imagenLabel.setForeground(Color.WHITE);
        imagenPanel.add(imagenLabel, BorderLayout.CENTER);

        seleccionarImagen.setBounds(500, 470, 250, 40);
        seleccionarImagen.setFont(new Font("Arial", Font.BOLD, 14));
        seleccionarImagen.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seleccionarImagen.setFocusPainted(false);
        seleccionarImagen.setForeground(new Color(65, 113, 217));
        seleccionarImagen.addActionListener(e -> seleccionarImagenAction());

        crearCuenta.setBounds(200, 560, 200, 45);
        crearCuenta.setFont(new Font("Arial", Font.BOLD, 18));
        crearCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        crearCuenta.setForeground(new Color(50, 168, 82));
        crearCuenta.setFocusPainted(false);
        crearCuenta.addActionListener(e -> crearCuentaAction());

        regresar.setBounds(450, 560, 200, 45);
        regresar.setFont(new Font("Arial", Font.BOLD, 18));
        regresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        regresar.setForeground(Color.red);
        regresar.setFocusPainted(false);
        regresar.addActionListener(e -> regresarAction());

        panel.add(titulo);
        panel.add(userText);
        panel.add(username);
        panel.add(passText);
        panel.add(password);
        panel.add(nombreText);
        panel.add(nombre);
        panel.add(nacimientoText);
        panel.add(dateChooser);
        panel.add(tipoUserText);
        panel.add(tipoUsuario);
        panel.add(imagenPanel);
        panel.add(seleccionarImagen);
        panel.add(crearCuenta);
        panel.add(regresar);
        add(panel);
    }

    private void seleccionarImagenAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Imagen de Perfil");

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes", "jpg", "jpeg", "png", "gif", "bmp"));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                ImageIcon originalIcon = new ImageIcon(selectedFile.getAbsolutePath());
                Image scaledImage = originalIcon.getImage().getScaledInstance(
                        270, 270, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                imagenLabel.setIcon(scaledIcon);
                imagenLabel.setText("");

                imagenSeleccionada = selectedFile;

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al cargar la imagen: " + e.getMessage(),
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String guardarImagen(String username) {
        if (imagenSeleccionada == null) {
            return ""; // Sin imagen
        }

        try {
            // Crear directorio de imágenes si no existe
            File dirImagenes = new File("steam/images");
            if (!dirImagenes.exists()) {
                dirImagenes.mkdirs();
            }

            // Obtener la extensión del archivo original
            String nombreOriginal = imagenSeleccionada.getName();
            String extension = "";
            int i = nombreOriginal.lastIndexOf('.');
            if (i > 0) {
                extension = nombreOriginal.substring(i);
            }

            // Crear nombre único para la imagen
            String nombreDestino = "profile_" + username + "_" + System.currentTimeMillis() + extension;
            Path destino = Paths.get("steam/images/" + nombreDestino);

            // Copiar archivo
            Files.copy(imagenSeleccionada.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

            return destino.toString();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar la imagen: " + e.getMessage(),
                    "ERROR",
                    JOptionPane.WARNING_MESSAGE);
            return "";
        }
    }

    private void crearCuentaAction() {
        try {
            String usernameStr = username.getText().trim();
            String pass = new String(password.getPassword());
            String nombreStr = nombre.getText().trim();
            Date fechaNacimiento = dateChooser.getDate();
            String tipoUser = (String) tipoUsuario.getSelectedItem();

            // Validaciones básicas
            if (usernameStr.isEmpty() || pass.isEmpty() || nombreStr.isEmpty() || fechaNacimiento == null) {
                JOptionPane.showMessageDialog(this,
                        "Debe de llenar todos los campos obligatorios!",
                        "ADVERTENCIA",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar que el username no tenga espacios
            if (usernameStr.contains(" ")) {
                JOptionPane.showMessageDialog(this,
                        "El username no puede contener espacios!",
                        "ERROR DE VALIDACIÓN",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar longitud de password
            if (pass.length() < 4) {
                JOptionPane.showMessageDialog(this,
                        "La contraseña debe tener al menos 4 caracteres!",
                        "ERROR DE VALIDACIÓN",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que la fecha de nacimiento sea válida (no futura)
            if (fechaNacimiento.after(new Date())) {
                JOptionPane.showMessageDialog(this,
                        "La fecha de nacimiento no puede ser futura!",
                        "ERROR DE VALIDACIÓN",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Guardar imagen si fue seleccionada
            String pathImagen = guardarImagen(usernameStr);
            if (imagenSeleccionada != null && pathImagen.isEmpty()) {
                // Si había imagen seleccionada pero no se pudo guardar, preguntar si continuar
                int opcion = JOptionPane.showConfirmDialog(this,
                        "No se pudo guardar la imagen. ¿Desea continuar sin imagen?",
                        "PROBLEMA CON IMAGEN",
                        JOptionPane.YES_NO_OPTION);
                if (opcion != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            long nacimientoLong = fechaNacimiento.getTime();

            // Intentar crear el usuario usando la clase Steam
            boolean usuarioCreado = steamManager.addPlayer(
                    usernameStr, 
                    pass, 
                    nombreStr, 
                    nacimientoLong, 
                    pathImagen, 
                    tipoUser
            );

            if (usuarioCreado) {
                JOptionPane.showMessageDialog(this,
                        "¡Cuenta '" + usernameStr + "' creada exitosamente!\n" +
                        "Tipo de usuario: " + tipoUser + "\n" +
                        (pathImagen.isEmpty() ? "Sin imagen de perfil" : "Con imagen de perfil"),
                        "REGISTRO EXITOSO",
                        JOptionPane.INFORMATION_MESSAGE);

                limpiarCampos();
                new Main().setVisible(true);
                dispose();
                
            } else {
                JOptionPane.showMessageDialog(this,
                        "El username '" + usernameStr + "' ya existe!\n" +
                        "Por favor, elija otro nombre de usuario.",
                        "ERROR DE REGISTRO",
                        JOptionPane.ERROR_MESSAGE);
                username.requestFocus();
                username.selectAll();
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al acceder al sistema de archivos: " + e.getMessage(),
                    "ERROR DEL SISTEMA",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado al crear la cuenta: " + e.getMessage(),
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        username.setText("");
        password.setText("");
        nombre.setText("");
        dateChooser.setDate(null);
        imagenLabel.setIcon(null);
        imagenLabel.setText("<html><center>SIN<br>IMAGEN</center></html>");
        imagenSeleccionada = null;
        tipoUsuario.setSelectedIndex(0);
    }

    private void regresarAction() {
        new Main().setVisible(true);
        dispose();
    }

    private final JLabel titulo = new JLabel();
    private final JLabel userText = new JLabel();
    private final JLabel passText = new JLabel();
    private final JLabel nombreText = new JLabel();
    private final JLabel nacimientoText = new JLabel();
    private final JLabel tipoUserText = new JLabel();
    private final JTextField username = new JTextField();
    private final JPasswordField password = new JPasswordField();
    private final JTextField nombre = new JTextField();
    private final JDateChooser dateChooser = new JDateChooser();
    private final JButton crearCuenta = new JButton("CREAR CUENTA");
    private final JButton regresar = new JButton("REGRESAR");
    private final JButton seleccionarImagen = new JButton("SELECCIONAR IMAGEN DE PERFIL");
    private final JComboBox<String> tipoUsuario = new JComboBox<>();
    private final JPanel imagenPanel = new JPanel();
    private final JLabel imagenLabel = new JLabel();
    private File imagenSeleccionada = null;

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
        SwingUtilities.invokeLater(() -> {
            new Registro().setVisible(true);
        });
    }
}