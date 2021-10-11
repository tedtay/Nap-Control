import com.fazecast.jSerialComm.SerialPort;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Edward Taylor, 976335
 */
public class Main {
    static ArrayList<SerialData> eegData = new ArrayList();
    static  PrintStream out;

    /**
     * Method that listens for incoming data and sends it to be processes
     *
     * @param args
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public static void main (String[] args) throws FileNotFoundException, InterruptedException {

        //List available ports
        SerialPort[] ports = SerialPort.getCommPorts();
        int i =0;
        System.out.println("Available Ports");
        for(SerialPort port: ports){
            System.out.println(port.getSystemPortName());
        }
        //Open the first available port
        SerialPort port = ports[0];
        if (port.openPort()){
            System.out.println("Successfully Opened Port "+port.getSystemPortName());
        }else{
            System.out.println("Could not open "+port.getSystemPortName());
        }
        //read port data
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING,0,0);
        Scanner portData = new Scanner(port.getInputStream());

        //create Gui object
        //create file to write to
        out = new PrintStream(new FileOutputStream("testdata.txt"));


        GUI myGUI = new GUI();


        //Main loop to process new line
        int y =1;
        String currentLine;
        while(portData.hasNextLine()) {

            currentLine = portData.nextLine();
            //only continue if the line is a data line, as oppose to an error line (every second line)
            if (currentLine.length() > 1) {
                if (currentLine.charAt(0) == '0' || currentLine.charAt(0) == '1' || currentLine.charAt(0) == '2'
                        || currentLine.charAt(0) == '3' || currentLine.charAt(0) == '4' || currentLine.charAt(0) == '5'
                        || currentLine.charAt(0) == '6' || currentLine.charAt(0) == '7' || currentLine.charAt(0) == '8'
                        || currentLine.charAt(0) == '9') {
                    try {
                        //make new RawReading object which takes in current line csv, splitRawCsw, make new
                        RawReading csv = new RawReading(currentLine);
                        SerialData newData = new SerialData(y, csv.splitRawCsv()[0], csv.splitRawCsv()[1],
                                csv.splitRawCsv()[2], csv.splitRawCsv()[3], csv.splitRawCsv()[4], csv.splitRawCsv()[5],
                                csv.splitRawCsv()[6], csv.splitRawCsv()[7], csv.splitRawCsv()[8], csv.splitRawCsv()[9],
                                csv.splitRawCsv()[10]);
                        eegData.add(newData);

                        printSerialDataLineNorm(y);
                        sendToTXT(Arrays.toString(eegData.get(y).getAll()));
                        printRolAvgNorm(y);
                        myGUI.updateAvgArray(runningRolAverageNorm(y));
                        myGUI.updateRawArray(eegData.get(y).getAllNorm());

                        //myGUI.updateGUIRaw(y);
                        myGUI.updateGUIRunningAvg(y);

                        y++;
                    } catch (Exception ex) { ex.printStackTrace(); }
                }else {
                    System.out.println("Error line " + y +": "+ currentLine);
                }
            }
        }
    }

    /**
     * Prints live raw data to terminal
     *
     * @param y time value
     */
    public static void printSerialDataLineRaw(int y){
        System.out.println("Data Line  " + eegData.get(y).getAll()[0] + ": " + eegData.get(y).getAll()[1] + ", " + eegData.get(y).getAll()[2] + ", " + eegData.get(y).getAll()[3]
                + ", " + eegData.get(y).getAll()[4] + ", " + eegData.get(y).getAll()[5] + ", " + eegData.get(y).getAll()[6] + ", " + eegData.get(y).getAll()[7]
                + ", " + eegData.get(y).getAll()[8] + ", " + eegData.get(y).getAll()[9] + ", " + eegData.get(y).getAll()[10] + ", " + eegData.get(y).getAll()[11]);
    }

    /**
     * Prints live normalised data to terminal
     *
     * @param y time value
     */
    public static void printSerialDataLineNorm(int y){

            System.out.println("Data Line  " + (int)eegData.get(y).getAllNorm()[0] + ": " + eegData.get(y).getAllNorm()[1] + ", " + eegData.get(y).getAllNorm()[2] + ", " + eegData.get(y).getAllNorm()[3]
                    + ", " + eegData.get(y).getAllNorm()[4] + ", " + eegData.get(y).getAllNorm()[5] + ", " + eegData.get(y).getAllNorm()[6] + ", " + eegData.get(y).getAllNorm()[7]
                    + ", " + eegData.get(y).getAllNorm()[8] + ", " + eegData.get(y).getAllNorm()[9] + ", " + eegData.get(y).getAllNorm()[10] + ", " + eegData.get(y).getAllNorm()[11]);

    }

    /**
     * Prints averaged raw data to terminal
     *
     * @param y time value
     */
    public static void printRolAvgRaw(int y){
        if(y>9){
            System.out.println("100 Avg Raw "+y+": "+ runningRolAverageRaw(y)[0] + ", "+ runningRolAverageRaw(y)[1]
                    + ", "+ runningRolAverageRaw(y)[2] + ", "+ runningRolAverageRaw(y)[3] + ", " + runningRolAverageRaw(y)[4]
                    + ", "+ runningRolAverageRaw(y)[5] + ", "+ runningRolAverageRaw(y)[6] + ", "+ runningRolAverageRaw(y)[7]
                    + ", "+ runningRolAverageRaw(y)[8] + ", " + runningRolAverageRaw(y)[9] + ", " + runningRolAverageRaw(y)[10]);
        }else{
            System.out.println("100 Avg Raw "+y+" : Not enough data");
        }
    }

    /**
     * Prints averaged normalised data to terminal
     *
     * @param y time value
     */
    public static void printRolAvgNorm(int y){
        if(y>99){
            System.out.println("100 Avg Nor "+y+": "+ runningRolAverageNorm(y)[0] + ", "+ runningRolAverageNorm(y)[1]
                    + ", "+ runningRolAverageNorm(y)[2] + ", "+ runningRolAverageNorm(y)[3] + ", " + runningRolAverageNorm(y)[4]
                    + ", "+ runningRolAverageNorm(y)[5] + ", "+ runningRolAverageNorm(y)[6] + ", "+ runningRolAverageNorm(y)[7]
                    + ", "+ runningRolAverageNorm(y)[8] + ", " + runningRolAverageNorm(y)[9] + ", " + runningRolAverageNorm(y)[10]);
        }else{
            System.out.println("100 Avg Nor " +y+": Not enough data");
        }
    }

    /**
     * Method to print test data to a text file
     *
     * @param currentLine
     */
    public static void sendToTXT(String currentLine){
        out.println(currentLine);
    }

    /**
     *
     *
     * @param line current line value
     * @return an array of the rolling average values of the past 100 raw data lines,
     * if there are <100 lines before returns and array of 0.0
     */
    public static int[] runningRolAverageRaw(int line){

        int ss = 0,a= 0,m= 0,d= 0,t= 0,la= 0,ha= 0,lb= 0,hb= 0,lg= 0,hg= 0;
        if (line>99){
            for(int i = 0; i<100; i++){
                ss = ss + eegData.get(line-i).getAll()[1];
                a = a +  eegData.get(line-i).getAll()[2];
                m = m +  eegData.get(line-i).getAll()[3];
                d = d + eegData.get(line-i).getAll()[4];
                t = t + eegData.get(line-i).getAll()[5];
                la = la + eegData.get(line-i).getAll()[6];
                ha = ha + eegData.get(line-i).getAll()[7];
                lb = lb +  eegData.get(line-i).getAll()[8];
                hb = hb + eegData.get(line-i).getAll()[9];
                lg = lg + eegData.get(line-i).getAll()[10];
                hg = hg + eegData.get(line-i).getAll()[11];
            }
            ss = ss/100;
            a = a/100;
            m = m/100;
            d = d/100;
            t = t/100;
            la = la/100;
            ha = ha/100;
            lb = lb/100;
            hb = hb/100;
            lg = lg/100;
            hg = hg/100;

            return new int[]{ss, a, m, d, t, la, ha, lb, hb, lg, hg};
        }else{
            return new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        }
    }

    /**
     *
     *
     * @param line current line value
     * @return an array of the rolling average values of the past 100 normalised data lines,
     * if there are <100 lines before returns and array of 0.0
     */
    public static double[] runningRolAverageNorm(int line){

        double ss = 0,a= 0,m= 0,d= 0,t= 0,la= 0,ha= 0,lb= 0,hb= 0,lg= 0,hg= 0;
        if (line>99){
            for(int i = 0; i<100; i++){
                ss = ss + eegData.get(line-i).getAllNorm()[1];
                a = a +  eegData.get(line-i).getAllNorm()[2];
                m = m +  eegData.get(line-i).getAllNorm()[3];
                d = d + eegData.get(line-i).getAllNorm()[4];
                t = t + eegData.get(line-i).getAllNorm()[5];
                la = la + eegData.get(line-i).getAllNorm()[6];
                ha = ha + eegData.get(line-i).getAllNorm()[7];
                lb = lb +  eegData.get(line-i).getAllNorm()[8];
                hb = hb + eegData.get(line-i).getAllNorm()[9];
                lg = lg + eegData.get(line-i).getAllNorm()[10];
                hg = hg + eegData.get(line-i).getAllNorm()[11];
            }
            ss = (ss/100);
            a = (a/100);
            m = (m/100);
            d = (d/100);
            t = (t/100);
            la = (la/100);
            ha = (ha/100);
            lb = (lb/100);
            hb = (hb/100);
            lg = (lg/100);
            hg = (hg/100);

            return new double[]{ss, a, m, d, t, la, ha, lb, hb, lg, hg};
        }else{
            return new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        }
    }

}
