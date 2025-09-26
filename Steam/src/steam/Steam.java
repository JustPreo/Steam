/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steam;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author user
 */
public class Steam {

    RandomAccessFile codigos;
    RandomAccessFile games;
    RandomAccessFile usuarios;
    File file;

    /*
    Formato:codigos
        juegos int
        clientes int 
        downloads int
     */
 /*
    Formato:games
        int code;
        String titulo
        String genero;
        char sistemaOperativo
        int edadMinima
        double precio
        int contadorDownloads
        String path;//para la foto
        
    
     */
 /*
    Formato: player
        int code
        String username
        String password
        String nombre
        long nacimiento //Fecha en milisegundos
        int contadorDownloads
        string path
        String tipoUsuario //ADMIN O NORMAL
        bool estado --
        
    
     */
    public Steam() {
        try {
            inicializarCarpeta();
            codigos = new RandomAccessFile("steam/codes.stm", "rw");
            games = new RandomAccessFile("steam/games.stm", "rw");
            usuarios = new RandomAccessFile("steam/usuarios.stm", "rw");
            initCodes();

        } catch (IOException e) {
            System.out.println("Error de archivos!");

        }

    }

    private void inicializarCarpeta() {
        file = new File("steam/downloads");
        if (!file.exists()) {
            file.mkdirs();
        }

    }

    public boolean addPlayer(String username, String password, String nombre, long nacimiento, String path, String tipoUsuario) throws IOException {
        //Lo pone al final del archivo
        if (seekUser(username)) {
            return false;
        }

        usuarios.seek(usuarios.length());//Pone al final del archivo
        usuarios.writeInt(getCode());
        usuarios.writeUTF(username);
        usuarios.writeUTF(password);
        usuarios.writeUTF(nombre);
        usuarios.writeLong(nacimiento);
        usuarios.writeInt(0);//Supongo que cantidad de descargas
        usuarios.writeUTF(path);
        usuarios.writeUTF(tipoUsuario);
        usuarios.writeBoolean(true);//Activo o inactivo el usuario

        return true;
    }

    private void initCodes() throws IOException {
        if (codigos.length() == 0) {
            codigos.writeInt(1);//Juegos
            codigos.writeInt(1);//Clientes
            codigos.writeInt(1);//Downloads globales supongo
        }
    }

    public boolean addGame(String titulo, String genero, char OS, int edadMinima, double precio, String path) throws IOException {
        int code = getCodeJuegos();
        if (seekGame(code)) {
            return false;
        }
        games.seek(games.length());//ADD AL FINAL DE TODO
        games.writeInt(code);//cODE
        games.writeUTF(titulo);//TITULO
        games.writeUTF(genero);//GENERO
        games.writeChar(OS);//OS
        games.writeInt(edadMinima);//EDADMINIMA
        games.writeDouble(precio);//PRECIO
        games.writeInt(0);//CONTADORDOWNLOADS
        games.writeUTF(path);//PATH
        return true;

    }

    public boolean seekGame(int code) throws IOException {
        games.seek(0);
        while (games.getFilePointer() < games.length()) {
            Long pos = games.getFilePointer();
            if (games.readInt() == code) {
                games.seek(pos);
                return true;
            }
            games.readUTF();//TITULO
            games.readUTF();//GENERO
            games.readChar();//OS
            games.readInt();//EDADMINIMA
            games.readDouble();//PRECIO
            games.readInt();//CONTADORDOWNLOADS
            games.readUTF();//PATH

        }
        return false;

    }

    
    public boolean downloadGame(int gameCode, int clientCode, char sistemaOperativoCliente) throws IOException {
    // Verificar que el juego exista
    if (!seekGame(gameCode)) {
        System.out.println("El juego no existe");
        return false;
    }
    
    long posInicioGame = games.getFilePointer(); // Guardar pos para luego actualizar contador
    games.readInt(); // code
    String tituloJuego = games.readUTF();
    games.readUTF(); // genero
    char osJuego = games.readChar();
    int edadMinima = games.readInt();
    double precio = games.readDouble();
    long posContadorGame = games.getFilePointer(); // Para actualizar luego
    int contadorGame = games.readInt();
    String pathImagen = games.readUTF(); // ruta de imagen

    // Verificar compatibilidad del sistema operativo
    if (osJuego != sistemaOperativoCliente) {
        System.out.println("El juego no es compatible con el sistema operativo del cliente");
        return false;
    }

    // Verificar que el cliente exista
    if (!seekUserCode(clientCode)) {
        System.out.println("El cliente no existe.");
        return false;
    }

    long posInicioUser = usuarios.getFilePointer(); // Guardar pos para actualizar contador
    usuarios.readInt(); // code
    String username = usuarios.readUTF();
    usuarios.readUTF(); // password
    String nombre = usuarios.readUTF();
    long nacimiento = usuarios.readLong();
    long posContadorUser = usuarios.getFilePointer(); // Para actualizar luego
    int contadorUser = usuarios.readInt();
    usuarios.readUTF(); // path
    usuarios.readUTF(); // tipoUsuario
    boolean estado = usuarios.readBoolean();

    if (!estado) {
        System.out.println("El usuario está inactvio");
        return false;
    }

    // Verificar edad mínima
    long edadMilis = System.currentTimeMillis() - nacimiento;
    int edadAnios = (int) (edadMilis / (1000L * 60 * 60 * 24 * 365));
    if (edadAnios < edadMinima) {
        System.out.println("El usuario no cumple la edad minima requerida (" + edadMinima + ")");
        return false;
    }

    // Si todo se cumple, crear el archivo de descarga
    int downloadCode = getDownloadsGlobales(); // Genera code
    String nombreArchivo = "steam/downloads/download_" + downloadCode + ".stm";
    File fileDescarga = new File(nombreArchivo);

    RandomAccessFile downloadFile = new RandomAccessFile(fileDescarga, "rw");
    long fechaActual = System.currentTimeMillis();

    downloadFile.writeInt(downloadCode);
    downloadFile.writeInt(clientCode);
    downloadFile.writeUTF(nombre);
    downloadFile.writeInt(gameCode);
    downloadFile.writeUTF(tituloJuego);
    downloadFile.writeUTF(pathImagen); // Guardamos el path de la imagen
    downloadFile.writeDouble(precio);
    downloadFile.writeLong(fechaActual);
    downloadFile.close();

    // Actualizar contador de descargas del juego
    games.seek(posContadorGame);
    games.writeInt(contadorGame + 1);

    // Actualizar contador de descargas del usuario
    usuarios.seek(posContadorUser);
    usuarios.writeInt(contadorUser + 1);

    System.out.println("Descarga generada correctamente: " + nombreArchivo);
    System.out.println("Download numero " + downloadCode + " | " + nombre + " descargaste " + tituloJuego + " a $" + precio);
    return true;
}
    
    

    private boolean seekUser(String username) throws IOException {
        usuarios.seek(0);//Principio

        while (usuarios.getFilePointer() < usuarios.length()) {

            Long pos = usuarios.getFilePointer();
            usuarios.readInt();

            if (usuarios.readUTF().equalsIgnoreCase(username)) {
                usuarios.seek(pos);
                return true;
            }
            //Leer resto de cosas
            usuarios.readUTF();
            ////password
            usuarios.readUTF();//nombre
            usuarios.readLong();//nacimiento
            usuarios.readInt();//cantidad descargas
            usuarios.readUTF();//path
            usuarios.readUTF();//tipouser
            usuarios.readBoolean();//Boolean
        }
        return false;

    }
    
    private boolean seekUserCode(int code) throws IOException {//Lo mismo que arriba pero con codigo
    usuarios.seek(0);
    while (usuarios.getFilePointer() < usuarios.length()) {
        long pos = usuarios.getFilePointer();
        int current = usuarios.readInt();
        if (current == code) {
            usuarios.seek(pos);
            return true;
        }
        usuarios.readUTF(); // username
        usuarios.readUTF(); // password
        usuarios.readUTF(); // nombre
        usuarios.readLong(); // nacimiento
        usuarios.readInt(); // contador
        usuarios.readUTF(); // path
        usuarios.readUTF(); // tipo
        usuarios.readBoolean(); // estado
    }
    return false;
}

    public boolean login(String username, String password) throws IOException {
        if (seekUser(username)) {
            usuarios.readUTF();
            usuarios.readUTF();
            //Consume los anteriores al code
            if (usuarios.readUTF().equals(password)) {
                return true;
            }
            System.out.println("Encontro al usuario pero la contra era mala");
        }
        System.out.println("No encuentro al usuario");
        return false;//Por si no lo encontro
    }

    public void addDownloadJugador(String username) throws IOException {
        if (seekUser(username)) {
            usuarios.readInt();//code
            usuarios.readUTF();//username
            usuarios.readUTF();//password
            usuarios.readUTF();//nombre
            usuarios.readLong();//nacimiento
            long pos = usuarios.getFilePointer();
            int down = usuarios.readInt();
            usuarios.seek(pos);
            usuarios.writeInt(down + 1);//Supongo que cantidad de descargas del user
        }
    }

    public void cambiarEstadoJugador(String username) throws IOException {
        if (seekUser(username)) {
            usuarios.skipBytes(4);//Int
            usuarios.readUTF();//username
            usuarios.readUTF();
            ///password
            usuarios.readUTF();//nombre
            usuarios.skipBytes(12);//Int + Long
            usuarios.readUTF();//path
            usuarios.readUTF();//tipouser
            Long pos = usuarios.getFilePointer();
            boolean bool = usuarios.readBoolean();//Boolean
            usuarios.seek(pos);
            usuarios.writeBoolean(!bool);//lo opuesto a bool
        }
    }
    
    
    private int getCode() throws IOException {
        codigos.seek(1);//
        int code = codigos.readInt();
        codigos.seek(0);
        codigos.writeInt(code + 1);
        return code;
    }

    private int getCodeJuegos() throws IOException {
        codigos.seek(0);
        int code = codigos.readInt();
        codigos.seek(0);
        codigos.writeInt(code + 1);
        return code;
    }

    private int getDownloadsGlobales() throws IOException {
        codigos.seek(2);
        int code = codigos.readInt();
        codigos.seek(2);
        codigos.writeInt(code + 1);
        return code;
    }
    
    public char getSistemaOperativo(int code) throws IOException {
    if (seekGame(code)) { // Si lo encuentra, el puntero queda al inicio del registro
        games.readInt();       // code
        games.readUTF();       // título
        games.readUTF();       // género
        return games.readChar(); // sistema operativo
    }
    return 'L'; // Si no lo encuentra , 
}

}
