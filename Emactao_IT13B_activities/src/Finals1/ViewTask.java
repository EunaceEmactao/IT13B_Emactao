/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Finals1;

import Finals.CreateAccount;
import Finals.loginfinal2;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Eunace Faith Emactao
 */
public class ViewTask extends javax.swing.JFrame {
    private JPanel tasksPanel;
    private JScrollPane scrollPane;
    private JPanel selectedTaskPanel = null;
    private String selectedTaskData = null;
    private String currentInstitution;

    /**
     * Creates new form ViewTask
     */
    public ViewTask() {
        initComponents();
        setupTasksPanel();
        
        
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[] { "All Categories", "Academics", "Work", "Home", "Personal" }));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[] { "All Priorities", "High", "Medium", "Low" }));
        
        
        loadAssignedUsers();
        
        
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTasks(); 
            }
        });
        
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTasks(); 
            }
        });
        
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTasks(); 
            }
        });
        
        loadTasks();
        setLocationRelativeTo(null);
    }

    private void setupTasksPanel() {
        
        tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        tasksPanel.setBackground(new Color(240, 240, 240));

        
        scrollPane = new JScrollPane(tasksPanel);
        scrollPane.setBounds(50, 150, 950, 450);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        
        jPanel2.setLayout(null);
        jPanel2.add(scrollPane);
    }

    private void createTaskPanel(String taskData) {
        
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BorderLayout());
        taskPanel.setBackground(new Color(255, 255, 255));
        taskPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
        ));
        taskPanel.setMaximumSize(new Dimension(800, 200));
        taskPanel.setPreferredSize(new Dimension(800, 200));

        
        JTextArea taskArea = new JTextArea();
        taskArea.setEditable(false);
        taskArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        taskArea.setBackground(new Color(255, 255, 255));
        taskArea.setLineWrap(true);
        taskArea.setWrapStyleWord(true);
        taskArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        
        String[] fields = taskData.split(", ");
        StringBuilder formattedTask = new StringBuilder();
        for (String field : fields) {
            formattedTask.append(field).append("\n");
        }
        taskArea.setText(formattedTask.toString());

        
        taskPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (selectedTaskPanel != null) {
                    selectedTaskPanel.setBackground(new Color(255, 255, 255));
                    selectedTaskPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
                    ));
                }
                selectedTaskPanel = taskPanel;
                selectedTaskData = taskData;
                taskPanel.setBackground(new Color(204, 230, 255)); // Lighter blue than #99CCFF
                taskPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(10, 10, 10, 10),
                    BorderFactory.createLineBorder(new Color(100, 180, 220), 2) // Optional: blue border
                ));
            }
        });

        
        taskPanel.add(taskArea, BorderLayout.CENTER);

        
        JPanel spacerPanel = new JPanel();
        spacerPanel.setPreferredSize(new Dimension(800, 10));
        spacerPanel.setBackground(new Color(240, 240, 240));

        
        tasksPanel.add(taskPanel);
        tasksPanel.add(spacerPanel);
    }

    private void loadTasks() {
        String username;
        if (CreateAccount.currentUSer != null) {
            username = CreateAccount.currentUSer;
        } else {
            username = loginfinal2.currentUSer;
        }

        String selectedCategory = (String) jComboBox1.getSelectedItem();
        String selectedPriority = (String) jComboBox2.getSelectedItem();
        String selectedAssignee = (String) jComboBox3.getSelectedItem();

        String basePath = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Documents\\assignments\\GUI txt\\";
        String taskFilePath = basePath + username + ".txt";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(taskFilePath))) {
            String line;
            tasksPanel.removeAll(); // Clear existing tasks
            boolean headerPassed = false;
            
            while ((line = reader.readLine()) != null) {
                if (line.contains("------------------------")) {
                    headerPassed = true;
                    continue;
                }
                
                
                if (headerPassed && !line.trim().isEmpty() && line.contains("Task Title:")) {
                    
                    boolean categoryMatch = selectedCategory.equals("All Categories") || 
                                         line.contains("Category: " + selectedCategory);
                    boolean priorityMatch = selectedPriority.equals("All Priorities") || 
                                         line.contains("Priority: " + selectedPriority);
                    boolean assigneeMatch = selectedAssignee.equals("All Users") || 
                                         line.contains("Assigned To: " + selectedAssignee);

                    if (categoryMatch && priorityMatch && assigneeMatch) {
                        createTaskPanel(line);
                    }
                }
            }
            
            if (tasksPanel.getComponentCount() == 0) {
                
                JPanel noTaskPanel = new JPanel();
                noTaskPanel.setLayout(new BorderLayout());
                noTaskPanel.setBackground(new Color(255, 255, 255));
                noTaskPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(10, 10, 10, 10),
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
                ));

                String message = "No tasks found";
                if (!selectedCategory.equals("All Categories") || 
                    !selectedPriority.equals("All Priorities") || 
                    !selectedAssignee.equals("All Users")) {
                    message += " matching the selected filters";
                }
                
                JTextArea noTaskArea = new JTextArea(message);
                noTaskArea.setEditable(false);
                noTaskArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                noTaskArea.setBackground(new Color(255, 255, 255));
                noTaskPanel.add(noTaskArea, BorderLayout.CENTER);
                tasksPanel.add(noTaskPanel);
            }
            
            tasksPanel.revalidate();
            tasksPanel.repaint();
            
        } catch (Exception e) {
            // Create error panel
            JPanel errorPanel = new JPanel();
            errorPanel.setLayout(new BorderLayout());
            errorPanel.setBackground(new Color(255, 240, 240));
            errorPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(255, 200, 200), 1)
            ));

            JTextArea errorArea = new JTextArea("Error loading tasks: " + e.getMessage());
            errorArea.setEditable(false);
            errorArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            errorArea.setBackground(new Color(255, 240, 240));
            errorPanel.add(errorArea, BorderLayout.CENTER);
            tasksPanel.add(errorPanel);
        }
    }

    private void loadAssignedUsers() {
        try {
            
            String credentialsPath = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Documents\\assignments\\GUI txt\\eun2.txt";
            String currentUser = CreateAccount.currentUSer != null ? CreateAccount.currentUSer : loginfinal2.currentUSer;
            
            
            java.io.BufferedReader institutionReader = new java.io.BufferedReader(new java.io.FileReader(credentialsPath));
            String line;
            while ((line = institutionReader.readLine()) != null) {
                if (line.contains("Username:" + currentUser)) {
                    String[] parts = line.split(" , ");
                    for (String part : parts) {
                        if (part.startsWith("Institution:")) {
                            currentInstitution = part.substring("Institution:".length()).trim();
                            break;
                        }
                    }
                    break;
                }
            }
            institutionReader.close();

            
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(credentialsPath));
            java.util.ArrayList<String> users = new java.util.ArrayList<>();
            users.add("All Users"); 

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" , ");
                String username = "";
                String institution = "";
                String role = "";

                for (String part : parts) {
                    if (part.startsWith("Username:")) {
                        username = part.substring("Username:".length()).trim();
                    } else if (part.startsWith("Institution:")) {
                        institution = part.substring("Institution:".length()).trim();
                    } else if (part.startsWith("Role:")) {
                        role = part.substring("Role:".length()).trim();
                    }
                }

                
                if (institution.equals(currentInstitution) && role.equals("I receive tasks")) {
                    users.add(username);
                }
            }
            reader.close();

            
            jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(users.toArray(new String[0])));

        } catch (Exception e) {
            System.err.println("Error loading users: " + e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(204, 230, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("To-Do Tasks");

        jComboBox1.setBackground(new java.awt.Color(153, 204, 255));
        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Categories", "Academics", "Work", "Home", "Personal" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Category:");

        jComboBox2.setBackground(new java.awt.Color(153, 204, 255));
        jComboBox2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Priorities", "High", "Medium", "Low" }));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Priority:");

        jButton1.setBackground(new java.awt.Color(153, 204, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(153, 204, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Assigned To:");

        jComboBox3.setBackground(new java.awt.Color(153, 204, 255));
        jComboBox3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Maria", "Elena", "Charise", "Penny" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addGap(32, 32, 32)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(jButton2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 509, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        loadTasks();  
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
        dispose();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedTaskPanel == null || selectedTaskData == null) {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.", "No Task Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this task?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            deleteTask();
        }
    }

    private void deleteTask() {
        String username;
        if (CreateAccount.currentUSer != null) {
            username = CreateAccount.currentUSer;
        } else {
            username = loginfinal2.currentUSer;
        }

        String basePath = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Documents\\assignments\\GUI txt\\";
        String taskFilePath = basePath + username + ".txt";
        File inputFile = new File(taskFilePath);
        File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

        try {
            
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(tempFile));

            String line;
            boolean headerWritten = false;
            while ((line = reader.readLine()) != null) {
                
                if (!headerWritten && (line.contains("Tasks for user") || line.contains("------------------------"))) {
                    writer.println(line);
                    continue;
                }
                headerWritten = true;

                
                if (!line.trim().equals(selectedTaskData.trim())) {
                    writer.println(line);
                }
            }
            writer.close();
            reader.close();

            
            if (!inputFile.delete()) {
                throw new Exception("Could not delete original file");
            }

            
            if (!tempFile.renameTo(inputFile)) {
                throw new Exception("Could not rename temp file");
            }

            
            tasksPanel.remove(selectedTaskPanel);
            
            if (tasksPanel.getComponentCount() > 0) {
                tasksPanel.remove(tasksPanel.getComponentCount() - 1);
            }
            
            selectedTaskPanel = null;
            selectedTaskData = null;
            
            tasksPanel.revalidate();
            tasksPanel.repaint();
            
            
            JOptionPane.showMessageDialog(this, "Task deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            
            loadTasks();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error deleting task: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

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
            java.util.logging.Logger.getLogger(ViewTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewTask().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
