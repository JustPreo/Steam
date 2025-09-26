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

    private void initCodes() throws IOException {
        if (codigos.length() == 0) {
            codigos.writeInt(1);//Juegos
            codigos.writeInt(1);//Clientes
            codigos.writeInt(1);//Downloads globales supongo
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

    public void crearUsuario(String username, String password, String nombre, long nacimiento, String path, String tipoUsuario) throws IOException {
        //Lo pone al final del archivo
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
    
    

}
