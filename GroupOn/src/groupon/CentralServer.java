/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupon;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author OM SHUBHAM
 */
public class CentralServer extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form CentralServer
     */
    static ArrayList ClientSockets= new ArrayList();
    static ArrayList LoginNames = new ArrayList();
    ServerSocket soc=null;
    int flag=0;
    Socket Csoc;
 
    public CentralServer() throws Exception {
          initComponents();
          getContentPane().setBackground(new java.awt.Color(0,5,17));
          port.setText(null);
          ipaddr.setText(null);
 
    }
    
    class StartServer extends Thread{
        
        String Port=null;
        String IPaddress=null;
        StartServer(String port,String ipaddress) throws Exception{
           Port=port;
           IPaddress=ipaddress;
           start(); 
        }
        
        public void run(){
        connInfo.setText("SERVER is starting!!");
        connInfo.setText(connInfo.getText()+"\n"+"Server Started on port: "+port.getText()+" and IP:"+ipaddr.getText());
        try {
         
          soc=new ServerSocket(Integer.parseInt(Port),0,InetAddress.getByName(IPaddress));
        } catch (IOException ex) {
            Logger.getLogger(CentralServer.class.getName()).log(Level.SEVERE, null, "Error Fill Again");
        }
        
//        ClientSockets=new ArrayList[];
//        LoginNames=new Vector();
        while(true)
        {
            try{
            Csoc = soc.accept();
            
            }
            catch (Exception ex){
               JOptionPane.showMessageDialog(null, "ERROR!");  
            }
            try {
                AcceptClient obClient=new AcceptClient(Csoc);
            } catch (IOException ex) {
                Logger.getLogger(CentralServer.class.getName()).log(Level.SEVERE, null, "ERROR");
            }
        }
        }
        
    }
    
    class AcceptClient extends Thread{
        Socket ClientSocket;
        DataInputStream din=null;
        DataOutputStream dout=null;
        AcceptClient(Socket Csoc) throws IOException{
            ClientSocket=Csoc;
            din=new DataInputStream(ClientSocket.getInputStream());
            dout =new DataOutputStream(ClientSocket.getOutputStream());
            
            String LoginName=din.readUTF();
             connInfo.setText(connInfo.getText()+"\n"+"User: "+LoginName+" Connected!");
             LoginNames.add(LoginName);
             ClientSockets.add(ClientSocket);
             start();
        }
        
        public void run(){
            while(true){
                try{
                    
                    String msgFromClient=new String();
                    msgFromClient=din.readUTF();
                    StringTokenizer st=new StringTokenizer(msgFromClient);
                    String Sendto=st.nextToken();
                    String from=st.nextToken();
                    String MsgType=st.nextToken();
                
                    int iCount=0;
                    
                    if(MsgType.equals("#GROUP#")){
                        String mymsg=from;
                        String originalMessage;
                        originalMessage = din.readUTF();
                        mymsg = mymsg + " " + originalMessage;
                        ArrayList sendThem = new ArrayList();
                        while(st.hasMoreTokens()){
                            sendThem.add(st.nextToken());
                        }
                        for(int i=0;i<sendThem.size();i++){
                            String give = sendThem.get(i).toString();
                            if(LoginNames.contains(give)){
                              Socket tsoc=(Socket)ClientSockets.get(LoginNames.indexOf(give));
                              DataOutputStream tdout=new DataOutputStream(tsoc.getOutputStream());
                              tdout.writeUTF(mymsg);
                            }
                        }
                    }
                    else if(MsgType.equals("#LOGOUT#")){
                       
                        int  indexToRemove= LoginNames.indexOf(Sendto);
                        connInfo.append("\n"+Sendto + " Logged Out!!");
                        ClientSockets.remove(indexToRemove);
                        LoginNames.remove(indexToRemove);   
                        
                    }
                    else if(MsgType.equals("#REFRESH#")){
                        
                          
                        String giveUsers="#REFRESH#";
                        for(iCount=0;iCount<LoginNames.size();iCount++){
                            
                           if(LoginNames.get(iCount).toString().equals(from))
                               continue;
                           else
                            giveUsers = giveUsers + " " + LoginNames.get(iCount).toString();
                            
                        }
                        for(iCount=0;iCount<LoginNames.size();iCount++){
                            
                        if(LoginNames.get(iCount).toString().equals(from)){
                     
                        Socket tsoc=(Socket)ClientSockets.get(iCount);
                        DataOutputStream tdout=new DataOutputStream(tsoc.getOutputStream());
                        tdout.writeUTF(giveUsers);
                        break;
                        }
                            
                        }
                        
                    }
                    else{
               
                        String msg=from;
                        while(st.hasMoreTokens()){
                            msg = msg + " " + st.nextToken();
                        }

                            
                            if(LoginNames.contains(Sendto))
                            {
                              Socket tsoc=(Socket)ClientSockets.get(LoginNames.indexOf(Sendto));
                              DataOutputStream tdout=new DataOutputStream(tsoc.getOutputStream());
                              tdout.writeUTF(msg);  
                            }
                            else {
 
                            Socket tsoc=(Socket)ClientSockets.get(LoginNames.indexOf(from));
                            DataOutputStream tdout=new DataOutputStream(tsoc.getOutputStream());
                            tdout.writeUTF("#ERROR# Message Not Sent (USER OFFLINE)");
                          }
                             
                       
                        
                        
                    }
                    if(MsgType.equals("#LOGOUT#"))
                    {
                        break;
                    }
                    
                }
                catch(Exception ex){
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
        connInfo = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        port = new javax.swing.JTextField();
        ipaddr = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Central Server");

        connInfo.setEditable(false);
        connInfo.setColumns(20);
        connInfo.setRows(5);
        jScrollPane1.setViewportView(connInfo);

        jLabel1.setBackground(new java.awt.Color(51, 255, 51));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("PORT:");

        jLabel2.setBackground(new java.awt.Color(51, 255, 51));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 51));
        jLabel2.setText("IP ADDRESS:");

        port.setText("5127");

        ipaddr.setText("127.0.0.1");

        jButton1.setBackground(new java.awt.Color(0, 153, 51));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setText("START SERVER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(port)
                            .addComponent(ipaddr))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ipaddr, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      
        
        if(port.getText().equals("") || ipaddr.getText().equals("")){
             JOptionPane.showMessageDialog(null,"Error Fill Again");
        }
        else if(flag==1){
            JOptionPane.showMessageDialog(null,"Server is Already Running"); 
        }
        else{
        flag=1;
            try {
                StartServer ss = new StartServer(port.getText(),ipaddr.getText());
              
            } catch (Exception ex) {
                Logger.getLogger(CentralServer.class.getName()).log(Level.SEVERE, null, ex);
            }
       
     }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(CentralServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CentralServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CentralServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CentralServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CentralServer().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(CentralServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea connInfo;
    private javax.swing.JTextField ipaddr;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField port;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
