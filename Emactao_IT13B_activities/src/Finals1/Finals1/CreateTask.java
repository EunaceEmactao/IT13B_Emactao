    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String taskTitle = tasktitle.getText();
        String taskDescription = TaskArea.getText();
        String assignedTo = assignedto.getSelectedItem().toString();
        Date deadline = (Date) deadlineSpinner.getValue();
        String priority = jComboBox1.getSelectedItem().toString();
        String category = CategoryBox.getSelectedItem().toString();
        
        if(taskTitle.isEmpty() || taskDescription.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields!");
            return;
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDeadline = dateFormat.format(deadline);
        
        String username;

        // Get username from either CreateAccount or LogIn
        if (createAccount.currentUSer != null) {
            username = createAccount.currentUSer;
        } else {
            username = LogIn.currentUSer;
        }

        // Create a unique file path for the current user
        String basePath = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Documents\\assignments\\GUI txt\\";
        String filePath = basePath + username + ".txt";

        try (java.io.FileWriter writer = new java.io.FileWriter(filePath, true)) {
            // Format the task information with all details
            writer.write("Task Title: " + taskTitle + "\n");
            writer.write("Task Description: " + taskDescription + "\n");
            writer.write("Assigned To: " + assignedTo + "\n");
            writer.write("Deadline: " + formattedDeadline + "\n");
            writer.write("Priority: " + priority + "\n");
            writer.write("Category: " + category + "\n");
            writer.write("------------------------\n");

            // Show confirmation with all details
            JOptionPane.showMessageDialog(null, "Task Added Successfully!\n\n" +
                "Title: " + taskTitle + "\n" +
                "Description: " + taskDescription + "\n" +
                "Assigned To: " + assignedTo + "\n" +
                "Deadline: " + formattedDeadline + "\n" +
                "Priority: " + priority + "\n" +
                "Category: " + category);

            // Clear fields
            tasktitle.setText("");
            TaskArea.setText("");
            deadlineSpinner.setValue(new Date()); // Reset to current date
            assignedto.setSelectedIndex(0);
            CategoryBox.setSelectedIndex(0);
            jComboBox1.setSelectedIndex(0);

            // Go to dashboard
            Dashboard eunna = new Dashboard();
            eunna.setVisible(true);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to save task: " + e.getMessage());
        }
    } 