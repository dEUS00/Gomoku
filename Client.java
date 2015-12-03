import java.io.*;
import java.net.*;

public class Client extends Thread {
    private DataInputStream is = null;
    private Socket socket = null;  
    private DataOutputStream os = null;
    private BufferedReader userIn = null;

    public Client(String addr, int port) {
        try {
            socket = new Socket(addr, port);
            os = new DataOutputStream(socket.getOutputStream());
            is = new DataInputStream(socket.getInputStream());
            userIn = new BufferedReader(new InputStreamReader(System.in));
        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about host: hostname");
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: hostname");
        }

        String input;
        try {
            this.start();
            while (true) {
                input = userIn.readLine();
                os.writeBytes(input + "\n");            
            }
        }
        catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        }
        catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }

    public void run() {
        String responseLine;
        while (true) {
            try {
                responseLine = is.readLine();
                System.out.println("Server: " + responseLine);
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
    } 

    public static void main(String[] args) {
        Client client = new Client(args[0], Integer.parseInt(args[1]));
    }
}