/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpsocket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class Tela extends javax.swing.JFrame {
    
    private Socket socket, socketArquivo;
    
    private DataOutputStream saida;
    private DataInputStream entrada;
    
    private DataOutputStream saidaArquivo;
    private DataInputStream entradaArquivo;
    
    private String nome = "";
    
    public Tela(Socket socket, Socket socketArquivo, String nome) {
        initComponents();
        
        this.nome = nome;
        this.socket = socket;
        this.socketArquivo = socketArquivo;
        
        try {
            saida = new DataOutputStream(socket.getOutputStream());
            entrada = new DataInputStream(socket.getInputStream());
            
            saidaArquivo = new DataOutputStream(socketArquivo.getOutputStream());
            entradaArquivo = new DataInputStream(socketArquivo.getInputStream());
            
            recebeMensagem();
            recebeArquivo();
        } catch (IOException erro) {
            System.out.println("Erro ao iniciar o socket : " + erro.getMessage());
        }
        
    }
    
    public Tela() {
        initComponents();
        
        this.nome = JOptionPane.showInputDialog("Nome: ");
        
        try {
            this.socket = new Socket("127.0.0.1", 1500);
            this.socketArquivo = new Socket("127.0.0.1", 3000);
            
            saida = new DataOutputStream(socket.getOutputStream());
            entrada = new DataInputStream(socket.getInputStream());
            
            saidaArquivo = new DataOutputStream(socketArquivo.getOutputStream());
            entradaArquivo = new DataInputStream(socketArquivo.getInputStream());
            
            recebeMensagem();
            recebeArquivo();
        } catch (IOException erro) {
            System.out.println("Erro ao iniciar o socket : " + erro.getMessage());
        }
        
    }
    
    public void recebeMensagem() {
       new Thread() {
            public void run() {
                while (true) {
                    try {
                        String mensagem = entrada.readUTF();
                        txtChat.append(mensagem + "\n");
                        if (!mensagem.contains(nome + " : ")) {
                            //salvarArquivo(mensagem);
                        }
                    } catch (IOException erro) {
                        System.out.println("Erro ao receber mensagem : " + erro.getMessage());
                    }
                }
            }
        }.start();
    }
    
    public void recebeArquivo() {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        
                        System.out.println("Nome Arquivo (Recebe Arquivo) : " + entradaArquivo.readUTF());
                        
                        FileReader in = new FileReader(entradaArquivo.readUTF());
                        FileWriter out = new FileWriter(entradaArquivo.readUTF());
         
                        int c;
                        while ((c = in.read()) != -1) {
                           out.write(c);
                        }
                        
                        in.close();
                        out.close();
                        
                    } catch (IOException erro) {
                        System.out.println("Erro ao receber mensagem : " + erro.getMessage());
                    }
                }
            }
        }.start();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEnviar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtChat = new javax.swing.JTextArea();
        txtMensagem = new javax.swing.JTextField();
        btnCarregarArq = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        txtChat.setColumns(20);
        txtChat.setRows(5);
        jScrollPane1.setViewportView(txtChat);

        btnCarregarArq.setText("Carregar Arquivo");
        btnCarregarArq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarregarArqActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtMensagem)
                        .addGap(18, 18, 18)
                        .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCarregarArq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCarregarArq, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        try {
            String mensagem = txtMensagem.getText();
            saida.writeUTF(nome + " : " + mensagem);
            //txtChat.append(nome + " : " + mensagem + "\n");
        } catch (IOException erro) {
            System.out.println("Erro ao enviar/receber mensagem! : " + erro.getMessage());
        }
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnCarregarArqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarregarArqActionPerformed
        
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    File selectedFile = jfc.getSelectedFile();
                    String nomeArquivo = selectedFile.getName();
                    
                    FileInputStream in = new FileInputStream(selectedFile);
                    FileOutputStream out = new FileOutputStream(nomeArquivo);
                                       
                    int c;
                    while ((c = in.read()) != -1) {
                        out.write(c);
                    }
                    
                    in.close();
                    out.close();
                    
                    saidaArquivo.writeUTF(nomeArquivo);
                    
                    saida.writeUTF(nome + " (Arquivo) : " + nomeArquivo);
                    
                    System.out.println("Nome Arquivo (Carregar) : " + nomeArquivo);
                } catch (IOException ex) {
                    System.out.println("Erro ao upar arquivo : " + ex.getMessage());
                }
        }
    }//GEN-LAST:event_btnCarregarArqActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tela().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCarregarArq;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtChat;
    private javax.swing.JTextField txtMensagem;
    // End of variables declaration//GEN-END:variables
}
