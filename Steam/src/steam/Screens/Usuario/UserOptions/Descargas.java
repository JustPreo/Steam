package steam.Screens.Usuario.UserOptions;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import steam.Steam;

public class Descargas extends JFrame {

    private final int userCode;
    private JTable tablaDescargas;
    private DefaultTableModel modeloTabla;

    public Descargas(int userCode) {
        this.userCode = userCode;
        initVentana();
        initComponentes();
        cargarDescargas();
    }

    private void initVentana() {
        setSize(800, 500);
        setTitle("STEAMTEC | MIS DESCARGAS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }

    private void initComponentes() {
        panel.setSize(800, 500);
        panel.setLayout(null);

        JLabel titulo = new JLabel("MI HISTORIAL DE DESCARGAS");
        titulo.setBounds(150, 20, 500, 40);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo);

        // Configuración de la tabla
        String[] columnas = {"ID Descarga", "Juego", "Precio", "Fecha de Descarga"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaDescargas = new JTable(modeloTabla);
        
        // Estilo de la tabla para que coincida con el tema oscuro
        tablaDescargas.setBackground(new Color(30, 60, 110));
        tablaDescargas.setForeground(Color.WHITE);
        tablaDescargas.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaDescargas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tablaDescargas.getTableHeader().setBackground(new Color(15, 32, 65));
        tablaDescargas.getTableHeader().setForeground(Color.WHITE);
        tablaDescargas.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tablaDescargas);
        scrollPane.setBounds(50, 80, 700, 300);
        scrollPane.getViewport().setBackground(new Color(30, 60, 110));
        panel.add(scrollPane);
        
        JButton volver = new JButton("VOLVER AL MENU");
        volver.setBounds(300, 400, 200, 50);
        volver.setFont(new Font("Arial", Font.BOLD, 18));
        volver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        volver.setFocusPainted(false);
        volver.addActionListener(e -> dispose());
        panel.add(volver);

        add(panel);
    }

    private void cargarDescargas() {
        File carpetaDescargas = new File("steam/downloads");
        File[] archivos = carpetaDescargas.listFiles();

        if (archivos == null) {
            JOptionPane.showMessageDialog(this, "El directorio de descargas no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (File archivo : archivos) {
            if (archivo.isFile() && archivo.getName().startsWith("download_")) {
                try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
                    raf.seek(0);
                    int downloadCode = raf.readInt();
                    int clientCodeFromFile = raf.readInt();

                    // Comprueba si la descarga pertenece al usuario que inició sesión
                    if (clientCodeFromFile == this.userCode) {
                        raf.readUTF(); // Omitir nombre de cliente
                        raf.readInt(); // Omitir gameCode
                        String tituloJuego = raf.readUTF();
                        raf.readUTF(); // Omitir path de imagen
                        double precio = raf.readDouble();
                        long fechaMillis = raf.readLong();
                        String fecha = sdf.format(new Date(fechaMillis));
                        
                        // Añadir fila a la tabla
                        modeloTabla.addRow(new Object[]{downloadCode, tituloJuego, String.format("$%.2f", precio), fecha});
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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
