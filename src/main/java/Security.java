import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

public class Security{





         String Algorithm = "AES/CBC/PKCS5Padding";
         File configFile = new File("src/main/resources/Config.properties");


         public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
             SecretKey key = null;
             try
             {
                 KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                 keyGenerator.init(n);
                 key = keyGenerator.generateKey();
             }catch (Exception ex)
             {
                 throw ex;
             }
             finally {
                 return key;
             }
         }

         public String Encrypt(String input, String PassedKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
                 InvalidAlgorithmParameterException, InvalidKeyException,
                 BadPaddingException, IllegalBlockSizeException, IOException {
             try
             {
                 Cipher cipher = Cipher.getInstance(Algorithm);
                 byte[] decodedKey = Base64.getDecoder().decode(PassedKey);
                 SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
                 int[] keys = new int[]{27, -94, -69, -65, -23, 16, -3, -69, -66, -44, -22, -32, -87, -111, 68, -193};
                 InputStream inputStream = new FileInputStream(configFile);
                 Properties prop = new Properties();
                 prop.load(inputStream);
                 IvParameterSpec Iv = generateIv();
                 cipher.init(Cipher.ENCRYPT_MODE,originalKey ,Iv);
                 byte[] cipherText = cipher.doFinal(input.getBytes());
                 return Base64.getEncoder()
                         .encodeToString(cipherText);
             }
             catch (Exception Ex)
             {
                 throw Ex;
             }
         }

         public String Decrypt(String cipherText,String PassedKey) throws
         NoSuchPaddingException, NoSuchAlgorithmException,
                 InvalidAlgorithmParameterException, InvalidKeyException,
                 BadPaddingException, IllegalBlockSizeException
         {
             Cipher cipher = Cipher.getInstance(Algorithm);
             byte[] decodedKey = Base64.getDecoder().decode(PassedKey);
             SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
             cipher.init(Cipher.DECRYPT_MODE, originalKey, generateIv());
             byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
             return new String(plainText);
         }
         public static IvParameterSpec generateIv()
         {
             //byte[] iv = new byte[16];
             byte[] iv = {87, -77, -126, 107, 24, -25, 113, 36, 49, -75, 100, -28, -7, -78, -74, -93};
             //new SecureRandom().nextBytes(iv);
             return new IvParameterSpec(iv);
         }

}