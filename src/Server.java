import ServerConfig.ServerConfigReader;
import ServerLogger.ServerLog;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;




public class Server {
    static final String END_OF_HEADER = "";
    private Server(int port, File file) {
        try {

            ServerSocket server = new ServerSocket(port);
            System.out.println("Server started on " + InetAddress.getLocalHost() + ":" + port);
            System.out.println("Press ctrl+c to stop the server");
            while (true) {
                Socket ss = server.accept();
                new Connection(ss, file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerConfigReader config;
        try {
            config = new ServerConfigReader();
            ServerLog.setup(config.getLogFolder(), config.getLogPattern(), config.getLogOutFormat());
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (config.getLogOutFormat().equals("txt")) ServerLog.fileTxt.close();
                if (config.getLogOutFormat().equals("html")) ServerLog.fileHTML.close();
                System.out.println("Logs closed");
                Connection.conLogger.info("***Server stopped***");
            }));
            new Server(config.getPort(), config.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
