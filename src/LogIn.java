import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

import DataBaseConnection.*;  // database connectivity package

import DataBaseConnection.DbConnection;
import WeatherPackage.WeatherGui;  // weather app gui and logic code package
public class LogIn extends JFrame
{
    public LogIn(){
        super("LogIn");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setSize(450,650);
        setLocationRelativeTo(null);
        setResizable(false);
        addLoginComponents();
        setVisible(true);
    }
    private void addLoginComponents(){

        JLabel loginBg= new JLabel(loadImage("Weather app java/src/asset/login.jpg"));
        loginBg.setBounds(0, 0,450 , 650);
        add(loginBg);

        JLabel loginnLabel = new JLabel("LOGIN");
        loginnLabel.setFont(new Font("Arial", Font.BOLD,30));
        loginnLabel.setForeground(Color.black);
        loginnLabel.setBounds(170, 90, 100, 30);
        loginBg.add(loginnLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD,20));
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBounds(50, 150, 150, 30);
        loginBg.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(170, 150, 200, 30);
        loginBg.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD,20));
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setBounds(50, 240, 100, 30);
        loginBg.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(170, 240, 200, 30);
        loginBg.add(passwordField);

        JButton loginButton = new JButton("Submit");
        loginButton.setBounds(170, 340, 100, 30);
        loginBg.add(loginButton);

        JButton signupButton = new JButton("Sign up");
        signupButton.setBounds(170, 380, 100, 30);
        loginBg.add(signupButton);

        JLabel errorLabel = new JLabel("");
        errorLabel.setBounds(50, 450, 300, 90);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Dialog",Font.BOLD,15));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginBg.add(errorLabel);

//         Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // Validate username and password
                if (validateLogin(username, password)) {
                    dispose();   // Close login page and open weather application
                    new WeatherGui(username);
                    System.out.println("hey i am in login page");
                     }
                    else {
                    errorLabel.setText("<html>Invalid username or password. Try again.<br>, or Create account");
                }
            }
        });


        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignUp();
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        String query = "select user_password from user_details where user_name=?";
        try{
            Connection con = DbConnection.getConnection();
            PreparedStatement st =con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            String db_password = null;
            if (rs.next()) {
                db_password = rs.getString("user_password");
            }
            return password.equals(db_password);
        }
        catch (SQLException | NullPointerException exe){
            System.out.println("some connection issue in sql ");
            return false;
        }
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

