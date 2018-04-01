package team3543.powerglove;

import java.io.*;
import gnu.io.*;

/**
 * Open a serial port using Java Communications.
 *
 * @author Ian F. Darwin, http://www.darwinsys.com/
 */
class CommPortOpen {
	/** How long to wait for the open to finish up. */
	public static final int TIMEOUTSECONDS = 30;
	/** The baud rate to use. */
	public static final int BAUD = 9600;
	
	/** The input stream */
	protected BufferedInputStream is;
	/** The output stream */
	protected PrintStream os;
	/** The chosen Port Identifier */
	CommPortIdentifier thePortID;
	/** The chosen Port itself */
	CommPort thePort;

	public static void main(String[] argv)
			throws IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {

		new CommPortOpen(argv[0]).converse();

		System.exit(0);
	}

	/* Constructor */
	public CommPortOpen(String portIdentifier)
			throws IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {

		// Get the CommPortIdentifier.
		thePortID = CommPortIdentifier.getPortIdentifier(portIdentifier);

		// Now actually open the port.
		// This form of openPort takes an Application Name and a timeout.
		//
		System.out.println("Trying to open " + thePortID.getName() + "...");
		
		switch (thePortID.getPortType()) {
		case CommPortIdentifier.PORT_SERIAL:
			thePort = thePortID.open(this.getClass().getName(), TIMEOUTSECONDS * 1000);
			SerialPort myPort = (SerialPort) thePort;

			// set up the serial port
			myPort.setSerialPortParams(BAUD, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			break;

		default: // Neither parallel nor serial??
			throw new IllegalStateException("Unknown/unwanted port type " + thePortID);
		}

		// Get the input and output streams
		// Printers can be write-only
		try {
			is = new BufferedInputStream(thePort.getInputStream());
		} catch (IOException e) {
			System.err.println("Can't open input stream: write-only");
			is = null;
		}
		os = new PrintStream(thePort.getOutputStream(), true);
	}

	/**
	 * This method will be overridden by non-trivial subclasses to hold a
	 * conversation.
	 */
	protected void converse() throws IOException {

		System.out.println("Ready to read and write port.");

		// Input/Output code not written -- must subclass.

		// Finally, clean up.
		if (is != null)
			is.close();
		os.close();
	}
}