package dad;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Cliente {
    private static final String ALGORITHM = "AES";
    private static final String CLAVE_BASE64 = "bjJH1HNLRmPruDvxKkF9Tg==";
    private static final SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode("bjJH1HNLRmPruDvxKkF9Tg=="), "AES");

    public Cliente() {
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Introduce un mensaje: ");

                String mensaje;
                while((mensaje = stdIn.readLine()) != null) {
                    String mensajeCifrado = cifrar(mensaje);
                    System.out.println("Mensaje original: " + mensaje);
                    System.out.println("Mensaje cifrado: " + mensajeCifrado);
                    out.println(mensajeCifrado);
                    String respuestaCifrada = in.readLine();
                    String respuesta = descifrar(respuestaCifrada);
                    System.out.println("Respuesta: " + respuesta);
                }
            } catch (Throwable var10) {
                try {
                    socket.close();
                } catch (Throwable var9) {
                    var10.addSuppressed(var9);
                }

                throw var10;
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String cifrar(String mensaje) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, secretKey);
        byte[] encryptedBytes = cipher.doFinal(mensaje.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String descifrar(String mensajeCifrado) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(mensajeCifrado);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}