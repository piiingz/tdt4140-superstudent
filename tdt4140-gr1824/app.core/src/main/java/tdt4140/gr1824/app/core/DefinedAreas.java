package tdt4140.gr1824.app.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class DefinedAreas implements Iterable<Area> {
	
	
	public static final Area glos = new Area(new Location(63.419954, 10.398281), new Location(63.419954, 10.409288), 
			new Location(63.415452, 10.398281), new Location(63.415452, 10.409288));
	
	public static final Area sitTrening = new Area(new Location(63.421337, 10.403785), new Location(63.421337, 10.405688), 
			new Location(63.420809, 10.403785), new Location(63.420809, 10.405688));
	
	public static final Area samfundet = new Area(new Location(63.422726, 10.394794), new Location(63.422726, 10.396044), 
			new Location(63.422191, 10.394794), new Location(63.422191, 10.396044));

	
	public static Collection<Area> areas = new ArrayList<Area>(Arrays.asList(glos, sitTrening, samfundet));
	
	public Iterator<Area> iterator() {
		return areas.iterator();
		
	}
}
