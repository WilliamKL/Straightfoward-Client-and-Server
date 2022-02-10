import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;

public class TCPServer
{
    public static void main(String argv[]) throws Exception
    {

        String clientSentence; 

        Socket connectionSocket; 

        BufferedReader fromClient; 

        long totalTime = 0; 

        String comparedFile = "Test.txt"; 

        String outputFileName = "receivedFile"; 

        ServerSocket serSock = new ServerSocket(6785); 

        System.out.println("I am waiting for the connection from the Client Side....");

        
        for(int i=0; i<100; i++)
        {
            long fileTime = 0;
            long startTime = System.currentTimeMillis();

            
            connectionSocket = serSock.accept();

            System.out.println("I am starting to receive the " + comparedFile + " file for the " + (i+1) + "th time.");

            
            fromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            
            PrintWriter out = new PrintWriter(outputFileName+(i+1)+ ".txt");

            int lineCounter = 0; 

            
            while(true)
            {
                clientSentence = fromClient.readLine();

                if(clientSentence == null) break;

                out.println(clientSentence);
                lineCounter++;
                System.out.println("I have received: " + lineCounter + " lines");
            }

            
            out.close();

            
            connectionSocket.close();

            long endTime = System.currentTimeMillis();

            fileTime = endTime - startTime;

            System.out.println("The file took " + Math.abs(fileTime) + " milliseconds to be received.");

            totalTime += fileTime;

            System.out.println("I am finishing receiving the " + comparedFile + " file for the " + (i+1) + "th time.");
            System.out.println("\n-----------------------\n");
        }
        System.out.println("I am done receiving files.");

        
        serSock.close();

        int failCount = 0;

        File serverFile = new File(comparedFile); 

        
        for(int i=0; i<100; i++)
        {
            File clientFile = new File(outputFileName + (i+1) + ".txt");
            Scanner serverFileReader = new Scanner(serverFile);
            Scanner clientFileReader = new Scanner(clientFile);

            boolean passedTheTest;
            while(serverFileReader.hasNextLine() && clientFileReader.hasNextLine())
            {
                passedTheTest = serverFileReader.nextLine().equals(clientFileReader.nextLine());

                
                if(!(passedTheTest))
                {
                    System.out.println("File " + outputFileName+(i+1)+ ".txt has error");
                    failCount++;
                    break;
                }
            }
            serverFileReader.close();
            clientFileReader.close();
        }
        System.out.println("The failure rate is " + failCount + "/100"); 
        System.out.println("The average time to receive the file: " + (totalTime/100) + " milliseconds"); 
        System.out.println("I am done");
    }
}
