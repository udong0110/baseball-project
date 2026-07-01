package baseball.domain.player;

public enum HandPitcherType {
    LEFT("좌투"),
    RIGHT("우투");
    // 스위치투수는 kbo역사상 존재하지 않았기 때문에 생략

    private final String description;

    HandPitcherType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static HandPitcherType findType(String input) {
        for (HandPitcherType type : HandPitcherType.values()) {
            if (type.description.equals(input) || type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        return null;
    }
}