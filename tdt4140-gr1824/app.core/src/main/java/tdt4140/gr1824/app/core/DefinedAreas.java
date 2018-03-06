package tdt4140.gr1824.app.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class DefinedAreas implements Iterable<Area> {
	
	
	public static final Area glos = new Area("glos", new Location(63.4305149,10.3950528), new Location(63.4305149,10.408700), 
			new Location(63.415836,10.3950528), new Location(63.415836,10.408700));
	
	public static final Area sitTrening = new Area("sitTrening", new Location(63.420813, 10.404738), new Location(63.421076, 10.405420), 
			new Location(63.421266, 10.405052), new Location(63.421217, 10.404931), new Location(63.421302, 10.404762), new Location(63.421100, 10.404210));
	
	public static final Area samfundet = new Area("samfundet", new Location(63.422547, 10.394912), new Location(63.422636, 10.395427), new Location(63.422569, 10.396070), 
			new Location(63.422226, 10.395754), new Location(63.422317, 10.395164));
	
	public static final Area nowhere = new Area("nowhere", new Location(0.0, 0.0), new Location(0.0, 0.0));

	
	public static Collection<Area> areas = new ArrayList<Area>(Arrays.asList(glos, sitTrening, samfundet));
	
	public Iterator<Area> iterator() {
		return areas.iterator();
		
	}
}
