package baseball.domain.player;

public enum Zone {
    LEFTHIGH(1,"좌측 상단"),
    MIDDLEHIGH(2,"중앙 상단"),
    RIGHTHIGH(3,"우측 상단"),
    LEFTMIDDLE(4,"좌측 중앙"),
    MIDDLE(5,"중앙"),
    RIGHTMIDDLE(6,"우측 중앙"),
    LEFTLOW(7,"좌측 하단"),
    MIDDLELOW(8,"중앙 하단"),
    RIGHTLOW(9,"우측 하단");


    private int zoneNumber;
    private String zoneHeight;

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
}
