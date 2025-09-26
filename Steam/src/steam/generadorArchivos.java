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
public class generadorArchivos {

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
    public generadorArchivos() {
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
        file = new File("steam");
        if (!file.exists()) {
            file.mkdir();
        }

    }

    private void initCodes() throws IOException {
        if (codigos.length() == 0) {
            codigos.writeInt(1);//Juegos
            codigos.writeInt(1);//Clientes
            codigos.writeInt(1);//Downloads globales supongo
        }
    }

    private int getCode() throws IOException {
        codigos.seek(0);//Inicio archivo
        int code = codigos.readInt();
        codigos.seek(0);
        codigos.writeInt(code + 1);
        return code;
    }

    public void crearUsuario(String username, String password, String nombre, long nacimiento, String path, char tipoUsuario) throws IOException {
        //Lo pone al final del archivo
        usuarios.seek(usuarios.length());//Pone al final del archivo
        usuarios.writeInt(getCode());
        usuarios.writeUTF(username);
        usuarios.writeUTF(password);
        usuarios.writeUTF(nombre);
        usuarios.writeLong(nacimiento);
        usuarios.writeInt(0);//Supongo que cantidad de descargas
        usuarios.writeUTF(path);
        usuarios.writeChar(tipoUsuario);
        usuarios.writeBoolean(true);//Activo o inactivo el usuario
    }

    private boolean seekUser(String username) throws IOException {
        usuarios.seek(0);//Principio
        long pos = 0;
        while (usuarios.getFilePointer() < usuarios.length()) {
            pos = usuarios.getFilePointer();
            usuarios.readInt();
            if (usuarios.readUTF().equalsIgnoreCase(username)) {
                usuarios.seek(pos);
                return true;
            }
        }
        return false;

    }
    

}
