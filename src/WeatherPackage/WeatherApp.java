package WeatherPackage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/** This code used to fetch data from API
 * this fetch data from the external API and return it and the GUI will display the data */
public class WeatherApp {
    //fetch external data for given location
    public static JSONObject getWeatherData(String locationName)
    {
        //get location coordinated using the geolocation API
        JSONArray locationData = getLocationData(locationName);
        if (locationData == null || locationData.isEmpty()) {
            System.out.println("Error: Location data not found.");
            return null;
        }

        //extract langtitute and latitute data
        JSONObject location =  (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        // build api request url with location coordinates
        String urlString = "https://api.open-meteo.com/v1/forecast?"+
                "latitude="+ latitude +"&longitude="+ longitude +
                "&hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&timezone=Asia%2FSingapore";

        try
        {
            //call api and get the response
            HttpURLConnection conn = fetchApiRespose(urlString);
            //check response status  //200 means succesful connection
            if(conn.getResponseCode()!=200)
            {
                System.out.println("Error : could not connect to the API");
                return null;
            }
            //store resulting in Json data
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while(scanner.hasNext())
            {
                resultJson.append(scanner.nextLine());  // read and store into the stringbuilder
            }
            scanner.close();
            conn.disconnect();

            //parse through our data
            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj= (JSONObject) parser.parse(String.valueOf(resultJson));

            //retrieve hourly data
            JSONObject hourly = (JSONObject) resultJsonObj.get("hourly"); //hourly refers to the json object that we gonna get
            //we want to get the current hours data
            //so we need to get the current hour index
            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurerntTime(time);
            //get temperature
            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double  temperature = (double) temperatureData.get(index);
            System.out.println("Temperature : "+temperature);

            //get weather code
            JSONArray weatherCode = (JSONArray) hourly.get("weather_code");
            String weatherCondition = convertWeatherCode((long)weatherCode.get(index));
            System.out.println("weather condition : "+weatherCondition);


            //becaues it is not a string value so we convert in to actual string
            //get humidity
            JSONArray relativeHumidity = (JSONArray) hourly.get("relative_humidity_2m");
            long humidity = (long) relativeHumidity.get(index);
            System.out.println("HUmidity : " + humidity);

            //get windspeed
            JSONArray windspeedData = (JSONArray) hourly.get("wind_speed_10m");
            double windSpeed = (double) windspeedData.get(index);
            System.out.println("windspeed : "+ windSpeed);


            //build the weather json data object that we are going to acces in our front end
            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature",temperature);
            weatherData.put("weatherCondition",weatherCondition);
            weatherData.put("humidity",humidity);
            weatherData.put("windSpeed", windSpeed);

            return weatherData;

        }catch (Exception e)
        {
            System.out.println("something problem in putting data in the object");
            e.printStackTrace();
            return null;
        }


    }



    //retrives geographic coordinates for given location name
    public static JSONArray getLocationData(String locationName)
    {
        //replace any whitespace in location name to  + to adhere to API's request format
        locationName = locationName.replaceAll(" ","+");

        //build API url with location parameter
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name="+
                          locationName + "&count=10&language=en&format=json";
        try
        {
            //call api and get the response
            HttpURLConnection conn = fetchApiRespose(urlString);
            //check response status  //200 means succesful connection
            if(conn.getResponseCode()!=200)
            {
                System.out.println("Error : could not connect to the API");
                return null;
            }
            else{
                //store the API results
                StringBuilder resultJson = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());
                //read and store the json  data into our Stringbuilder
                while(scanner.hasNext())
                {
                    resultJson.append(scanner.nextLine());
                }
                scanner.close();
                //close the url connection
                conn.disconnect();


                //parse the json string into the json object
                JSONParser parser = new JSONParser();
                JSONObject resultJsonObj= (JSONObject) parser.parse(String.valueOf(resultJson));

                //get the list of location that the api generated from the given location name
                JSONArray locationData = (JSONArray) resultJsonObj.get("results");  //time 20:48
                return locationData;
            }

        }catch(Exception e)
        {
            System.out.println("ssomthing problem in getting location lattitutes and longtitutes");
            e.printStackTrace();
            return null;
        }

    }

    private static HttpURLConnection fetchApiRespose(String urlString)
    {
        try{
            URL url= new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //connect to our API
            conn.connect();
            return conn;
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        //if could not make a connention then
        return null;
    }

    private static int findIndexOfCurerntTime(JSONArray timeList) {
        String currentTime = getCurrentTime();
        //iterate through the time list and see which one matches the current time
        for(int i=0;i<timeList.size();i++)
        {
            String time =  (String) timeList.get(i);
            if(time.equalsIgnoreCase(currentTime))
            {
                //return the index
                return i;
            }
        }
        return 0;
    }

    public static String getCurrentTime() {
        //get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        //format data and time to be 2024-09-02T00:00 (this is how it is read at the api)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00");
        //format and print the current date and time
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;

    }

    //convert the weather code into something readable
    private static String convertWeatherCode(long weatheCode) {
        String weatherCondition=" ";
        if(weatheCode==0L)
        {
            weatherCondition="Clear";
        }
        else if (weatheCode>0L && weatheCode<=3L) {
            weatherCondition="Cloudy";
        }
        else if ((weatheCode>=51L && weatheCode<=67L) ||(weatheCode>=80L && weatheCode<=99L)){
            weatherCondition="Rain";
        }
        else if (weatheCode >= 71L && weatheCode <= 77L)
        {
            weatherCondition="Snow";
        }
        else
        {
            weatherCondition="don't know";
        }
        return weatherCondition;

    }



}
