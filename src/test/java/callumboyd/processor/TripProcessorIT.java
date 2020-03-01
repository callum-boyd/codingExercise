package callumboyd.processor;

import callumboyd.model.Journey;
import callumboyd.model.Status;
import callumboyd.model.Tap;
import callumboyd.model.TapType;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TripProcessorIT {

    @Test
    public void shouldConstructIncompleteJourney() {
        FileProcessor fileProcessor = mock(FileProcessor.class);
        List<Tap> taps = createTestData();
        when(fileProcessor.readCSV()).thenReturn(taps);

        TripProcessor tripProcessor = new TripProcessor();
        tripProcessor.csvProcessor = fileProcessor;
        tripProcessor.processRecords();

        ArgumentCaptor<List> journeys = ArgumentCaptor.forClass(ArrayList.class);
        verify(fileProcessor).writeCSV(journeys.capture());

        List<Journey> journeyList = journeys.getValue();


        Journey journey1 = completeJourney(taps.get(0), taps.get(1));
        Journey journey2 = completeJourney(taps.get(2), taps.get(3));
        Journey journey3 = completeJourney(taps.get(4), taps.get(5));
        Journey journey4 = incompleteJourney(taps.get(6));
        Journey journey5 = incompleteJourney(taps.get(7));


        assertEquals(journey1, journeyList.get(0));
        assertEquals(Status.COMPLETED, journeyList.get(0).getStatus());
        assertEquals(journey2, journeyList.get(1));
        assertEquals(Status.COMPLETED, journeyList.get(1).getStatus());
        assertEquals(journey3, journeyList.get(2));
        assertEquals(Status.CANCELLED, journeyList.get(2).getStatus());
        assertEquals(journey4, journeyList.get(3));
        assertEquals(Status.INCOMPLETE, journeyList.get(3).getStatus());
        assertEquals(journey5, journeyList.get(4));
        assertEquals(Status.INCOMPLETE, journeyList.get(4).getStatus());
    }

    private List<Tap> createTestData() {
        List<Tap> taps = new ArrayList<>();
        taps.add(new Tap(1, new Date(1583028000000L), TapType.ON, "Stop1","Company1", "Bus1", "34343434343434"));
        taps.add(new Tap(2, new Date(1583028600000L), TapType.OFF, "Stop2","Company1", "Bus1", "34343434343434"));
        taps.add(new Tap(3, new Date(1583114400000L), TapType.ON, "Stop2","Company1", "Bus2", "34343434343434"));
        taps.add(new Tap(4, new Date(1583118000000L), TapType.OFF, "Stop3","Company1", "Bus2", "34343434343434"));
        taps.add(new Tap(5, new Date(1583204400000L), TapType.ON, "Stop3","Company1", "Bus3", "34343434343434"));
        taps.add(new Tap(6, new Date(1583204500000L), TapType.OFF, "Stop3","Company1", "Bus3", "34343434343434"));
        taps.add(new Tap(7, new Date(1583290800000L), TapType.ON, "Stop3","Company1", "Bus3", "34343434343434"));
        taps.add(new Tap(8, new Date(1583290860000L), TapType.ON, "Stop2","Company1", "Bus3", "34343434343434"));

        return taps;
    }

    private Journey completeJourney(Tap tapOn, Tap tapOff) {
        return new Journey(tapOn.getTapDate(), tapOff.getTapDate(), tapOn.getStopId(),
            tapOff.getStopId(), tapOn.getCompanyId(), tapOn.getBusId(), tapOn.getPan());
    }

    private Journey incompleteJourney(Tap tap) {
        return new Journey(tap.getTapDate(),tap.getStopId(), tap.getCompanyId(), tap.getBusId(), tap.getPan());
    }

}
