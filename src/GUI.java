import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import static org.knowm.xchart.style.markers.SeriesMarkers.NONE;

/**
 * @author Edward Taylor, 976335
 */
public class GUI extends JPanel {

    static double timeY = 1.0;
    static final double [] init = {0.5};
    static int sleepLength;
    static ArrayList<Double> time = new ArrayList<Double>();
    static ArrayList<Double> attentionSeriesY = new ArrayList<Double>();
    static ArrayList<Double> meditationSeriesY = new ArrayList<Double>();
    static ArrayList<Double> deltaSeriesY = new ArrayList<Double>();
    static ArrayList<Double> thetaSeriesY = new ArrayList<Double>();
    static ArrayList<Double> alphaSeriesY = new ArrayList<Double>();
    static ArrayList<Double> betaSeriesY = new ArrayList<Double>();
    static ArrayList<Double> gammaSeriesY = new ArrayList<Double>();
    static double [] avgArray;
    static double [] rawArray;
    static boolean isPause = false;

    XYChart chart = QuickChart.getChart("EEG Chart", "Time (Seconds)", "Brain Wave Strength (Hz)", "deltaSeries", init, init);
    JFrame frame;
    JPanel panel = new JPanel();
    JButton setButton = new JButton("Set: 20");
    JSlider slider = new JSlider(JSlider.HORIZONTAL, 5, 40 ,20);
    JButton clearButton = new JButton("Clear Chart");
    JLabel isActive = new JLabel("<html><font size='5' color =black>User Asleep & Alarm Set: </font> "
        + "<html><font size='5' color =red>NO</font>");
    JButton pauseButton = new JButton("Pause Chart Toggle");

    /**
     * constructor which sets up graph series and GUI objects
     *
     * @throws InterruptedException
     */
    public GUI() throws InterruptedException {
        //add series
        chart.addSeries("attentionSeries",init, init).setMarker(NONE);
        chart.addSeries("meditationSeries",init, init).setMarker(NONE);
        chart.addSeries("thetaSeries",init, init).setMarker(NONE);
        chart.addSeries("alphaSeries",init, init).setMarker(NONE);
        chart.addSeries("betaSeries",init, init).setMarker(NONE);
        chart.addSeries("gammaSeries",init, init).setMarker(NONE);
        //add chart to frame
        frame = new SwingWrapper(chart).displayChart();
        frame.setSize(1100,600);

        //format buttons and listen for clicks
        isActive.setBounds(740,600,300,50);

        pauseButton.setBounds(580,670,150,20);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseChart();
            }
        });

        clearButton.setBounds(800,670,100,20);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearChart();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });

        setButton.setBounds(320,645,100,20);
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sleepLength = slider.getValue();
                System.out.println("Set alarm for: "+ sleepLength);
            }
        });

        slider.setBounds(150,575,400,80);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println(slider.getValue());
                setButton.setText("Set: "+slider.getValue());
            }
        });

        //add objects to frame
        frame.add(pauseButton);
        frame.add(setButton);
        frame.add(clearButton);
        frame.add(slider);
        frame.add(isActive);
        frame.add(panel);
        frame.setSize(1100,750);

        setVisible(true);
    }

    /**
     * method that clears chart
     *
     * @throws InterruptedException
     */
    private void clearChart() throws InterruptedException {
        attentionSeriesY.clear();
        meditationSeriesY.clear();
        deltaSeriesY.clear();
        thetaSeriesY.clear();
        alphaSeriesY.clear();
        betaSeriesY.clear();
        gammaSeriesY.clear();
        time.clear();

        System.out.println("clearing chart");
    }

    /**
     * method that pauses current chart updates
     */
    private void pauseChart(){
        isPause = !isPause;
    }

    /**
     * updates chart for running average values
     *
     * @param y
     * @throws InterruptedException
     */
    public void updateGUIRunningAvg(double y) throws InterruptedException {
        timeY=y;
        time.add(timeY);

        attentionSeriesY.add(avgArray[1]);
        meditationSeriesY.add(avgArray[2]);
        deltaSeriesY.add(avgArray[3]);
        thetaSeriesY.add(avgArray[4]);
        alphaSeriesY.add(avgArray[5]);
        betaSeriesY.add(avgArray[7]);
        gammaSeriesY.add(avgArray[9]);

        chart.updateXYSeries("attentionSeries",time,attentionSeriesY,null);
        chart.updateXYSeries("meditationSeries",time,meditationSeriesY,null);
        chart.updateXYSeries("deltaSeries",time,deltaSeriesY,null);
        chart.updateXYSeries("thetaSeries",time,thetaSeriesY,null);
        chart.updateXYSeries("alphaSeries",time,alphaSeriesY,null);
        chart.updateXYSeries("betaSeries",time,betaSeriesY,null);
        chart.updateXYSeries("gammaSeries",time,gammaSeriesY,null);

        if (!isPause){
            frame.update(frame.getGraphics());
        }
        checkAlarm();
    }

    /**
     * updates chart for raw values
     *
     * @param y
     * @throws InterruptedException
     */
    public void updateGUIRaw(double y) throws InterruptedException {
        timeY=y;
        time.add(timeY);

        attentionSeriesY.add(rawArray[2]);
        meditationSeriesY.add(rawArray[3]);
        deltaSeriesY.add(rawArray[4]);
        thetaSeriesY.add(rawArray[5]);
        alphaSeriesY.add(rawArray[6]);
        betaSeriesY.add(rawArray[8]);
        gammaSeriesY.add(rawArray[10]);

        chart.updateXYSeries("attentionSeries",time,attentionSeriesY,null);
        chart.updateXYSeries("meditationSeries",time,meditationSeriesY,null);
        chart.updateXYSeries("deltaSeries",time,deltaSeriesY,null);
        chart.updateXYSeries("thetaSeries",time,thetaSeriesY,null);
        chart.updateXYSeries("alphaSeries",time,alphaSeriesY,null);
        chart.updateXYSeries("betaSeries",time,betaSeriesY,null);
        chart.updateXYSeries("gammaSeries",time,gammaSeriesY,null);

        if (!isPause){
            frame.update(frame.getGraphics());
        }

    }

    /**
     * checks if alarm is able to be set and if so plays alarm sound after waiting for alarm
     * @throws InterruptedException
     */
    private void checkAlarm () throws InterruptedException {
        if (sleepLength > 0) {
            if ((avgArray[2] > 0.75)&&(avgArray[5]>0.05)) {
                isActive.setText("<html><font size='5' color =black>User Asleep & Alarm Set: </font> "
                        + "<html><font size='5' color =green>YES</font>");
                System.out.println("Asleep");
                new java.util.Timer().schedule(
                        new java.util.TimerTask(){
                            @Override
                            public void run(){
                                System.out.println("ALARM !");
                                playAlarm();
                            }

                        },(sleepLength*60)*1000
                );
            }
        }
    }

    /**
     * method that plays the alarm sound
     */
    private void playAlarm(){
        File audio = new File("alarm.wav");
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audio));
            clip.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * method that updates current average data
     * @param runningRolAverageNorm
     */
    public void updateAvgArray(double [] runningRolAverageNorm){
        this.avgArray = runningRolAverageNorm;
    }

    /**
     * method that updates current raw data
     * @param rawArrayN
     */
    public void updateRawArray(double [] rawArrayN){
        this.rawArray = rawArrayN;
    }

}