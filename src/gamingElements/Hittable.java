package gamingElements;

import java.awt.geom.Area;

public interface Hittable {
    public Area getArea();
    public void kill();
    public boolean isKilled();
}
