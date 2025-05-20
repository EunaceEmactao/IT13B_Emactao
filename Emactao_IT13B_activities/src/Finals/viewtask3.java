/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Finals;

import java.io.File;

/**
 *
 * @author Eunace Faith Emactao
 */
public class viewtask3 extends javax.swing.JFrame {

    /**
     
     */
    private javax.swing.JTextArea selectedTaskArea = null;
    private java.util.List<String> allTasks = new java.util.ArrayList<>();
    private javax.swing.JPanel tasksPanel;
    private javax.swing.JScrollPane jScrollPane1;

    public viewtask3() {
        initComponents();
        String username;

        if (CreateAccount.currentUSer != null) {
            username = CreateAccount.currentUSer;
        } else {
            username = loginfinal2.currentUSer;
        }

        String basePath = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Documents\\assignments\\GUI txt\\";
        String taskFilePath = basePath + username + ".txt";
        
        File directory = new File(basePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Initialize the tasks panel and scroll pane
        tasksPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tasksPanel.setLayout(new javax.swing.BoxLayout(tasksPanel, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(tasksPanel);
        
        // Add the scroll pane to the main panel
        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(50, 100, 739, 297);
        
        // Add delete button action listener
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSelectedTask();
            }
        });
        
        // Load tasks when the form opens
        loadTasks();
    }

    private void loadTasks() {
        String username;
        if (CreateAccount.currentUSer != null) {
            username = CreateAccount.currentUSer;
        } else {
            username = loginfinal2.currentUSer;
        }

        String basePath = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Documents\\assignments\\GUI txt\\";
        String taskFilePath = basePath + username + ".txt";
        
        try {
            File file = new File(taskFilePath);
            if (file.exists()) {
                java.util.Scanner scanner = new java.util.Scanner(file);
                tasksPanel.removeAll();
                allTasks.clear();
                
                while (scanner.hasNextLine()) {
                    String task = scanner.nextLine();
                    if (!task.trim().isEmpty()) {
                        allTasks.add(task);
                        String[] parts = task.split(",");
                        if (parts.length >= 4) {
                            String taskName = parts[0].trim();
                            String deadline = parts[1].trim();
                            String priority = parts[2].trim();
                            String category = parts[3].trim();
                            
                            String formattedTask = "Task: " + taskName + "\n" +
                                                 "Deadline: " + deadline + "\n" +
                                                 "Priority: " + priority + "\n" +
                                                 "Category: " + category + "\n";
                            
                            javax.swing.JTextArea taskArea = new javax.swing.JTextArea(formattedTask);
                            taskArea.setEditable(false);
                            taskArea.setBackground(new java.awt.Color(240, 240, 240));
                            taskArea.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));
                            taskArea.setRows(4);
                            
                            // Add mouse listener for selection
                            taskArea.addMouseListener(new java.awt.event.MouseAdapter() {
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    // Deselect previous task
                                    if (selectedTaskArea != null) {
                                        selectedTaskArea.setBackground(new java.awt.Color(240, 240, 240));
                                    }
                                    // Select new task
                                    selectedTaskArea = taskArea;
                                    taskArea.setBackground(new java.awt.Color(200, 200, 255));
                                }
                            });
                            
                            tasksPanel.add(taskArea);
                            tasksPanel.add(javax.swing.Box.createVerticalStrut(10));
                        }
                    }
                }
                scanner.close();
                tasksPanel.revalidate();
                tasksPanel.repaint();
            } else {
                javax.swing.JTextArea noTasksArea = new javax.swing.JTextArea("No tasks found.");
                noTasksArea.setEditable(false);
                tasksPanel.add(noTasksArea);
            }
        } catch (Exception e) {
            javax.swing.JTextArea errorArea = new javax.swing.JTextArea("Error loading tasks: " + e.getMessage());
            errorArea.setEditable(false);
            tasksPanel.add(errorArea);
        }
    }

    private void deleteSelectedTask() {
        if (selectedTaskArea == null) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Please select a task to delete first.", 
                "No Task Selected", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this task?",
            "Confirm Delete",
            javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            try {
                String username;
                if (CreateAccount.currentUSer != null) {
                    username = CreateAccount.currentUSer;
                } else {
                    username = loginfinal2.currentUSer;
                }

                String basePath = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Documents\\assignments\\GUI txt\\";
                String taskFilePath = basePath + username + ".txt";

                // Find the index of the selected task
                int selectedIndex = -1;
                for (int i = 0; i < allTasks.size(); i++) {
                    String[] parts = allTasks.get(i).split(",");
                    if (parts.length >= 4) {
                        String formattedTask = "Task: " + parts[0].trim() + "\n" +
                                             "Deadline: " + parts[1].trim() + "\n" +
                                             "Priority: " + parts[2].trim() + "\n" +
                                             "Category: " + parts[3].trim() + "\n";
                        if (formattedTask.equals(selectedTaskArea.getText())) {
                            selectedIndex = i;
                            break;
                        }
                    }
                }

                if (selectedIndex != -1) {
                    // Remove the task from the list
                    allTasks.remove(selectedIndex);

                    // Write the updated tasks back to the file
                    java.io.FileWriter writer = new java.io.FileWriter(taskFilePath);
                    for (String task : allTasks) {
                        writer.write(task + "\n");
                    }
                    writer.close();

                    // Reload the tasks display
                    loadTasks();
                    selectedTaskArea = null;
                }
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Error deleting task: " + e.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(839, 481));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("To-Do Tasks");

        jComboBox1.setBackground(new java.awt.Color(0, 0, 0));
        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Categories", "Academics", "Work", "Home", "Personal" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Category:");

        jComboBox2.setBackground(new java.awt.Color(0, 0, 0));
        jComboBox2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Priorities", "High", "Medium", "Low" }));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Priority:");

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Delete");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(334, 334, 334))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(viewtask3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewtask3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewtask3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewtask3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewtask3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
