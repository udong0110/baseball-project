package baseball.domain;

public enum Hand {
    LEFT("left"),
    RIGHT("right"),
    SWITCH("switch");

    private final String hand;

    Hand(String hand) {
        this.hand = hand;
    }
}
