package fi.antientropy.pagerdutyreports;

import java.util.Optional;

import fi.antientropy.pagerdutyreports.domain.logentries.LogEntries;

public interface ServiceLevels {

    public Optional<String> getReactionTime(LogEntries logEntries) throws Exception;
    public Optional<String> getReactionTimeStamp(LogEntries logEntries) throws Exception;
    public Optional<String> getResolvedTime(LogEntries logEntries) throws Exception;
    public Optional<String> getMessages(LogEntries logEntries) throws Exception;
}
