# Nap Control: A Novel System For Precisely Controlling Nap Duration
###### Please contact @tedtay for permission to reference or use any of ideas or the work/ code in this repository 
## Project Abstract

Lack of sleep costs the UK economy £40 billion per year . However, this is unsurprising considering
that over third of UK adults struggle to get to sleep at least once a week and of this group 46% say that
their poor sleep ‘severely’ or ‘very severely’ impacts their focus levels at work.

This dissertation details the planning and development of a prototype device that is able to combat the UK’s, along with other countries’, issues resulting from a lack of sleep amongst their populations.

The device created encourages napping through providing more control and certainty to users
over their nap duration and therefore helps to mitigate the impact of a lack of sleep.

## Methodology
This project has successfully developed a proof-of-concept system that uses electroencephalogram
(EEG) brain data to determine when a subject has fallen asleep and allow an alarm to be set from
that moment. 

This system was implemented in two stages: hardware development and software
development. The hardware functions as a sensor and relay, meaning it collects the brain wave data and
then sends it to the software. The software acts as the brains of the operation – processing the raw data
to determine when the user has fallen asleep.

![image](https://user-images.githubusercontent.com/56178841/136786960-ea117beb-d001-446e-bb00-0ac347a778ac.png)

## Prerequisites

Initial hardware setup and Arduino configuration was followed from the @kitschpatrol BrainGrapher repo. I thank @kitschpatrol for allowing me to use his method in this project.

**Initial Setup Follows:** https://github.com/kitschpatrol/BrainGrapher

## Code Summary
###### Please see /JavaDoc folder for JavaDoc's for each .java file used in this project.

![image](https://user-images.githubusercontent.com/56178841/136788336-6cd4fae4-0391-46ba-b20b-e008f375b094.png)

**• Main.java**

The main method starts off by listening for available serial ports and creates a Scanner object out
of the first open communication port it finds. This scanner object is then used to listen for new
data in the data stream.

The main method also starts the GUI class as well as creates the other class objects which
facilitates the data processing. In addition, the Main class provides helpful test functions such as 
printing live data to terminal, saving test data to a text file as well and catching exceptions if any
are thrown.

**• RawReading.java**

The RawReading object is one of the first class objects to be created (besides the GUI), this objects
only purpose is to take in the String object from the scanner, which originated from the Eleegoo
Uno, and split this CSV-style string into 11 individual values which can be called upon later.
Therefore, this class only has one method ‘splitRawCsv’ and takes a single String in its constructor
called ‘rawCsv’.

**• SerialData.java**

The SerialData class is where most of the data formatting take place. This class takes multiple
integer parameters which have been created from the splitting of the String in the RawReading
method. These values can then be processed, this is done using many methods which return the
normalised values of the integers passed (the raw readings from the MindFlex). These values can
be remotely accessed individually or as a group in an Array object.

**• GUI.java**

The GUI class takes care of the user interface and charts. To achieve this it holds arrays of rolling
averages of the data collected so far which are then updated and plotted onto the line chart. This
class also takes care of listening for user inputs such as the pressing of buttons. It also takes care
of deciding when the alarm should be set using its ‘checkAlarm’ method. This method checks that
the past 100-sample average reading for the meditation value is above 0.75 and the 100-sample
average for the alpha value is above 0.05 and if so, the wait to sound the alarm begins.

## Example Run

The test subject was able to fall asleep quite quickly and around at the 150 second mark the attention
value fell sharply while the meditation value continued to rise. This is the first indication the subject
is beginning to drift off to sleep. Then around the 230 second mark the alpha readings began to
rise ever so slightly, this indicates the subject’s mind state is no longer thinking about anything
in particular which is typical of the early sleep stages. At around the 240 second mark both the
meditation value was greater than 0.75 and the alpha value was above 0.05 meaning the alarm was set at
that moment. This is a good demonstration of the system working perfectly the system working perfectly

![image](https://user-images.githubusercontent.com/56178841/136793540-a9ac9988-db35-46cc-9710-997ec37a155c.png)
