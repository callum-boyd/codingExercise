package callumboyd.processor;

import callumboyd.model.Journey;
import callumboyd.model.Tap;
import callumboyd.model.TapType;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class FileProcessor {

    public List<Tap> readCSV(){
        //Taken from https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java


        String csvFile = "taps.csv";
        List<Tap> taps = new ArrayList<>();

        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int firstLine = 0;
            while ((line = br.readLine()) != null) {
                if (firstLine == 0 ) {
                    firstLine++; //Skip the first line
                } else {
                    Tap newTap = new Tap();

                    String[] tapInfo = line.split(cvsSplitBy);
                    newTap.setId(Integer.parseInt(tapInfo[0].trim()));
                    newTap.setTapType(TapType.valueOf(tapInfo[2].trim()));
                    newTap.setStopId(tapInfo[3].trim());
                    newTap.setCompanyId(tapInfo[4].trim());
                    newTap.setBusId(tapInfo[5].trim());
                    newTap.setPan(tapInfo[6].trim());

                    try {

                        SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                        newTap.setTapDate(formatter.parse(tapInfo[1].trim()));
                    }
                    catch (ParseException e) {
                        //Bad Data, not handling for this assessment.
                    }
                    taps.add(newTap);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return taps;
    }

    public void writeCSV(List<Journey> journeys) {
        //Adapted from https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
        try {
            FileWriter csvOutputFile = new FileWriter("trips.csv");
            PrintWriter printWriter = new PrintWriter(csvOutputFile);
            printWriter.println("Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN,Status");
            for (Journey journey: journeys) {
                printWriter.println(journey.toString());
            }
            printWriter.close();

        } catch (IOException e) {
            //Not handling fileio errors for this assessment.
        }

    }
}
