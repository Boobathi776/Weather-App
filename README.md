# Weather-App 🌦️

A Java-based Weather Application that fetches and displays real-time weather data for a given location using APIs. This project demonstrates API integration, GUI development using Swing, and JSON data handling.

---

## Features
- Fetches latitude and longitude for a given location using the Geocoding API.
- Retrieves weather details such as temperature, humidity, wind speed, and weather conditions.
- Displays weather data in a user-friendly GUI built with Java Swing.
- Error handling for API connectivity and invalid user inputs.

---

## APIs Used

1. **Geocoding API**  
   - Used to fetch the latitude and longitude of a location based on its name.  
   - Documentation: [https://open-meteo.com/en/docs/geocoding-api](https://open-meteo.com/en/docs/geocoding-api)

2. **Weather Forecast API**  
   - Used to fetch hourly weather data like temperature, humidity, weather conditions, and wind speed using the latitude and longitude data.  
   - Documentation: [https://open-meteo.com/en/docs#hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m](https://open-meteo.com/en/docs#hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m)

---

## Dependencies

1. **JSON-Simple Library**  
   - Used for parsing and handling JSON data.  
   - Download: [https://code.google.com/archive/p/json-simple/downloads](https://code.google.com/archive/p/json-simple/downloads)

2. **MySQL Connector/J**  
   - JDBC driver for MySQL database connections.  
   - Download: [https://dev.mysql.com/downloads/connector/j/](https://dev.mysql.com/downloads/connector/j/)

---

## How to Run

1. **Clone the Repository**  
   ```bash
   git clone https://github.com/your-username/weather-app.git
   cd weather-app
