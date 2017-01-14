# pagerduty-reports

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

*pagerduty-reports* gives csv format reporting on incidents in [PagerDuty](https://pagerduty.com/).

Format of the result is following:
```
IncidentNumber;IncidentKey;IncidentCreatedAt;ReactedToIncidentAt;ResolvedIncidentAt;TimeBetweenIncidentCreatedAndReacted;IncidentStatus;IncidentNotes
467;MyWebService;2016-12-08T03:00:42+02:00;2016-12-08T07:40:35.000Z;2016-12-08T08:34:13Z;03:40:35;resolved;Database was down, restarted.
468;MyDatabase;2016-12-09T15:48:15+02:00;2016-12-12T08:39:03.000Z;2016-12-12T08:58:25Z;04:50:48;resolved;Database was runnning low on disk space. Increased and restarted.
```

**TimeBetweenIncidentCreatedAndReacted** is calculated according to request parameters **serviceDays**, **serviceStart** and **serviceStop**.

## Request parameters

| Parameter    | Type              | Example              | Notes                                                              |
|--------------|-------------------|----------------------|--------------------------------------------------------------------|
| serviceDays  | array             | 1,2,3,4,5            | Monday is 1, Sunday is 7                                           |
| serviceStart | Time              | 08:00:00.000         | The service start time according to your SLA                       |
| serviceStop  | Time              | 22:00:00.00          | The service end time according to your SLA                         |
| token        | String            | w_8PcNuhHa-y3xYdmc1x | PagerDuty API token (can and should be read-only)                  |
| inclusion    | (Optional) String | Production           | Only PagerDuty services that start with *inclusion* will be listed |
| since        | Date              | 2017-01-01           | Incidents will be included since this day                          |
| until        | Date              | 2017-01-31           | Incidents will be included until this day                          |
| timezone     | String            | Europe/Helsinki      | Timestamps will use this timezone                                  |


## Run on local machine
```
mvn clean spring-boot:run
```

## Example request
```
curl "http://localhost:8080/?serviceDays=1,2,3,4,5&serviceStart=06:00:00.000&serviceStop=18:00:00.000&token=abc123&inclusion=Tuotanto&since=2017-01-01&until=2017-01-31&timezone=Europe/Helsinki"
```
