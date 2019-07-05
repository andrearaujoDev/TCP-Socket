package tcpsocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    static List<Socket> listSocketMsg = new ArrayList<>();
    static List<Socket> listSocketArquivos = new ArrayList<>();
    
    public static void main(String[] args) {
        int i = 0;
        try{
            ServerSocket server = new ServerSocket(1500);
            ServerSocket serverArquivo = new ServerSocket(3000);

            while(true){
                
                Socket socket = server.accept();
                listSocketMsg.add(socket);
                recebeMensagem(listSocketMsg.get(i));
                
                Socket socketArquivo = serverArquivo.accept();
                listSocketArquivos.add(socketArquivo);
                recebeArquivo(listSocketArquivos.get(i));
                
                System.out.println("Cliente conectado!");
                i++;
                
            }
            
            
            
        }catch(IOException erro){
            System.out.println("Erro na comunicação : " + erro.getMessage());
        }
    }
    
    public  static void recebeMensagem(Socket s) throws IOException {
        DataOutputStream saida = new DataOutputStream(s.getOutputStream());
        DataInputStream entrada = new DataInputStream(s.getInputStream());
        
       new Thread() {
            public void run() {
                try {
                    while(true){
                        String msg = entrada.readUTF();
                        enviandoMensagens(saida,msg);
                    }
                } catch (IOException erro) {
                    System.out.println("Erro ao receber mensagem : " + erro.getMessage());
                }
                
            }
        }.start();
    }
    
    public static void recebeArquivo(Socket socketArq) throws IOException {
        DataOutputStream saida = new DataOutputStream(socketArq.getOutputStream());
        DataInputStream entrada = new DataInputStream(socketArq.getInputStream());
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        
                        FileReader in = new FileReader(entrada.readUTF());
                        FileWriter out = new FileWriter(entrada.readUTF());
                        
                        int c;
                        while ((c = in.read()) != -1) {
                           out.write(c);
                        }
                        
                        enviandoArquivos(saida,entrada.readUTF());
                        
                        in.close();
                        out.close();
                        
                    } catch (IOException erro) {
                        System.out.println("Erro ao receber mensagem : " + erro.getMessage());
                    }
                }
            }
        }.start();
    }
    
    public static void enviandoMensagens(DataOutputStream saida , String mensagem) throws IOException{
        for(Socket s : listSocketMsg){
            saida = new DataOutputStream(s.getOutputStream());
            saida.writeUTF(mensagem);
        }
    }
    
    public static void enviandoArquivos(DataOutputStream saida , String mensagem) throws IOException{
        for(Socket s : listSocketArquivos){
            saida = new DataOutputStream(s.getOutputStream());
            saida.writeUTF(mensagem);
        }
    }
}
