/*Instituto Tecnológico de Costa Rica
Programación Orientada a Objetos
Profesor Yuen Law Wan
Realizado por Santiago Moreno Granados - c.2022040858
Tarea de Hilos y Sockets
I Semestre 2023 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    JFrame frame;
    JLabel blinker;
    JPanel panel;

    ServerSocket server;
    Socket socketCliente;
    DataOutputStream salida;
    DataInputStream entrada;
    
    boolean activo = false;

    public Server() throws IOException{
        frame = new JFrame("Tarea Programada POO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        blinker = new JLabel();
        blinker.setPreferredSize(new Dimension(300, 300));
        blinker.setOpaque(true);
        blinker.setBackground(Color.PINK);


        panel = new JPanel(new BorderLayout());
        panel.add(blinker, BorderLayout.WEST);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        iniciarServer();
        blink();
    }

    private void blink() throws IOException {
        /*método aparte para controlar la recepcion de datos desde el cliente, ya que sin esto
        se creaba un conflicto con el while (true) y solo parpadeaba una vez por cada vez
        que se encendía el botón */
        Thread inputThread = new Thread(() -> {
            while (true) {
                try {
                    entrada = new DataInputStream(socketCliente.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    activo = entrada.readBoolean();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        inputThread.start(); //para manejar la entrada del boton
        while (true) {
            if (activo) {
                try {
                    blinker.setBackground(Color.PINK);
                    Thread.sleep(500);
                    blinker.setBackground(Color.BLACK);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //método para iniciar el servidor en el puerto 8084
    public void iniciarServer(){
        try {
            server = new ServerSocket(8084);
            //acepta el cliente
            socketCliente = server.accept();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}