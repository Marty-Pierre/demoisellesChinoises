import demoisellesChinoises.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Format : java DemoisellesChinoises_clientGFX <IP address> <port number> <game dimension>");
            return;
        }
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        int d = Integer.parseInt(args[2]);
        System.out.println("hostname: "+hostname);
        System.out.println("port: "+port);
        System.out.println("d: "+d);

        try (Socket socket = new Socket(hostname, port)) {// Communication Endpoint for client and server

            System.out.println("Client Started...");

            // DataInputStream to read data from input stream
            //DataInputStream inp=new DataInputStream (cs.getInputStream());
            InputStream input = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);

            // DataOutputStream to write data on output stream
            //DataOutputStream out=new DataOutputStream(cs.getOutputStream());
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            writer.println(d);
            System.out.println("envoie au serveur de la dimension du plateau : "+d);


            Scanner clavier = new Scanner(System.in);
            String[] msg;
            String line;
            System.out.println("Entrez un mouvement de pion i,j,i',j' ou exit pour sortir:");
            line = clavier.nextLine();
            msg = line.split(",");
            System.out.println("commande saisie: "+msg[0]);

            while (!msg[0].equals("exit")) {


                if ((Integer.parseInt(msg[0]) + Integer.parseInt(msg[1]) <= (2 * d + 2)) && (Integer.parseInt(msg[2]) + Integer.parseInt(msg[3]) <= (2 * d + 2))) {
                    writer.println(line);
                    System.out.println("envoie du mouvement "+msg[0]+" "+msg[1]+" "+msg[2]+" "+msg[3]+" ");
                }
                else {
                    System.out.println(msg+": mauvais format");
                }

                System.out.println("Entrez un mouvement de pion i,j,i',j' ou exit pour sortir:");
                line = clavier.nextLine();
                msg = line.split(",");
            }
            writer.println("exit");
            System.out.println("envoie de la commande exit");
            output.close();
            System.out.println("Au revoir !");

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }



    }
}
