package tdt4140.gr1824.web.server;

import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import tdt4140.gr1824.app.core.Interpreter;


@Path("loc")
public class LocationResource {
	
	Interpreter interpreter = new Interpreter();

	@POST
	@Path("location")
	@Consumes(MediaType.APPLICATION_JSON)
	public NMEAdata sendLocation(NMEAdata NMEA) throws SQLException {
		System.out.println(NMEA);
		System.out.println(NMEA.getId());
		//collector.collect(NMEA);
		return NMEA;
	}
	
	@POST
	@Path("test")
	public Dude createDude(Dude dude) {
		System.out.println(dude);
		return dude;
	}
	
}
