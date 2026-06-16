package baseball.domain.player;

public enum HandType {
    LEFT("left"),
    RIGHT("right"),
    SWITCH("switch");

    private final String handType;

    HandType(String handType) {
        this.handType = handType;
    }

    public String getHandType() {
        return handType;
    }
}
