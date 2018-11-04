import java.io.*;
import java.net.*;
import java.util.*;
import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.io.UnsupportedEncodingException;


class Server implements Runnable{
    Socket connectionSocket;
    public static Vector clients = new Vector();

    public Server(Socket s){
        try{
            System.out.println("Client Got Connected");
            connectionSocket = s;
            }catch(Exception e){
                e.printStackTrace();
            }
    }

    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void run(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
            clients.add(writer);
            while(true){
                String data1 = reader.readLine().trim();

                System.out.println("Received : " + decrypt("7BFC2E049406E8306758D2F6060CF1A8" /*Key*/, "RandomInitVector" /*VectorInitValue*/, data1)); //Insert your Key and VectorInitValue according with ALL client

                for (int i = 0; i < clients.size(); i++){
                    try{
                        BufferedWriter bw = (BufferedWriter)clients.get(i);
                        bw.write(data1);
                        bw.write("\r\n");
                        bw.flush();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Cipher extracted() throws NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        return cipher;
    }

    public static void main(String argv[]) throws Exception{
        System.out.println("Threaded Chat Server is Running");
        ServerSocket mysocket = new ServerSocket(5555); //5555 is an example, insert your port according with client config
        while(true){
            Socket sock = mysocket.accept();
            Server server = new Server(sock);
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
    }
}