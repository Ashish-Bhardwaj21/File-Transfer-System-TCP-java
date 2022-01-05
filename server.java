import java.net.*;
import java.io.*;
import java.util.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

    public static void main(String[] args) throws IOException {
        // Initialize Sockets
        // Scanner sc = new Scanner(System.in);
        Socket socket = null;
        ServerSocket server = null;

        // public static ServerSocket server;
        System.out.println("Server has been started");
        while (true) {
            try {
                server = new ServerSocket(5000);

                System.out.println("Waiting for client to be connected");
                // creating socket and waiting for client connection
                /// Socket socket = ssock.accept();
                socket = server.accept();
                System.out.println("Client Connected");

                // Stream to receive data from the client through the socket.
                // DataInputStream dataInputStream = new
                // DataInputStream(socket.getInputStream());
                // The InetAddress specification
                InetAddress IA = InetAddress.getByName("localhost");

                // Specify the file
                File f1 = new File("C:/Users/ashish bhardwaj/Downloads/tcp/data");
                File[] files = f1.listFiles();
                ArrayList<String> list = new ArrayList<String>();

                int x = 0;
                for (int i = 0; i < files.length; i++) {
                    if (files[i].canRead()) {
                        list.add(files[i].getName() + "\n");
                        x++;
                    }
                }
                list.add("EOF");
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeUTF(x + " Files found\n");
                for (String s : list) {
                    out.writeUTF(s);

                }
                // System.out.println(sb);
                out.writeUTF(" Enter filename for download ");
                // String fname = sc.nextLine();
                // System.out.println(fname);
                DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                String s = in.readUTF();
                System.out.println("Requested File is " + s);
                File file = new File("C:/Users/ashish bhardwaj/Downloads/tcp/data" + s);
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                // Get socket's output stream
                OutputStream os = socket.getOutputStream();
                // Read File Contents into contents array
                byte[] contents;
                long fileLength = file.length();
                long current = 0;
                // long start = System.nanoTime();
                while (current != fileLength) {
                    int size = 10000;
                    if (fileLength - current >= size)
                        current += size;
                    else {
                        size = (int) (fileLength - current);
                        current = fileLength;
                    }
                    contents = new byte[size];
                    bis.read(contents, 0, size);
                    os.write(contents);
                    System.out.print("Sending file ... " + (current * 100) / fileLength + "% complete!");
                }
                os.flush();
                // File transfer done. Close the socket connection!
                System.out.println("File sent succesfully!\n");

                // ssock.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // socket.close();
            server.close();

        }

    }
}
