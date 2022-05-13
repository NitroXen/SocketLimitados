/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class HiloCliente extends Thread {

    Socket s;
    DataInputStream in;
    DataOutputStream out;
    Servidor ser;
    boolean salir;

    public HiloCliente(Socket s, Servidor ser) {
        this.s = s;
        this.ser = ser;

        try {
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error en el hilo del cliente");
        }

    }

    public void getSalida() {
        salir = !ser.isConectado();
    }

    @Override
    public void run() {
        try {
            getSalida();
            out.writeBoolean(salir);
            while (!salir) {

                String cmd = in.readUTF();
                if (cmd.equals("")) {
                    salir = true;
                }
                out.writeUTF(cmd);

            }
            salir();
        } catch (IOException ex) {
            System.out.println("Error en el run de hiloCliente");
        }
    }

    public void salir() throws IOException {
        ser.finConexion();
        s.close();
        in.close();
        out.close();
    }

}
