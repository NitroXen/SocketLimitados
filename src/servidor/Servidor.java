/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.net.*;

/**
 *
 * @author Admin
 */
public class Servidor extends Thread {

    ServerSocket ss;
    Socket s;
    private final int CONEXIONES_LIMITE = 3;
    private int numConexiones = 0;
    private int puerto = 4999;
    private boolean conectado;

    public Servidor() {
        try {
            inicio();
        } catch (IOException ex) {
        }
    }

    public void inicio() throws IOException {
        ss = new ServerSocket(puerto);
        System.out.println("Servidor Conectado");
    }

    public void finConexion() {
        --numConexiones;
    }

    public void flujo() throws IOException {
        do {
            s = ss.accept();
            ++numConexiones;
            conectado = (numConexiones <= CONEXIONES_LIMITE);
            new HiloCliente(s, this).start();
            System.out.println("Cliente Conectado desde " + s.getInetAddress());
            System.out.println(numConexiones);
        } while (true);
    }

    public boolean isConectado() {
        return conectado;
    }

    @Override
    public void run() {
        try {
            flujo();
        } catch (IOException ex) {
        }
    }

    public static void main(String[] args) {
        new Servidor().start();
    }

}
