/*Instituto Tecnológico de Costa Rica
Programación Orientada a Objetos
Profesor Yuen Law Wan
Realizado por Santiago Moreno Granados - c.2022040858
Tarea de Hilos y Sockets
I Semestre 2023 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente implements ActionListener{
    JFrame frame;
    JButton button;
    JPanel panel;

    Socket socket;

    public Cliente(){
        frame = new JFrame("Tarea Programada POO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        button = new JButton("Enceder");
        button.addActionListener(this);
        button.setActionCommand("encender");
        button.setPreferredSize(new Dimension(300, 200));
        button.setBackground(Color.PINK);


        panel = new JPanel(new BorderLayout());
        panel.add(button, BorderLayout.EAST);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        conectar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("encender")){
            button.setText("APAGAR");
            button.setActionCommand("apagar");
            //envia al servidor un valor true para cambiarlo en activo y encender el parpadeo
            try {
                DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
                salida.writeBoolean(true);
            } catch (IOException ex) {
                System.out.println("ERROR");
            }}
            
        else{
            button.setText("ENCENDER");
            button.setActionCommand("encender");
            //envia al servidor un valor false para cambiarlo en activo y apagar el parpadeo
            try {
                DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
                salida.writeBoolean(false);
            } catch (IOException ex) {
                System.out.println("ERROR");
            }
        }
    }

    public void conectar(){ 
        //metodo para conectar el cliente al servidor
        try {
            socket = new Socket("localhost", 8084);//192.4.1.1 o "127.0.0.1" o "localhost"   
        } catch (IOException ex) {   
        }
    }
}