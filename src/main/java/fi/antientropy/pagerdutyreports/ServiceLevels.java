package fi.antientropy.pagerdutyreports;

import fi.antientropy.pagerdutyreports.domain.logentries.LogEntries;

public interface ServiceLevels {

    public String getReactionTime(LogEntries logEntries) throws Exception;
    public String getReactionTimeStamp(LogEntries logEntries) throws Exception;
    public String getResolvedTime(LogEntries logEntries) throws Exception;
    public String getMessages(LogEntries logEntries) throws Exception;
}
