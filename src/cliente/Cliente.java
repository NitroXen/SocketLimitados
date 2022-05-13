/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Cliente {

    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 4999);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            Scanner sc = new Scanner(System.in);
            boolean salir = in.readBoolean();

            while (!salir) {
                String cmd = sc.nextLine();
                out.writeUTF(cmd);
                if (cmd.equals("")) {
                    salir = true;
                }
                System.out.println(in.readUTF() + "\n");
            }
            System.out.println("Bye Bye");
            s.close();

        } catch (IOException ex) {
            System.out.println("Error en el cliente");
        }
    }

}
