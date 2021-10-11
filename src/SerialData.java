
/**
 * @author Edward Taylor, 976335
 */
public class SerialData {
    private final int line;
    private final int signalStrength;
    private final int attention;
    private final int meditation;
    private final int delta;
    private final int theta;
    private final int lowAlpha;
    private final int highAlpha;
    private final int lowBeta;
    private final int highBeta;
    private final int lowGamma;
    private final int highGamma;

    /**
     * constructor that sets all the classes current data values
     *
     * @param line
     * @param signalStrength
     * @param attention
     * @param meditation
     * @param delta
     * @param theta
     * @param lowAlpha
     * @param highAlpha
     * @param lowBeta
     * @param highBeta
     * @param lowGamma
     * @param highGamma
     */
    public SerialData(int line, int signalStrength, int attention, int meditation, int delta, int theta,
                      int lowAlpha, int highAlpha, int lowBeta, int highBeta, int lowGamma, int highGamma){
        this.line = line;
        this.signalStrength = signalStrength;
        this.attention = attention;
        this.meditation = meditation;
        this.delta = delta;
        this.theta = theta;
        this.lowAlpha = lowAlpha;
        this.highAlpha = highAlpha;
        this.lowBeta = lowBeta;
        this.highBeta = highBeta;
        this.lowGamma = lowGamma;
        this.highGamma = highGamma;
    }

    /*
     * @return an int array of the current raw lines values
     */
    public int[] getAll(){
        return new int[]{line,signalStrength,attention,meditation,delta,theta,
                lowAlpha,highAlpha,lowBeta,highBeta,lowGamma,highGamma};
    }

    /*
     * @return  an int array of the current normalised lines values
     */
    public double[] getAllNorm(){
        return new double[]{(double)line,getSignalStrengthNorm(),getAttentionNorm(),getMeditationNorm(),getDeltaNorm(),
                getThetaNorm(), getLowAlphaNorm(),getHighAlphaNorm(),getLowBetaNorm(),getHighBetaNorm(),getLowGammaNorm(),
                getHighGammaNorm()};
    }

    /**
     *getters for rawindividual  data values
     *
     * @return raw data values
     */
    public  int getLine() {
        return line;
    }
    public  int getSignalStrength() {
        return signalStrength;
    }
    public  int getAttention() {
        return attention;
    }
    public  int getMeditation() {
        return meditation;
    }
    public  int getDelta() { return delta; }
    public  int getTheta() {
        return theta;
    }
    public  int getLowAlpha() {
        return lowAlpha;
    }
    public  int getHighAlpha() {
        return highAlpha;
    }
    public  int getLowBeta() { return lowBeta; }
    public  int getHighBeta() {
        return highBeta;
    }
    public  int getLowGamma() {
        return lowGamma;
    }
    public  int getHighGamma() {
        return highGamma;
    }

    /**
     * getters for all normalised data values
     * @return normalised data values
     */
    public  double getSignalStrengthNorm() {
        if(((double)signalStrength/(double)200)>1.0){
           return 0.5;
        }else{
            return ((double)signalStrength/(double)200);
        }
    }
    public  double getAttentionNorm() {
        if(((double)attention/(double)100)>1.0){
            return 0.5;
        }else{
            return ((double)attention/(double)100);
        }
    }
    public  double getMeditationNorm() {
        if(((double)meditation/(double)100)>1.0){
            return 0.5;
        }else{
            return ((double)meditation/(double)100);
        }
    }
    public  double getDeltaNorm() {
        if(((double)delta/(double)999999)>1.0){
            return 0.5;
        }else{
            return ((double)delta/(double)999999);
        }
    }
    public  double getThetaNorm() {
        if(((double)theta/(double)999999)>1.0){
            return 0.5;
        }else{
            return ((double)theta/(double)999999);
        }
    }
    public  double getLowAlphaNorm() {
        if(((double)lowAlpha/(double)999999)>1.0){
            return 0.5;
        }else{
            return ((double)lowAlpha/(double)999999);
        }
    }
    public  double getHighAlphaNorm() {
        if(((double)highAlpha/(double)999999)>1.0){
            return 0.5;
        }else{
            return ((double)highAlpha/(double)999999);
        }
    }
    public  double getLowBetaNorm() {
        if (((double) lowBeta / (double) 999999) > 1.0) {
            return 0.5;
        } else {
            return ((double) lowBeta / (double) 999999);
        }
    }
    public  double getHighBetaNorm() {
        if (((double) highBeta / (double) 999999) > 1.0) {
            return 0.5;
        } else {
            return ((double) highBeta / (double) 999999);
        }
    }
    public  double getLowGammaNorm() {
        if (((double) lowGamma / (double) 999999) > 1.0) {
            return 0.5;
        } else {
            return ((double) lowGamma / (double) 999999);
        }
    }
    public double getHighGammaNorm(){
        if (((double) highGamma / (double) 999999) > 1.0) {
            return 0.5;
        } else {
            return ((double) highGamma / (double) 999999);
        }
    }

}
