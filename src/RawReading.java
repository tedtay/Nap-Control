import static java.lang.Integer.parseInt;

/**
 * @author Edward Taylor, 976335
 */
public class RawReading {

    private String rawcsv;
    private String[] eegValues;

    /**
     * Splits the CSV string into multiple strings
     *
     * @param rawcsv the current CSV-formatted string
     */
    public RawReading(String rawcsv){
        this.rawcsv = rawcsv;

        this.eegValues = rawcsv.split(",");
        this.eegValues = rawcsv.split(",");
        this.eegValues = rawcsv.split(",");
        this.eegValues = rawcsv.split(",");
        this.eegValues = rawcsv.split(",");
        this.eegValues = rawcsv.split(",");
        this.eegValues = rawcsv.split(",");
        this.eegValues = rawcsv.split(",");
        this.eegValues = rawcsv.split(",");
        this.eegValues = rawcsv.split(",");
    }

    /**
     * method to parse the string data into ints
     *
     * @return array of raw ints which have been parsed from Strings to Ints
     */
    public int[] splitRawCsv(){
        int signalStrength = parseInt(eegValues[0]);
        int attention = parseInt(eegValues[1]);
        int meditation = parseInt(eegValues[2]);
        int delta = parseInt(eegValues[3]);
        int theta = parseInt(eegValues[4]);
        int lowAlpha = parseInt(eegValues[5]);
        int highAlpha = parseInt(eegValues[6]);
        int lowBeta = parseInt(eegValues[7]);
        int highBeta = parseInt(eegValues[8]);
        int lowGamma = parseInt(eegValues[9]);
        int highGamma = parseInt(eegValues[10]);

        return new int[]{signalStrength, attention, meditation, delta, theta, lowAlpha, highAlpha, lowBeta,
        highBeta, lowGamma, highGamma};
    }
}
