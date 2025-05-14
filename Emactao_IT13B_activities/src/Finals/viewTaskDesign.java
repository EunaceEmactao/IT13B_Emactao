/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Finals;

import Finals.CreateAccount;
import Finals.DASHBOARD;
import Finals.loginfinal2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Eunace Faith Emactao
 */
public class viewTaskDesign extends javax.swing.JFrame {

    /**
     * Creates new form viewTaskDesign
     */
    public viewTaskDesign() {
        initComponents();
              
           
    String username;

    // Get username from either CreateAccount or loginfinal2
    if (CreateAccount.currentUSer != null) {
        username = CreateAccount.currentUSer;
    } else {
        username = loginfinal2.currentUSer;
    }

    String taskFilePath = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Documents\\assignments\\GUI txt\\" + username + ".txt";

    // Clear existing content from the panel
    jPanel11.removeAll();
    jPanel11.setLayout(new BoxLayout(jPanel11, BoxLayout.Y_AXIS));

    // Add "To-Do Tasks" label at top center
    JLabel titleLabel = new JLabel("To-Do Tasks");
    titleLabel.setFont(new Font("Segoe", Font.BOLD, 18));
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    jPanel11.add(titleLabel);

    try (BufferedReader reader = new BufferedReader(new FileReader(taskFilePath))) {
        String line;

        while ((line = reader.readLine()) != null) {
            JPanel taskLine = new JPanel(new BorderLayout());
            taskLine.setBackground(Color.decode("#CCCCFF"));
            taskLine.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // some padding

            JTextArea taskText = new JTextArea(line);
            taskText.setEditable(false);
            taskText.setLineWrap(true);
            taskText.setWrapStyleWord(true);
            taskText.setFont(new Font("Segoe", Font.PLAIN, 18));
            taskText.setRows(2); // smaller height
            taskText.setColumns(25); // smaller width

            JScrollPane scroll = new JScrollPane(taskText);
            scroll.setPreferredSize(new Dimension(300, 50));
            taskLine.add(scroll, BorderLayout.CENTER);

            JButton deleteButton = new JButton("Delete");
            deleteButton.setBackground(Color.black);
            deleteButton.setFont(new Font("Segoe", Font.PLAIN, 18));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.addActionListener(e -> {
                try {
                    File file = new File(taskFilePath);
                    ArrayList<String> updatedTasks = new ArrayList<>();

                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String fileLine;
                        while ((fileLine = br.readLine()) != null) {
                            if (!fileLine.trim().equals(fileLine.trim())) {
                                updatedTasks.add(fileLine);
                            }
                        }
                    }

                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                        for (String updatedLine : updatedTasks) {
                            bw.write(updatedLine);
                            bw.newLine();
                        }
                    }

                    jPanel11.remove(taskLine);
                    jPanel11.revalidate();
                    jPanel11.repaint();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting task: " + ex.getMessage());
                }
            });

            taskLine.add(deleteButton, BorderLayout.EAST);
            jPanel11.add(taskLine);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error reading task file: " + e.getMessage());
    }

    JButton backButton = new JButton("Back");
     backButton.setBackground(Color.black);
           backButton.setFont(new Font("Segoe", Font.PLAIN, 18));
            backButton.setForeground(Color.WHITE);
             backButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    backButton.addActionListener(e -> {
        DASHBOARD dash = new DASHBOARD();
        dash.setVisible(true);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(jPanel11);
        topFrame.dispose();
    });

    JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    backPanel.add(backButton);
    jPanel11.add(Box.createVerticalStrut(10)); // spacing before back button
    jPanel11.add(backPanel);

    jPanel11.revalidate();
    jPanel11.repaint();
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel11 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 849, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 478, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(viewTaskDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewTaskDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewTaskDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewTaskDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewTaskDesign().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel11;
    // End of variables declaration//GEN-END:variables
}
