import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
public class SimpleHTTPServer{
    public static void main(String args[]) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Server Started on 8080.....");
        
        Baseball.setup("A","B");
        
        while(Baseball.getInnings() < 9){
            for(int i = 0; i < 2; i++){
                player cp = Baseball.getCurrentPlayer();
                while(!cp.isOut()){
                    cp.countRuns();
                    Baseball.printHTML("html/Game.html",Baseball.generateHTML());
                    Baseball.callCommand(getCommand(server));
                }
                cp.reset();
                Baseball.switchCurrentPlayer();
            }
            Baseball.addInning();
        }
    } 

    public static String getCommand(ServerSocket server) throws IOException{
        String res = "";
        while(true){
            try(Socket clientSocket = server.accept()){
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line = reader.readLine();
                int content = 0;
                while(!line.isEmpty() && line != null){
                    if(line.contains("Content-Length:")){
                        try{
                            content = Integer.parseInt(line.substring(line.length()-1));
                        }catch(Exception e){ System.err.println("Could not parse int: " + line.substring(line.length()-1)); }
                    }
                    line = reader.readLine();
                }
                System.out.println(content);
                
                for(int i = 0; i < content; i++){
                    res += (char)reader.read();
                }
                String resp = "HTTP/1.1 200 OK\r\n\r\n";
                clientSocket.getOutputStream().write(resp.getBytes("UTF-8"));       
                if(res.length() > 0){
                    break;
                }
            }
        } 
        return res;
    }
}
