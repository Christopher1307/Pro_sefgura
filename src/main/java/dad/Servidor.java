package dad;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Servidor {
    private static final String ALGORITHM = "AES";
    private static final String CLAVE_BASE64 = "bjJH1HNLRmPruDvxKkF9Tg==";
    private static final SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode("bjJH1HNLRmPruDvxKkF9Tg=="), "AES");

    public Servidor() {
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);

            try {
                System.out.println("Servidor en espera de conexiones...");

                while(true) {
                    try {
                        Socket socket = serverSocket.accept();

                        try {
                            System.out.println("Cliente conectado");
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                            String mensajeCifrado;
                            while((mensajeCifrado = in.readLine()) != null) {
                                String mensaje = descifrar(mensajeCifrado);
                                System.out.println("Mensaje recibido: " + mensaje);
                                String respuesta = "Mensaje recibido: " + mensaje;
                                String respuestaCifrada = cifrar(respuesta);
                                out.println(respuestaCifrada);
                            }
                        } catch (Throwable var11) {
                            if (socket != null) {
                                try {
                                    socket.close();
                                } catch (Throwable var10) {
                                    var11.addSuppressed(var10);
                                }
                            }

                            throw var11;
                        }

                        if (socket != null) {
                            socket.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable var13) {
                try {
                    serverSocket.close();
                } catch (Throwable var9) {
                    var13.addSuppressed(var9);
                }

                throw var13;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String cifrar(String mensaje) throws Exception {
        if (mensaje == null) {
            return null;
        } else {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, secretKey);
            byte[] encryptedBytes = cipher.doFinal(mensaje.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }
    }

    private static String descifrar(String mensajeCifrado) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(mensajeCifrado);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}
