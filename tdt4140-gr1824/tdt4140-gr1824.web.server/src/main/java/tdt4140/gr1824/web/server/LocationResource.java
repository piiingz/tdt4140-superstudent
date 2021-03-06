package tdt4140.gr1824.web.server;

import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import tdt4140.gr1824.app.core.Interpreter;
import tdt4140.gr1824.app.core.NMEAdata;


@Path("loc")
public class LocationResource {
	
	Interpreter interpreter = new Interpreter();
	
//Takes JSON objects from a restful server and delivers them as NMEAdata objects to interpreter


	@POST
	@Path("location")
	@Consumes(MediaType.APPLICATION_JSON)
	public NMEAdata sendLocation(NMEAdata data) throws SQLException {
		if (data.getQuality() == 4) {
			interpreter.receive(data);			
			return data;
		}
		return null;
	}
	
	
}
