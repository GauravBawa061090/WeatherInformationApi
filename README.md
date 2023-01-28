# WeatherInformationApi

This is a sample project that connects to and open source weather api to get weather information. I have used below Java frameworks to develop this -

1. Spring-Boot for micro-services. RestTemplate to connect to openweathermap api
2. Spring Cache to cache the response for 2 hours
3. Junit for unit testing

Note - openweathermap url, apikey and cache evict time is configured in application.properties


This project will expose two enpoints - 

- /location : This will get the weather information by city / country. Pass ISO standard city / country name
	- Sample Request - http://localhost:8080/location?region=Brazil

- /coordinate : This will get the weather information by longitude and latitude. 
	- Sample Request - http://localhost:8080/coordinate?lat=1.23&lon=-1.53
	
Base URI - http://localhost:8080
