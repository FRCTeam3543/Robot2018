package team3543.powerglove;

import java.io.IOException;

/**
 * Adapted from:
 * 
 * FRC https://wpilib.screenstepslive.com/s/currentCS/m/75361/l/851714-creating-a-client-side-program
 * 
 */
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * This program reads from the serial port and then writes it to network tables
 * 
 * @author mfkahn
 *
 */
public class Main implements SerialPortEventListener {
	public static final int TEAM = 3543;
	public static final String TABLE = "powerglove";
	public static final String WRIST_POS = "wrist";
	public static final String CLAW_POS = "claw";
	
	int team;
	SerialReadByEvents serialReader;
	NetworkTableInstance networkTable;
	
	
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println("Usage: java Main <team number> <com port>");
			System.exit(1);
		}
		
		new Main(Integer.parseInt(args[0]), args[1]).run();
	}

	Main(int teamNumber, String comPort) throws IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
		this.team = teamNumber;
		serialReader = new SerialReadByEvents(comPort);
		networkTable = NetworkTableInstance.getDefault().getTable(TABLE).getInstance();
	}
	
	public void run() throws IOException {
		
		networkTable.startClientTeam(TEAM);  // where TEAM=190, 294, etc, or use inst.startClient("hostname") or similar
		networkTable.startDSClient();  // recommended if running on DS computer; this gets the robot IP from the DS
		serialReader.converse(this);
		
//		while (true) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException ex) {
//				System.out.println("interrupted");
//				return;
//			}
//			double x = xEntry.getDouble(0.0);
//			double y = yEntry.getDouble(0.0);
//			System.out.println("X: " + x + " Y: " + y);
//		}
		
	}

	int ctr = 0;
	/**
	 * We want to read serial ports and write to the network table
	 */
	public void serialEvent(SerialPortEvent ev) {
		int i;
		char c;
		int state;
		// this is actually a character of the int we want.  We did this so
		// we could inspect on the arduino sketch serial monitor
		// so the int will be the ascii value of the integer
		
		try {
			i = (char)serialReader.read();
			state = Integer.parseInt(Character.toString((char)i));
//			System.out.println("Read "+i+" as "+state);
			
			// make sure it is one of our bytes
			if (state > 5) {
				System.out.println("Ignore "+state);
			}
			else {
				boolean clawClosed = false;
				String wristPos = "neutral";
				if ((state & 1) > 0) {
					clawClosed = true;
				}
				networkTable.getEntry(CLAW_POS).setBoolean(clawClosed);
				if ((state & 4) > 0) {
					wristPos = "DOWN";
					networkTable.getEntry(WRIST_POS).setNumber(-1);					
				}
				else if ((state & 2) > 0) {
					wristPos = "UP";
					networkTable.getEntry(WRIST_POS).setNumber(1);
				}
				else {
					networkTable.getEntry(WRIST_POS).setNumber(0);
				}
				System.out.println(String.format("%d Wrist %s and claw %s", ctr++, wristPos, clawClosed ? "closed" : "open"));
			}
		} catch (IOException ex) {
			System.err.println("IO Error " + ex);
		}
	}
	
}