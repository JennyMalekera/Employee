import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;

// connecting database to java application

public class Database {

    public Connection dataconnection() throws InvalidAlgorithmParameterException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, ClassNotFoundException {
        //String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        //String UserName = "SYSTEM";
        //String password = "Terr@2021!";
        Connection c = null;
        try {
            File configFile = new File("src/main/resources/config.properties");
            Properties prop = new Properties();
            InputStream input = new FileInputStream(configFile);
            prop.load(input);

            Security sec = new Security();
            //  SecretKey key = sec.generateKey(128);
            // String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
            String url = sec.Decrypt(prop.get("DatabaseUrl").toString(), prop.get("Key").toString());
            String user = sec.Decrypt(prop.get("UserName").toString(), prop.get("Key").toString());
            String password = sec.Decrypt(prop.get("password").toString(), prop.get("Key").toString());
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            c = DriverManager.getConnection(url, user, password);
            //   System.out.println("SUCCESSFULLY CONNECTED");

        } catch (Exception e) {
            // System.out.println(e.getMessage());
            throw e;
        }
        //System.out.println("successfully connected");
        return c;
    }



}