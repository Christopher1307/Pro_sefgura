package dad;

import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class GenerarClaveAES {
    public GenerarClaveAES() {
    }

    public static void main(String[] args) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Clave AES en Base64: " + encodedKey);
    }
}
