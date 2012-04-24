package de.lemo.dms.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import de.lemo.dms.core.ConnectorManager;

/**
 * load the whole database
 *  * @author Boris Wenzlaff
 *
 */
@Path("/loadwholedatabase")
public class ServiceLoadWholeDatabase extends BaseService {
	
	@GET @Produces(MediaType.APPLICATION_JSON)
	public JSONObject loadWholeDatabase() {
		super.logger.info("call for service: loadWholeDatabase");
		JSONObject rs = new JSONObject();
		ConnectorManager cm = ConnectorManager.getInstance();
		Boolean loaded = cm.getData();
		try {
			rs.put("loaded", loaded);
			rs.put("loaded", loaded);
		} catch (JSONException e) {
			super.logger.warn(e.getMessage());
		}
		return rs;
	}
}