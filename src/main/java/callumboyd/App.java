package callumboyd;

import callumboyd.processor.TripProcessor;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        TripProcessor tripProcessor = new TripProcessor();
        tripProcessor.processRecords();

    }
}
