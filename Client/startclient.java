import java.util.Random;

public class startclient{
    
    public static void main(String [] args){
        try{
            Random rnd = new Random();
            System.out.println("Hello");
            mychat c = new mychat("Utente " + rnd.nextInt(1000));
            Thread t1 = new Thread(c);
            t1.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}