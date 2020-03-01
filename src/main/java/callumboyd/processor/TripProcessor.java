package callumboyd.processor;

import callumboyd.model.Journey;
import callumboyd.model.Tap;
import callumboyd.model.TapType;
import callumboyd.validator.TapValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripProcessor {

    TapValidator validator = new TapValidator();
    FileProcessor csvProcessor = new FileProcessor();
    Map<String, Tap> unfinishedJourneys;
    List<Journey> finishedJourneys;

    public void processRecords() {
        unfinishedJourneys = new HashMap<>();
        finishedJourneys = new ArrayList<>();
        List<Tap> taps = csvProcessor.readCSV();

        for ( Tap tap: taps ) {
            if(!validator.validateTap(tap)) {
                //Handle bad data
                //Assuming Data is valid for this assessment.
            } else {
                processTap(tap);
            }
        }

        for (String key : unfinishedJourneys.keySet()) {
            incompleteJourney(unfinishedJourneys.get(key));
        }
        csvProcessor.writeCSV(finishedJourneys);
        System.out.println("trips.csv has been created.");
    }

    private void processTap(Tap tap) {
        if (tap.getTapType() == TapType.ON) {
            if (unfinishedJourneys.containsKey(tap.getPan())) {
                incompleteJourney(unfinishedJourneys.get(tap.getPan()));
                unfinishedJourneys.remove(tap.getPan());
            }
            unfinishedJourneys.put(tap.getPan(), tap);
        } else {
            if (unfinishedJourneys.containsKey(tap.getPan())) {
                completeJourney(unfinishedJourneys.get(tap.getPan()), tap);
                unfinishedJourneys.remove(tap.getPan());
            } else {
                //Start of journey doesn't exist.
                //For this assessment I'm assuming that the file loaded isn't missing a tap on.
            }

        }
    }

    private void incompleteJourney(Tap tap) {
        Journey journey = new Journey(tap.getTapDate(),tap.getStopId(), tap.getCompanyId(), tap.getBusId(), tap.getPan());
        finishedJourneys.add(journey);
    }

    private void completeJourney(Tap tapOn, Tap tapOff) {
        Journey journey = new Journey(tapOn.getTapDate(), tapOff.getTapDate(), tapOn.getStopId(),
            tapOff.getStopId(), tapOn.getCompanyId(), tapOn.getBusId(), tapOn.getPan());
        finishedJourneys.add(journey);
    }

}
