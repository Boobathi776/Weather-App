import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DataBaseConnection.*;
public class SignUp extends JFrame {
    public SignUp()
    {
        super("SignUp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setSize(450,650);
        setLocationRelativeTo(null);
        setResizable(false);
        addSignupComponents();
        setVisible(true);
    }
    public void addSignupComponents()
    {
        JLabel signupBg = new JLabel(loadImage("Weather app java/src/asset/signup.jpg"));
        signupBg.setBounds(0,0,450,650);
        add(signupBg);

        JLabel signupLabel = new JLabel("Sign Up");
        signupLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        signupLabel.setHorizontalAlignment(SwingConstants.CENTER);
        signupLabel.setBounds(100, 40, 200, 30);
        signupBg.add(signupLabel);

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 100, 100, 25);
        signupBg.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 100, 200, 25);
        signupBg.add(usernameField);

        // Date of birth label and field
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(50, 160, 100, 25);
        signupBg.add(dobLabel);
        JLabel dobHint = new JLabel("Example :2024-12-17 (yyyy-MM-dd)");
        dobHint.setBounds(150, 180, 200, 25);
        signupBg.add(dobHint);


        JTextField dobField = new JTextField();
        dobField.setBounds(150, 160, 200, 25);
        signupBg.add(dobField);

        // Address label and TextArea
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 220, 100, 25);
        signupBg.add(addressLabel);

        JTextArea addressField = new JTextArea();
        addressField.setBounds(150, 220, 200, 60);
        addressField.setLineWrap(true);
        addressField.setWrapStyleWord(true);
        signupBg.add(addressField);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 300, 100, 25);
        signupBg.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 300, 200, 25);
        signupBg.add(passwordField);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(160, 360, 100, 30);
        signupBg.add(submitButton);

        // Action Listener for the Submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String dob = dobField.getText();
                String address = addressField.getText();
                String password = new String(passwordField.getPassword());
                // Validate and process input (this can be customized as needed)
                if (username.isEmpty() || dob.isEmpty() || address.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    updateDetails(username,dob,address,password);
                    dispose(); // Close the sign-up window
                    new LogIn();
                }
            }

            private void updateDetails(String username, String dob, String address, String password) {
                String query = "insert into user_details values(?,?,?,?)";
                try{
                    Connection con = DbConnection.getConnection();
                    PreparedStatement st =con.prepareStatement(query);
                    st.setString(1,username);
                    st.setString(2,dob);
                    st.setString(3,address);
                    st.setString(4, password);
                    st.executeUpdate();
                }
                catch (SQLException | NullPointerException exe){
                    System.out.println("some connection issue in sql in signup ");
                }

            }
        });

    }

    public ImageIcon loadImage(String path)
    {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
            Image scaledImg = image.getScaledInstance(450, 650, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } catch (IOException e) {
            System.out.println("problem in reading image from the location");
            return null;
        }
    }

}


