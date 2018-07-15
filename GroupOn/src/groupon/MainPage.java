/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupon;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.Normalizer.Form;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author OM SHUBHAM
 */
public class MainPage extends javax.swing.JFrame {

    /**
     * Creates new form MainPage
     */
    public MainPage() {
        initComponents();
    }
    
    
    String port = null;
    String ipaddress = null;
    Socket soc;
    DataInputStream din = null;
    DataOutputStream dout = null;
    String LoginName;
    String sendto = "";

    public MainPage(String IPaddress, String Port, String login_name) throws IOException {
          initComponents();
        ChatHistory.setEditable(false);
        this.setTitle(login_name + " : Online");
        AvailableUser.clear();
        port = Port;
        msgBox.setText(null);
        ipaddress = IPaddress;
        LoginName = login_name;
        soc = new Socket(ipaddress, Integer.parseInt(port));
        din = new DataInputStream(soc.getInputStream());
        dout = new DataOutputStream(soc.getOutputStream());
        dout.writeUTF(LoginName);
        ClientRead cr = new ClientRead(soc);
        
    }
    
    class ClientRead extends Thread{
        
          Socket sock=null;
          DataInputStream dinp=null;
    
        ClientRead(Socket csoc) throws IOException{
           
            sock = csoc;
            start();
        }
        
       public void run(){
        
           while (true) {
            try {
                dinp=new DataInputStream(sock.getInputStream());
                String msg = dinp.readUTF();
                String realMsg = "";
                StringTokenizer st = new StringTokenizer(msg);
                String from = st.nextToken();
       
                if (from.equals("#REFRESH#")) {

                AvailableUser.clear();

                    
                    while(st.hasMoreTokens())
                        AvailableUser.add(st.nextToken());
                }
                else if(from.equals("#ERROR#")){
                  ChatHistory.append("--->" + "cannot send-------------USER offline !");  
                }
                    
                else {
                    while (st.hasMoreTokens()) {
                        realMsg = realMsg + " " + st.nextToken();
                    }

                    ChatHistory.append("\n" + from + ": " + realMsg);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
           
        }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ChatHistory = new javax.swing.JTextArea();
        send = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        Refresh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        msgBox = new javax.swing.JTextArea();
        AvailableUser = new java.awt.List();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ChatHistory.setColumns(20);
        ChatHistory.setRows(5);
        jScrollPane1.setViewportView(ChatHistory);

        send.setBackground(new java.awt.Color(0, 255, 51));
        send.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        send.setText("SEND");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });

        logout.setBackground(new java.awt.Color(255, 51, 51));
        logout.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        logout.setText("LOGOUT");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });

        Refresh.setBackground(new java.awt.Color(51, 51, 255));
        Refresh.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Refresh.setText("REFRESH");
        Refresh.setBorder(null);
        Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshActionPerformed(evt);
            }
        });

        msgBox.setColumns(20);
        msgBox.setRows(5);
        msgBox.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane2.setViewportView(msgBox);

        AvailableUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AvailableUserMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 51));
        jLabel1.setText("Online");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(send, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Refresh, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(AvailableUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(logout)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(2, 2, 2)
                        .addComponent(AvailableUser, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(send, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed

        if (!(msgBox.getText() == null) && !(sendto.equals(""))) {

            try {
                dout.writeUTF(sendto + " " + LoginName + " " + "#NORMAL#" + " " + msgBox.getText());
                ChatHistory.append("\n" + LoginName + "(YOU): " + msgBox.getText());
                msgBox.setText(null);
            } catch (IOException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_sendActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed

        try {
            dout.writeUTF(LoginName+ " " + LoginName + " " + "#REFRESH#");
       
        } catch (IOException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_RefreshActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed

        try {
            dout.writeUTF(LoginName + " " + LoginName + " " + "#LOGOUT#");
        } catch (IOException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
    }//GEN-LAST:event_logoutActionPerformed

    private void AvailableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AvailableUserMouseClicked
       
       String x[]=AvailableUser.getSelectedItems();
       sendto = x[0];

    }//GEN-LAST:event_AvailableUserMouseClicked

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
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
        
                new MainPage().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.List AvailableUser;
    private javax.swing.JTextArea ChatHistory;
    private javax.swing.JButton Refresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton logout;
    private javax.swing.JTextArea msgBox;
    private javax.swing.JButton send;
    // End of variables declaration//GEN-END:variables

}
