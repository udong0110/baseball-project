package baseball.domain.player;

public enum HandType {
    LEFT("왼손"),
    RIGHT("오른손"),
    SWITCH("양손");

    private final String handType;

    HandType(String handType) {
        this.handType = handType;
    }

    public String getHandType() {
        return handType;
    }

    public static HandType findType(String string) {

        for (HandType type : HandType.values()) {

            if (type.handType.equals(string)) {
                return type;
            }
        }
        return null;
    }
}
