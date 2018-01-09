package util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Upload {
    public static void upload(String absolutePath,String filename) {
        String user = "root";
        String pass = "S104881472a";
        String host = "116.196.76.185";

        Connection con = new Connection(host);
        try {
            con.connect();
            boolean isAuthed = con.authenticateWithPassword(user, pass);
            SCPClient scpClient = con.createSCPClient();
            scpClient.put(absolutePath, "/www/django-project/media/product");
            Session session = con.openSession();
            session.execCommand("chmod -R 777  /www/django-project/media/product"+filename);
            System.out.println("Here is some information about the remote host:");
            InputStream stdout = new StreamGobbler(session.getStdout());

            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }
            session.close();
            con.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
