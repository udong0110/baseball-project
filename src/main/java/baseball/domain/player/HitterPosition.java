package baseball.domain.player;

public enum HitterPosition {
    // 포수
    C("포수", "C"),

    // 내야수
    FB("1루수", "1B"),
    SB("2루수", "2B"),
    TB("3루수", "3B"),
    SS("유격수", "SS"),

    // 외야수
    LF("좌익수", "LF"),
    CF("중견수", "CF"),
    RF("우익수", "RF"),

    // 지명타자
    DH("지명타자", "DH");

    private final String positionName;
    private final String abbreviation;

    HitterPosition(String positionName, String abbreviation) {
        this.positionName = positionName;
        this.abbreviation = abbreviation;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static HitterPosition getPosition(String input) {
        for (HitterPosition position : HitterPosition.values()) {
            if (position.abbreviation.equalsIgnoreCase(input) || position.positionName.equals(input)) {
                return position;
            }
        }
        return null;
    }
}