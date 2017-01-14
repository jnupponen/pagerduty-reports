package fi.antientropy.pagerdutyreports;

import org.joda.time.LocalTime;
import org.junit.Test;

public class TimeShit {

    @Test
    public void test() {
        LocalTime serviceStart = LocalTime.parse("08:00:00.000");
        System.out.print(serviceStart.toString());
    }

}
