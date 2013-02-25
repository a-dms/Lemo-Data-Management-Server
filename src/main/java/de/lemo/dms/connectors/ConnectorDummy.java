/**
 * File ./main/java/de/lemo/dms/connectors/ConnectorDummy.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import org.apache.log4j.Logger;

/**
 * dummy connector with sleep function for connector tests
 * 
 * @author Boris Wenzlaff
 */
public class ConnectorDummy extends AbstractConnector {

	private final int sleep = (60 * 1000);
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean testConnections() {
		return true;
	}

	@Override
	public void getData() {
		try {
			this.logger.info("connector dummy will load whole database");
			Thread.sleep(this.sleep);
		} catch (final InterruptedException e) {

			this.logger.warn("connector dummy throws exception at getData()");
		}
	}

	@Override
	public void updateData(final long fromTimestamp) {
		try {
			this.logger.info("connector dummy will update whole database");
			Thread.sleep(this.sleep);
		} catch (final InterruptedException e) {
			this.logger.warn("connector dummy throws exception at updateData()");
		}
	}
}
