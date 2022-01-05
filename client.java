import java.io.*;
import java.net.*;
import java.util.*;

public class client {
  public static void main(String[] args) throws Exception {

    // Initialize socket
    Socket socket = new Socket(InetAddress.getByName("localhost"), 5000);

    byte[] contents = new byte[10000];
    // Initialize the FileOutputStream to the output file's full path.
    
    DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    Scanner sc = new Scanner(System.in);
    String line = "";
    line = in.readUTF();
    System.out.println(line);
    while (!line.equals("EOF")) {
      try {
        line = in.readUTF();

        System.out.println(line);

      } catch (IOException i) {
        System.out.println(i);
      }
    }
    
    line = in.readUTF();
    System.out.println(line);
    String input = sc.nextLine();
    out.writeUTF(input);
    FileOutputStream fos = new FileOutputStream("C:/Users/ashish bhardwaj/Downloads/tcp/data" + input);
    BufferedOutputStream bos = new BufferedOutputStream(fos);
    InputStream is = socket.getInputStream();
    // No of bytes read in one read() call
    int bytesRead = 0;
    while ((bytesRead = is.read(contents)) != -1)
      bos.write(contents, 0, bytesRead);
    bos.flush();
   socket.close();
    System.out.println("File saved successfully!\n");
    // out.writeUTF("Client disconnected");
  }
 //socket.close();

}
  
  

