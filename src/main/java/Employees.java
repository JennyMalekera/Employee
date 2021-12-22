import com.sun.security.auth.login.ConfigFile;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleType;
import oracle.jdbc.OracleTypes;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;


public class Employees {

 public void employees(){}

      public  void getEmployees() throws InvalidAlgorithmParameterException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, ClassNotFoundException {
           try{
               Database data = new Database();
               Connection database = data.dataconnection();
                //Statement stmt = database.createStatement();
               CallableStatement emp = database.prepareCall("{Call SelectAllEmployees(?)}");
               emp.registerOutParameter(1, OracleTypes.CURSOR);

               emp.execute();


                //String sql="select * from employees";
                //ResultSet rs =stmt.executeQuery(sql);
               ResultSet rs = (ResultSet) emp.getObject(1);

                while(rs.next()) {
                    int id = rs.getInt("ID");
                   String username = rs.getString("Name");
                    System.out.println(id + " " + username);
               }
            } catch (Exception e) {
                throw e;
            }
       }


}








