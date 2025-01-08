package WeatherPackage;

import org.json.simple.JSONObject;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherGui extends JFrame
{
    JLabel temperatureText;
    JLabel weatherConditionDesc;
    JLabel humidityText;
    JLabel windspeedText;
    JLabel weatherConditionImage;
    private JSONObject weatherData;
    public WeatherGui(String username)
    {
        super("Weather app");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450,650);
        setLocationRelativeTo(null);  //load our gui at the center of the screen
        setLayout(null);
        setResizable(false); // prevent any resize of our gui that pop up on our screen [no maxmize option]
        addGuiComponents(username);  // here we call the method of this class don't get confusion
        setVisible(true);
    }

    public void addGuiComponents(String name)
    {
        JTextField searchtf = new JTextField();
        searchtf.setBounds(15,15,351,45);
        searchtf.setFont(new Font("Dialog",Font.PLAIN,24));
        add(searchtf);

        // weather image
        weatherConditionImage = new JLabel(loadImage("Weather app java/src/asset/clear.png"));
        weatherConditionImage.setBounds(0,130,450,217);
        add(weatherConditionImage);

        JLabel user_name = new JLabel("<html>Welcome,<b>"+name+"</b> Let’s<br>check out today’s weather.</html>");
        user_name.setFont(new Font("Dialog",Font.ITALIC,20));
        user_name.setHorizontalAlignment(SwingConstants.CENTER);
        user_name.setBounds(80,70,300,50);
        add(user_name);

        //temperature text
        temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0,350,450,54);
        temperatureText.setFont(new Font("Dialog",Font.BOLD,48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);  //set the text center
        add(temperatureText);

        //weather condition description
        weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0,405,450,36);
        weatherConditionDesc.setFont(new Font("Dialog",Font.PLAIN,32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        //humidity image is added in this part
        JLabel humidityImage = new JLabel(loadImage("Weather app java/src/asset/humidity.png"));
        humidityImage.setBounds(15,500,74,66);
        add(humidityImage);

        // humidity text
        humidityText = new JLabel("<html><b>Humidity<b> 100% </html>"); //also use like this in Double quotes
        humidityText.setBounds(90,500,85,55);
        add(humidityText);

        //windspeed image
        JLabel windspeedImage = new JLabel(loadImage("Weather app java/src/asset/windspeed.png"));
        windspeedImage.setBounds(220,500,74,66);
        add(windspeedImage);

        //windspeed Text
        windspeedText = new JLabel("<html><b>Windspeed<b> 15km/h </html>");
        windspeedText.setBounds(310,500,85,55);
        add(windspeedText);

        //search button
        JButton searchbt = new JButton(loadImage("Weather app java/src/asset/search.png")); // here we call the class  in the  swing package ImageIcon
        searchbt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //here we change the cursor into hand cursor when it hover the search option
        searchbt.setBounds(375,13,47,45);
        add(searchbt);

        searchbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get location from the user
                String userInput = searchtf.getText();
//                validate text and remove whitespace to make like in the url format
//                if(userInput.replaceAll("\\s","").length()<=0d && userInput== null || userInput.trim().isEmpty())
//
                if(userInput== null || userInput.trim().isEmpty())
                {
                    return;
                }
                //retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);
                if (weatherData != null) {
                    displayWeatherInfo(weatherData);
                }
            }
        });
    }

    public void displayWeatherInfo(JSONObject weatherData){
        //update Gui
        //update weather images in gui
        String weatherCondition = (String) weatherData.get("weatherCondition");
        System.out.println(weatherCondition);
        //depend on the weather condition we are updating our images
        switch (weatherCondition)
        {
            case "Clear":
                //setIcon() is used to change the Image of the lable
                weatherConditionImage.setIcon(loadImage("Weather app java/src/asset/clear.png"));
                break;
            case "Cloudy":
                weatherConditionImage.setIcon(loadImage("Weather app java/src/asset/cloudy.png"));
                break;
            case "Rain":
                weatherConditionImage.setIcon(loadImage("Weather app java/src/asset/rain.png"));
                break;
            case "Snow":
                weatherConditionImage.setIcon(loadImage("Weather app java/src/asset/snow.png"));
                break;
        }

        //update temperature text
        double temperature = (double) weatherData.get("temperature");
        temperatureText.setText(temperature+" C");

        //update weather condition text
        weatherConditionDesc.setText(weatherCondition);

        //update humidity text
        long humidity = (long) weatherData.get("humidity");
        humidityText.setText("<html><b>Humidity<b><br/>"+humidity + " %</html>");

        //update wind speed
        double windspeed = (double) weatherData.get("windSpeed");
        windspeedText.setText("<html><b>WindSpeed<b><br/> "+windspeed+ " km/h</html>");
    }

    // used to create image for gui component button
    private ImageIcon loadImage(String resourcePath)
    {
        try
        {
            //read the image from the given file path
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        }
        catch (IOException e)
        {
            System.out.println("something wrong in the image reading.......");
            return null;  // here we handle the error in the reading of image
        }

    }
}
