package team3543.powerglove;

import java.io.IOException;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.ParallelPort;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * Read from a Serial port, notifying when data arrives. Simulation of part of
 * an event-logging service.
 * 
 * @version $Id: SerialReadByEvents.java,v 1.4 2004/04/11 23:50:40 ian Exp $
 * @author Ian F. Darwin, http://www.darwinsys.com/
 */
public class SerialReadByEvents extends CommPortOpen {

	public static void main(String[] argv)
			throws IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {

		new SerialReadByEvents(argv[0]).converse();
	}

	/* Constructor */
	public SerialReadByEvents(String commPortIdentifier)
			throws IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {

		super(commPortIdentifier);
	}

	/**
	 * Hold the conversation.
	 */
	protected void converse(SerialPortEventListener listener) throws IOException {

		if (!(thePort instanceof SerialPort)) {
			System.err.println("But I wanted a SERIAL port!");
			System.exit(1);
		}
		// Tell the Comm API that we want serial events.
		((SerialPort) thePort).notifyOnDataAvailable(true);
		try {
			((SerialPort) thePort).addEventListener(listener);
		} catch (TooManyListenersException ev) {
			// "CantHappen" error
			System.err.println("Too many listeners(!) " + ev);
			System.exit(0);
		}

	}

	public int read() throws IOException {
		
		return is.read();
	}
}
