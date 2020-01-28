import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class Main {

    public static void main(String args[]) throws Exception {
        ServerSocket ss = new ServerSocket(3333);
        Socket s = ss.accept();
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = "", str2 = "";
        while (!str.equals("stop")) {

            str = din.readUTF();

            switch (str) {

                case "hej":
                    System.out.println("client says: " + str);
                    str2 = "velkommen \n"
                            + "angiv dit valg \n"
                            + "horoskop : for at se dagens horoskop \n"
                            + "læs : for at læse fra fil \n"
                            + "stop : for at stoppe serveren";
                    break;

                case "horoskop":
                    str2 = "Meget taler for at dit forår bliver rigtigt interessant";
                    break;

                case "læs":
                    dout.writeUTF("angiv filnavn");
                    dout.flush();

                    str = din.readUTF();
                    str2 = readLineByLineJava8(str);
                    break;

                default:
                    str2 = " det forstod jeg ikke";
                    break;

            }

            System.out.println("client says: " + str);
            //str2 = br.readLine();  // denne skal selvfølgelig lige ud, da den venter på input i terminalen server -> client

            dout.writeUTF(str2);

            dout.flush();

        }

        din.close();

    }

    private static String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

}
