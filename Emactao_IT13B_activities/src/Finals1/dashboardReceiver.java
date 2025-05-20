/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Finals1;

import Finals1.LogIn;
import Finals1.createAccount;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.util.Map;
import java.util.HashMap;


/**
 *
 * @author Eunace Faith Emactao
 */
public class dashboardReceiver extends javax.swing.JFrame {

    private javax.swing.JTextArea selectedTaskArea = null;
    private java.util.List<String> allTasks = new java.util.ArrayList<>();
   

    public dashboardReceiver() {
        initComponents();
        
       
        
        // Add window listener
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                // Add a small delay to ensure everything is initialized
                javax.swing.SwingUtilities.invokeLater(() -> {
                    loadTasks();
                });
            }
        });
    }

   private void loadTasks() {
    // Initialize the tasks panel and scroll pane
    taskPanel = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    taskPanel.setLayout(new javax.swing.BoxLayout(taskPanel, javax.swing.BoxLayout.Y_AXIS));
    jScrollPane1.setViewportView(taskPanel);

    // Add the scroll pane to the main panel
    areatasks.add(jScrollPane1);
    jScrollPane1.setBounds(10, 10, 700, 350);

    String username;
    if (createAccount.currentUSer != null) {
        username = createAccount.currentUSer;
        System.out.println("Using CreateAccount.currentUSer: " + username);
    } else {
        username = LogIn.currentUSer;
        System.out.println("Using LogIn.currentUSer: " + username);
    }

    String basePath = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Documents\\assignments\\GUI txt\\";
    String taskFilePath = basePath + username + ".txt";

    try {
        File file = new File(taskFilePath);

        allTasks.clear();

        taskPanel.removeAll();

        if (file.exists()) {
            java.util.Scanner scanner = new java.util.Scanner(file);

            boolean foundTasks = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Skip header lines, empty lines, and lines with only dashes
                if (line.isEmpty() || line.startsWith("Tasks for user:") || 
                    line.startsWith("Role:") || line.startsWith("Institution:") ||
                    line.matches("[-\\s]+")) {
                    continue;
                }

                foundTasks = true;
                allTasks.add(line);

                // Format the line for display
                String[] parts = line.split(", ");
                StringBuilder taskText = new StringBuilder();
                for (String part : parts) {
                    taskText.append(part.trim()).append("\n");
                }

                JTextArea taskArea = new JTextArea(taskText.toString());
                taskArea.setEditable(false);
                taskArea.setBackground(new Color(240, 240, 240));
                taskArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                taskArea.setLineWrap(true);
                taskArea.setWrapStyleWord(true);
                taskArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Optional mouse click to highlight selected task
                taskArea.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        for (Component c : taskPanel.getComponents()) {
                            if (c instanceof JTextArea) {
                                c.setBackground(new Color(240, 240, 240));
                            }
                        }
                        taskArea.setBackground(new Color(204, 230, 255));
                         selectedTaskArea = taskArea;
                    }
                });

                taskPanel.add(taskArea);
                taskPanel.add(Box.createVerticalStrut(10));
            }

            scanner.close();

            if (!foundTasks) {
                JTextArea noTasksArea = new JTextArea("No tasks found.");
                noTasksArea.setEditable(false);
                taskPanel.add(noTasksArea);
            }
        } else {
            JTextArea noTasksArea = new JTextArea("No tasks found.");
            noTasksArea.setEditable(false);
            taskPanel.add(noTasksArea);
        }

        taskPanel.revalidate();
        taskPanel.repaint();

    } catch (Exception e) {
        System.err.println("Error loading tasks: " + e.getMessage());
        taskPanel.removeAll();
        JTextArea errorArea = new JTextArea("Error loading tasks: " + e.getMessage());
        errorArea.setEditable(false);
        taskPanel.add(errorArea);
        taskPanel.revalidate();
        taskPanel.repaint();
    }
   
}
private void LoadTasks() {
    String username;
    if (createAccount.currentUSer != null) {
        username = createAccount.currentUSer;
    } else {
        username = LogIn.currentUSer;
    }

    String Category = (String) category.getSelectedItem();
    String Priority = (String) priority.getSelectedItem();

    String basePath = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Documents\\assignments\\GUI txt\\";
    String taskFilePath = basePath + username + ".txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(taskFilePath))) {
        String line;
        taskPanel.removeAll(); // Clear existing tasks
        boolean headerPassed = false;

        while ((line = reader.readLine()) != null) {
            if (line.contains("------------------------")) {
                headerPassed = true;
                continue;
            }

            if (headerPassed && !line.trim().isEmpty() && line.contains("Task Title:")) {
                boolean categoryMatch = Category.equals("All Categories") || 
                                        line.contains("Category: " + Category);
                boolean priorityMatch = Priority.equals("All Priorities") || 
                                        line.contains("Priority: " + Priority);

                if (categoryMatch && priorityMatch) {
                    // Create and add the task panel
                    JPanel singleTaskPanel = new JPanel();
                    singleTaskPanel.setLayout(new BorderLayout());
                    singleTaskPanel.setBackground(Color.WHITE);
                    singleTaskPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                    JTextArea taskArea = new JTextArea(line);
                    taskArea.setEditable(false);
                    taskArea.setWrapStyleWord(true);
                    taskArea.setLineWrap(true);
                    taskArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    taskArea.setBackground(Color.WHITE);

                    singleTaskPanel.add(taskArea, BorderLayout.CENTER);
                    taskPanel.add(singleTaskPanel);
                }
            }
        }

        if (taskPanel.getComponentCount() == 0) {
            JPanel noTaskPanel = new JPanel(new BorderLayout());
            noTaskPanel.setBackground(Color.WHITE);
            noTaskPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
            ));

            String message = "No tasks found";
            if (!Category.equals("All Categories") || !Priority.equals("All Priorities")) {
                message += " matching the selected filters.";
            }

            JTextArea noTaskArea = new JTextArea(message);
            noTaskArea.setEditable(false);
            noTaskArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            noTaskArea.setBackground(Color.WHITE);
            noTaskPanel.add(noTaskArea, BorderLayout.CENTER);
            taskPanel.add(noTaskPanel);
        }

        taskPanel.revalidate();
        taskPanel.repaint();

    } catch (Exception e) {
        JPanel errorPanel = new JPanel(new BorderLayout());
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
        taskPanel.add(errorPanel);
    }
}

// Helper to parse a task line into a map of fields
private Map<String, String> parseTaskFields(String taskLine) {
    Map<String, String> map = new HashMap<>();
    String[] parts = taskLine.split(",");
    for (String part : parts) {
        String[] kv = part.split(":", 2);
        if (kv.length == 2) {
            map.put(kv[0].trim(), kv[1].trim());
        }
    }
    return map;
}

private boolean deleteTaskFromAssignerFileByFields(String assignerFilePath, String fullTaskText) {
    // Parse the selected task into a map
    Map<String, String> selectedFields = parseTaskFields(fullTaskText);

    // Remove "Assigned By" from the map, as it's not in the assigner's file
    selectedFields.remove("Assigned By");

    File assignerFile = new File(assignerFilePath);
    File tempFile = new File(assignerFile.getAbsolutePath() + ".tmp");
    boolean found = false;

    try (
        BufferedReader reader = new BufferedReader(new FileReader(assignerFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
    ) {
        String line;
        while ((line = reader.readLine()) != null) {
            Map<String, String> fileFields = parseTaskFields(line);
            // Only compare fields that are in the selected task (ignoring "Assigned By")
            boolean allMatch = true;
            for (String key : selectedFields.keySet()) {
                String selectedValue = selectedFields.get(key);
                String fileValue = fileFields.get(key);
                if (fileValue == null || !fileValue.trim().equalsIgnoreCase(selectedValue.trim())) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) {
                found = true;
                continue; // Skip this line (delete)
            }
            writer.write(line);
            writer.newLine();
        }
        writer.flush();
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, "Error updating assigner's file: " + ex.getMessage());
        return false;
    }

    if (found) {
        if (!assignerFile.delete()) {
            JOptionPane.showMessageDialog(null, "Failed to delete original assigner's file: " + assignerFile.getAbsolutePath());
            return false;
        }
        if (!tempFile.renameTo(assignerFile)) {
            JOptionPane.showMessageDialog(null, "Failed to rename temp file: " + tempFile.getAbsolutePath());
            return false;
        }
    } else {
        tempFile.delete();
    }
    return found;
}

    // Add delete button action listener in your GUI builder code
   
 

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        taskPanel = new javax.swing.JPanel();
        category = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        priority = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areatasks = new javax.swing.JTextArea();
        completed = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        taskPanel.setBackground(new java.awt.Color(204, 230, 255));

        category.setBackground(new java.awt.Color(153, 204, 255));
        category.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        category.setForeground(new java.awt.Color(0, 0, 0));
        category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Categories", "Academics", "Work", "Home", "Personal" }));
        category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Category:");

        priority.setBackground(new java.awt.Color(153, 204, 255));
        priority.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        priority.setForeground(new java.awt.Color(0, 0, 0));
        priority.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Priorities", "High", "Medium", "Low" }));
        priority.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priorityActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Priority:");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("To-Do Tasks");

        areatasks.setBackground(new java.awt.Color(255, 255, 255));
        areatasks.setColumns(20);
        areatasks.setRows(5);
        jScrollPane1.setViewportView(areatasks);

        completed.setBackground(new java.awt.Color(153, 204, 255));
        completed.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        completed.setForeground(new java.awt.Color(0, 0, 0));
        completed.setText("Completed");
        completed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completedActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(153, 204, 255));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(0, 0, 0));
        jButton13.setText("Log Out");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout taskPanelLayout = new javax.swing.GroupLayout(taskPanel);
        taskPanel.setLayout(taskPanelLayout);
        taskPanelLayout.setHorizontalGroup(
            taskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, taskPanelLayout.createSequentialGroup()
                .addGap(0, 116, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
            .addGroup(taskPanelLayout.createSequentialGroup()
                .addGroup(taskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(taskPanelLayout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(186, 186, 186)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(priority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(taskPanelLayout.createSequentialGroup()
                        .addGap(418, 418, 418)
                        .addComponent(jLabel1))
                    .addGroup(taskPanelLayout.createSequentialGroup()
                        .addGap(367, 367, 367)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(completed)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        taskPanelLayout.setVerticalGroup(
            taskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(taskPanelLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addGroup(taskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(priority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(taskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(completed, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taskPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taskPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryActionPerformed
           LoadTasks();
    }//GEN-LAST:event_categoryActionPerformed

    private void completedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedActionPerformed
  
if (selectedTaskArea == null) {
    JOptionPane.showMessageDialog(null, "Please select a task to complete.");
    return;
}

// Convert multiline taskText to single-line format matching the txt file
String taskTextMultiLine = selectedTaskArea.getText().trim();
if (taskTextMultiLine.isEmpty()) {
    JOptionPane.showMessageDialog(null, "Selected task is empty.");
    return;
}

String[] lines = taskTextMultiLine.split("\n");
StringBuilder sb = new StringBuilder();
for (int i = 0; i < lines.length; i++) {
    sb.append(lines[i].trim());
    if (i < lines.length - 1) {
        sb.append(", ");
    }
}
String taskText = sb.toString();

String currentUser = createAccount.currentUSer != null
    ? createAccount.currentUSer
    : LogIn.currentUSer;
String basePath = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Documents\\assignments\\GUI txt\\";
String currentUserFile = basePath + currentUser + ".txt";

// Extract "Assigned By" from single-line taskText
String assignedBy = null;
int assignedByIndex = taskText.indexOf("Assigned By: ");
if (assignedByIndex != -1) {
    int commaAfter = taskText.indexOf(",", assignedByIndex);
    if (commaAfter == -1) commaAfter = taskText.length();
    assignedBy = taskText.substring(
        assignedByIndex + "Assigned By: ".length(),
        commaAfter
    ).trim();
}

if (assignedBy == null || assignedBy.isEmpty()) {
    JOptionPane.showMessageDialog(null, "Could not identify the assigner.");
    return;
}

try {
    File originalFile = new File(currentUserFile);
    File tempFile = new File(basePath + "temp_" + currentUser + ".txt");

    // Read original, write to temp skipping the task line
    try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

        String line;
        boolean taskFoundAndRemoved = false;

        while ((line = reader.readLine()) != null) {
            if (line.trim().equalsIgnoreCase(taskText)) {
                taskFoundAndRemoved = true;
                continue; // Skip this line (delete)
            }
            writer.write(line);
            writer.newLine();
        }

        if (!taskFoundAndRemoved) {
            JOptionPane.showMessageDialog(
                null,
                "Could not find the selected task in your file."
            );
            // clean up temp file
            tempFile.delete();
            return;
        }
    }

    // Replace original file with temp file
    if (!originalFile.delete() || !tempFile.renameTo(originalFile)) {
        JOptionPane.showMessageDialog(
            null,
            "Failed to update your task file."
        );
        return;
    }
    
 String assignerFile = basePath + assignedBy + ".txt";
 boolean deleted = deleteTaskFromAssignerFileByFields(assignerFile, taskText);
 if (!deleted) {
    JOptionPane.showMessageDialog(null, "Task not found in assigner's file.");
 }
try (BufferedWriter writer = new BufferedWriter(new FileWriter(assignerFile, true))) {
   // Simply append a progress line at the end
   writer.write("Progress: Completed: " + taskText + "\n");
   writer.newLine();
} catch (IOException ex) {
   JOptionPane.showMessageDialog(null,
    "Failed to log progress to assigner: " + ex.getMessage(),
       "Error", JOptionPane.ERROR_MESSAGE);
}

    JOptionPane.showMessageDialog(null, "Task marked as completed.");
Component[] comps = taskPanel.getComponents();
for (int i = 0; i < comps.length; i++) {
    if (comps[i] == selectedTaskArea) {
        // Remove the spacer below it, if any
        if (i + 1 < comps.length && comps[i + 1] instanceof Box.Filler) {
            taskPanel.remove(comps[i + 1]);
        }
        // Remove the task area
        taskPanel.remove(selectedTaskArea);
        break;
    }
}
taskPanel.revalidate();
taskPanel.repaint();
selectedTaskArea = null;


} catch (IOException e) {
    JOptionPane.showMessageDialog(
        null,
        "Error processing task: " + e.getMessage()
    );
}

    }//GEN-LAST:event_completedActionPerformed


    private void priorityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priorityActionPerformed
        LoadTasks();
    }//GEN-LAST:event_priorityActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
       LogOut logOut = new LogOut();
       logOut.setVisible(true);
       dispose();
    }//GEN-LAST:event_jButton13ActionPerformed

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
            java.util.logging.Logger.getLogger(dashboardReceiver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboardReceiver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboardReceiver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboardReceiver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboardReceiver().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areatasks;
    private javax.swing.JComboBox<String> category;
    private javax.swing.JButton completed;
    private javax.swing.JButton jButton13;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> priority;
    private javax.swing.JPanel taskPanel;
    // End of variables declaration//GEN-END:variables
}
