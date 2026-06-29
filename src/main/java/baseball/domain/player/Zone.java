package baseball.domain.player;

public enum Zone {
    LEFT_HIGH(1,"좌측 상단"),
    MIDDLE_HIGH(2,"중앙 상단"),
    RIGHT_HIGH(3,"우측 상단"),
    LEFT_MIDDLE(4,"좌측 중앙"),
    MIDDLE(5,"중앙"),
    RIGHT_MIDDLE(6,"우측 중앙"),
    LEFT_LOW(7,"좌측 하단"),
    MIDDLE_LOW(8,"중앙 하단"),
    RIGHT_LOW(9,"우측 하단");


    private final int zoneNumber;
    private final String zoneHeight;

    Zone(int zoneNumber,String zoneHeight) {
        this.zoneNumber = zoneNumber;
        this.zoneHeight = zoneHeight;
    }

    public int getZoneNumber() {
        return zoneNumber;
    }

    public String getZoneHeight() {
        return zoneHeight;
    }

    public static Zone findFromNNumber(int zoneNumber) {
        for (Zone zone : Zone.values()) {
            if (zone.getZoneNumber() == zoneNumber) {
                return zone;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return zoneHeight;
    }
}
