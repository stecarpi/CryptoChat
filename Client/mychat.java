import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.io.UnsupportedEncodingException;

public class mychat implements Runnable{
    public JTextField tx;
    public JTextArea ta;
    public String login = "";
    BufferedWriter writer;
    BufferedReader reader;

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

    public mychat(String l){
        login = l;

        JFrame f = new JFrame("my chat");
        f.setSize(400,400);

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        
        tx = new JTextField();
        p1.add(tx, BorderLayout.CENTER);
        
        JButton b1 = new JButton("Send");
        p1.add(b1, BorderLayout.EAST);
        
        ta = new JTextArea();
        p2.add(ta, BorderLayout.CENTER);
        p2.add(p1, BorderLayout.SOUTH);
        
        f.setContentPane(p2);

        try{
            Socket socketClient= new Socket("localhost",5555); //localhost,5555 is an example, insert your server IP and port according with server config!
            writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        }catch(Exception e){
            e.printStackTrace();
        }
    
        b1.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev){
                    String s = login + " : " + tx.getText();
                    tx.setText("");
                    try{
                        writer.write(encrypt("7BFC2E049406E8306758D2F6060CF1A8" /*Key*/, "RandomInitVector" /*VectorInitValue*/, s)); //Insert your Key and VectorInitValue according with Server
                        writer.write("\r\n");
                        writer.flush();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        );
        
        f.setVisible(true);
    }

    public void run(){
        try{
            String serverMsg="";
            while((serverMsg = reader.readLine()) != null){
                System.out.println("from server: " + decrypt("7BFC2E049406E8306758D2F6060CF1A8" /*Key*/, "RandomInitVector" /*VectorInitValue*/, serverMsg)); //Insert your Key and VectorInitValue according with Server
                ta.append(decrypt("7BFC2E049406E8306758D2F6060CF1A8" /*Key*/, "RandomInitVector" /*VectorInitValue*/, serverMsg) + "\n"); //Insert your Key and VectorInitValue according with Server
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}