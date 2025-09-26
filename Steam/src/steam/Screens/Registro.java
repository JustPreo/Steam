package steam.Screens;

import java.awt.*;
import javax.swing.*;

public class Registro extends JFrame {

    public Registro() {
        initVentana();
        initComponentes();
    }

    private void initVentana() {
        setSize(700, 600);
        setTitle("STEAMTEC | REGISTRO");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }

    private void initComponentes() {
        panel.setSize(700, 600);
        panel.setLayout(null);

        titulo.setBounds(150, 50, 400, 100);
        titulo.setText("CREAR CUENTA");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        userText.setBounds(200, 150, 300, 30);
        userText.setText("USUARIO:");
        userText.setFont(new Font("Arial", Font.BOLD, 18));
        userText.setForeground(Color.WHITE);

        usuario.setBounds(200, 185, 300, 40);
        usuario.setFont(new Font("Arial", Font.PLAIN, 16));

        passText.setBounds(200, 250, 300, 30);
        passText.setText("CONTRASEÑA:");
        passText.setFont(new Font("Arial", Font.BOLD, 18));
        passText.setForeground(Color.WHITE);

        password.setBounds(200, 285, 300, 40);
        password.setFont(new Font("Arial", Font.PLAIN, 16));

        tipoUserText.setBounds(200, 340, 300, 30);
        tipoUserText.setText("TIPO DE USUARIO:");
        tipoUserText.setFont(new Font("Arial", Font.BOLD, 18));
        tipoUserText.setForeground(Color.WHITE);

        tipoUsuario.setBounds(200, 375, 300, 40);
        tipoUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        tipoUsuario.setBackground(new Color(255, 255, 255, 200));

        tipoUsuario.addItem("Normal");
        tipoUsuario.addItem("Administrador");

        crearCuenta.setBounds(200, 430, 300, 45);
        crearCuenta.setFont(new Font("Arial", Font.BOLD, 20));
        crearCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        crearCuenta.setForeground(new Color(50, 168, 82));
        crearCuenta.setFocusPainted(false);
        crearCuenta.addActionListener(e -> crearCuentaAction());

        regresar.setBounds(200, 490, 300, 45);
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
        panel.add(tipoUserText);
        panel.add(tipoUsuario);
        panel.add(crearCuenta);
        panel.add(regresar);
        add(panel);
    }

    //aa
    private void crearCuentaAction() {
        String user = usuario.getText();
        String pass = new String(password.getPassword());
        String tipoUser = (String) tipoUsuario.getSelectedItem();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe de llenar todos los campos",
                    "ADVERTENCIA",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Cuenta '" + user + "' creada exitosamente!\n",
                "REGISTRO EXITOSO",
                JOptionPane.INFORMATION_MESSAGE);

        usuario.setText("");
        password.setText("");
        tipoUsuario.setSelectedIndex(0);

        new Principal().setVisible(true);
        dispose();
    }

    private void regresarAction() {
        new Principal().setVisible(true);
        dispose();

    }

    private final JLabel titulo = new JLabel();
    private final JLabel userText = new JLabel();
    private final JLabel passText = new JLabel();
    private final JLabel tipoUserText = new JLabel();
    private final JTextField usuario = new JTextField();
    private final JPasswordField password = new JPasswordField();
    private final JComboBox<String> tipoUsuario = new JComboBox<>();
    private final JButton crearCuenta = new JButton("CREAR CUENTA");
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
        new Registro().setVisible(true);
    }
}
