package callumboyd.model;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class JourneyTest {

    @Test
    public void shouldConstructCompletedJourney() {
        Date startDate = new Date(1583028000000L);
        Date endDate = new Date(1583029800000L);//30 minutes later

        Journey journey = new Journey(startDate,endDate, "Stop1", "Stop2","Company1", "Bus1", "34343434343434");

        assertEquals(Status.COMPLETED, journey.getStatus());
        assertEquals(1800, journey.getDurationSecs());
        assertEquals("$3.25",journey.getChargeAmount());
    }

    @Test
    public void shouldConstructIncompleteJourney() {
        Date startDate = new Date(1583028000000L);

        Journey journey = new Journey(startDate, "Stop1","Company1", "Bus1", "34343434343434");

        assertEquals(Status.INCOMPLETE, journey.getStatus());
        assertEquals(0, journey.getDurationSecs());
        assertEquals("$7.30",journey.getChargeAmount());
    }

    @Test
    public void shouldConstructCancelledJourney() {
        Date startDate = new Date(1583028000000L);
        Date endDate = new Date(1583028060000L);//1 minutes later

        Journey journey = new Journey(startDate,endDate, "Stop1", "Stop1","Company1", "Bus1", "34343434343434");

        assertEquals(Status.CANCELLED, journey.getStatus());
        assertEquals(60, journey.getDurationSecs());
        assertEquals("$0",journey.getChargeAmount());
    }

    @Test
    public void shouldFormatStringCorrectly() {
        Date startDate = new Date(1583028000000L);
        Date endDate = new Date(1583028060000L);//1 minutes later

        Journey incompleteJourney = new Journey(startDate, "Stop1","Company1", "Bus1", "36700102000000");
        Journey journey = new Journey(startDate,endDate, "Stop1", "Stop1","Company1", "Bus1", "34343434343434");

        assertEquals("01-03-2020 02:00:00, 01-03-2020 02:01:00, 60, Stop1, Stop1, $0, Company1, Bus1, 34343434343434, CANCELLED", journey.toString());
        assertEquals("01-03-2020 02:00:00, , 0, Stop1, , $7.30, Company1, Bus1, 36700102000000, INCOMPLETE", incompleteJourney.toString());
    }
}
