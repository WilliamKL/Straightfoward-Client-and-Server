
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.*;
import java.net.*;
public class TCPClient
{
   public static void main(String argv[]) throws Exception
   {

       String fileName = "Test.txt"; 

       System.out.println("Looking for " + fileName + " file....");
       System.out.println("\n-----------------------\n");

       File clientFile = new File(fileName); 

       long totalTime = 0; 

       
       for (int i=0; i<100; i++)
       {
           long fileTime = 0;

           long startTime = System.currentTimeMillis(); 

           Scanner clientFileReader = new Scanner(fileName); 

           
           Socket clientSocket = new Socket("168.26.118.221", 6785); 
           
           System.out.println("I am connecting to the Server side: " + clientSocket.getLocalAddress());

           System.out.println("\nSending " + clientFile + " file for the " + (i+1) + "th time");

           System.out.println("File transmission start time: " + startTime); 

          
           DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());

           
           while(clientFileReader.hasNextLine())
           {
               toServer.writeBytes(clientFileReader.nextLine() + "\n");
           }

           clientFileReader.close(); 

           clientSocket.close(); 

           long endTime = System.currentTimeMillis(); 

           System.out.println("File transmission end time: " + endTime);

           fileTime = endTime - startTime;

           System.out.println("The file took " + fileTime + " milliseconds");

           totalTime += fileTime; 

           System.out.println("The " + clientFile + " file sent " + (i+1) + " times");

           System.out.println("\n-----------------------\n");
       }
       System.out.println("The total time to send the file 100 times: " + totalTime + " milliseconds"); 
       System.out.println("The average time to send the file: " + totalTime/100 + "." + totalTime%100 + " milliseconds"); 
       System.out.println("I am done");
   }
}
