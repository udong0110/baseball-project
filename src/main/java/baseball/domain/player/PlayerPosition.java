package baseball.domain.player;

public enum PlayerPosition {

    // 투수
    SP("선발투수","SP"),
    RP("구원투수","RP"),
    CP("마무리투수","CP"),

    // 포수
    C("포수","C"),

    // 내야수
    FB("1루수","1B"),
    SB("2루수","2B"),
    TB("3루수","3B"),
    SS("유격수","SS"),

    // 외야수
    LF("좌익수","LF"),
    CF("중견수","CF"),
    RF("우익수","RF"),

    // 지명타자
    DH("지명타자","DH");

    private final String positionName;
    private final String abbreviation;

    PlayerPosition(String PositionName, String abbreviation) {
        this.positionName = PositionName;
        this.abbreviation = abbreviation;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static PlayerPosition getPlayerPosition(String inputPosition) {
        for (PlayerPosition position : PlayerPosition.values()) {
            if (position.abbreviation.equals(inputPosition)) {
                return position;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return positionName;
    }
}
