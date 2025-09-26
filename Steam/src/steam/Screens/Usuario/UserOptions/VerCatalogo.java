package steam.Screens.Usuario.UserOptions;

import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import steam.Steam;

public class VerCatalogo extends JFrame {
    private Steam steam;
    private DefaultTableModel tableModel;
    
    public VerCatalogo() {
        this.steam = new Steam();
        initVentana();
        initComponentes();
        cargarCatalogo();
    }
    
    private void initVentana() {
        setSize(900, 700);
        setTitle("STEAMTEC | CATALOGO DE JUEGOS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }
    
    private void initComponentes() {
        panel.setSize(900, 700);
        panel.setLayout(null);
        
        titulo.setBounds(250, 20, 400, 60);
        titulo.setText("CATALOGO DE JUEGOS");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        String[] columnas = {"Codigo", "Titulo", "Genero", "S.O.", "Edad Min.", "Precio", "Downloads"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(tableModel);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabla.setRowHeight(25);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(50, 100, 800, 450);
        
        btnVolver.setBounds(450, 580, 200, 50);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 18));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setFocusPainted(false);
        btnVolver.setForeground(Color.red);
        btnVolver.addActionListener(e -> volverAction());
        
        btnRefrescar.setBounds(240, 580, 200, 50);
        btnRefrescar.setFont(new Font("Arial", Font.BOLD, 18));
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setForeground(new Color(34, 189, 67));
        btnRefrescar.addActionListener(e -> {
            cargarCatalogo();
            JOptionPane.showMessageDialog(this, "Catalogo actualizado!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        });
        
        panel.add(titulo);
        panel.add(scrollPane);
        panel.add(btnVolver);
        panel.add(btnRefrescar);
        add(panel);
    }
    
    private void cargarCatalogo() {
        try {
            tableModel.setRowCount(0);
            
            RandomAccessFile games = new RandomAccessFile("steam/games.stm", "r");
            games.seek(0);
            
            while (games.getFilePointer() < games.length()) {
                int code = games.readInt();
                String titulo = games.readUTF();
                String genero = games.readUTF();
                char os = games.readChar();
                int edadMinima = games.readInt();
                double precio = games.readDouble();
                int downloads = games.readInt();
                games.readUTF(); // path imagen
                
                Object[] fila = {code, titulo, genero, os, edadMinima, "$" + String.format("%.2f", precio), downloads};
                tableModel.addRow(fila);
            }
            
            games.close();
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar el catalogo: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void volverAction() {
        dispose();
    }
    
    private final JLabel titulo = new JLabel();
    private JTable tabla;
    private JScrollPane scrollPane;
    private final JButton btnVolver = new JButton("VOLVER");
    private final JButton btnRefrescar = new JButton("REFRESCAR");
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
        new VerCatalogo().setVisible(true);
    }
}
