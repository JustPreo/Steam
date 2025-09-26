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
    File file;

    /*
    Formato:
        juegos int
        clientes int 
        downloads int
    
    
     */
    public generadorArchivos() {
        try {
            inicializarCarpeta();
            codigos = new RandomAccessFile("steam/codes.stm", "rw");
            initCodes();

        } catch (IOException e) {
            System.out.println("Error de archivos!");

        }

    }

    public void inicializarCarpeta() {
        file = new File("steam");
        if (!file.exists()) {
            file.mkdir();
        }

    }

    public void initCodes() throws IOException {
        if (codigos.length() == 0) {
            codigos.writeInt(1);//Juegos
            codigos.writeInt(1);//Clientes
            codigos.writeInt(1);//Downloads globales supongo
            
        }
    }

}
