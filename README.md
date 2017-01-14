# pagerduty-reports

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Run on local machine
```
mvn clean spring-boot:run
```

## Example request
```
curl "http://localhost:8080/?serviceDays=1,2,3,4,5&serviceStart=06:00:00.000&serviceStop=18:00:00.000&token=abc123&inclusion=Tuotanto&since=2017-01-01&until=2017-01-31&timezone=Europe/Helsinki"
```