/**
 * File ./main/java/de/lemo/dms/core/ConnectorGetDataWorkerThread.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.core;

import org.apache.log4j.Logger;
import de.lemo.dms.connectors.IConnector;

/**
 * Background worker thread for the connectors.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
public class ConnectorGetDataWorkerThread extends Thread {

	private final IConnector connector;
	private final Logger logger = Logger.getLogger(this.getClass());

	public ConnectorGetDataWorkerThread(final IConnector connector) {
		this.connector = connector;
	}

	public IConnector getConnector() {
		return connector;
	}

	@Override
	public void run() {
		this.logger.info("Running connector update " + this.connector);
		this.connector.getData();
	}
}
