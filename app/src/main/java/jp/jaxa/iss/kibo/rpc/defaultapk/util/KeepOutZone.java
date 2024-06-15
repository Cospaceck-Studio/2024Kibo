package jp.jaxa.iss.kibo.rpc.defaultapk.util;


import gov.nasa.arc.astrobee.types.Point;

public class KeepOutZone {
    public static final KeepOutZone KOZ_1 = new KeepOutZone(
            new Cuboid(10.87, -9.5, 4.27, 11.6, -9.45, 4.97),
            new Cuboid(10.25, -9.5, 4.97, 10.87, -9.45, 5.62)
    );

    public static final KeepOutZone KOZ_2 = new KeepOutZone(
            new Cuboid(10.87, -8.5, 4.97, 11.6, -8.45, 5.62),
            new Cuboid(10.25, -8.5, 4.27, 10.7, -8.45, 4.97)
    );

    public static final KeepOutZone KOZ_3 = new KeepOutZone(
            new Cuboid(10.87, -7.4, 4.27, 11.6, -7.35, 4.97),
            new Cuboid(10.25, -7.4, 4.97, 10.87, -7.35, 5.62)
    );

    public final Cuboid zone1;
    public final Cuboid zone2;
    public final double yPos;
    public final Point safe1;
    public final Point safe2;

    public KeepOutZone(Cuboid zone1, Cuboid zone2) {
        this.zone1 = zone1;
        this.zone2 = zone2;
        this.yPos = zone1.getMidPoint().getY();

        double safe1X = KOZ_1.zone1.getMidPoint().getX();
        double safe1Z = KOZ_1.zone2.getMidPoint().getZ();
        double safe2X = KOZ_1.zone2.getMidPoint().getX();
        double safe2Z = KOZ_1.zone1.getMidPoint().getZ();

        this.safe1 = new Point(safe1X, this.yPos, safe1Z);
        this.safe2 = new Point(safe2X, this.yPos, safe2Z);
    }
}
