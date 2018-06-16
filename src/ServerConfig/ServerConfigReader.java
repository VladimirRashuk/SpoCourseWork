package ServerConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfigReader {

    private InputStream inputStream;
    private int Port;
    private String FileName;
    private String DocFolder;
    private String Folder;
    private String LogFolder;
    private String LogPattern;
    private String LogOutFormat;

    public ServerConfigReader() throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }


            String portTemp = prop.getProperty("port");
            Port = Integer.parseInt(portTemp);
            FileName = prop.getProperty("fileName");
            DocFolder = prop.getProperty("docFolder");
            Folder = prop.getProperty("Folder");
            LogFolder = prop.getProperty("logFolder");

            LogPattern = prop.getProperty("logPattern");
            LogOutFormat = prop.getProperty("logOutFormat");

            checkExists(getLogFolder());

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            assert inputStream != null;
            inputStream.close();
        }


    }


    private void checkExists(File logFolder) {
        if (!(logFolder.exists())) {
            if (!(new File(logFolder.getParent()).exists()))
                if ((new File(logFolder.getParent())).mkdir()) System.out.println("Doc Directory Created");
            if (logFolder.mkdir()) System.out.println("Log Directory Created");
        }

    }

    public File getFile() {
        return new File(DocFolder + File.separator + getFolder() + File.separator + FileName);
    }

    public int getPort() {
        return Port;
    }

    public String getFileName() {
        return FileName;
    }

    public String getFolder() {
        return Folder;
    }

    public File getLogFolder() {
        return new File(DocFolder + File.separator + LogFolder);
    }

    public String getLogPattern() {
        return LogPattern;
    }

    public String getLogOutFormat() {
        return LogOutFormat;
    }
}
