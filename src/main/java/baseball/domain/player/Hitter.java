package baseball.domain.player;

public class Hitter extends Player {
    private final HandHitterType handType;
    private final HitterPosition position;

    public Hitter(String name, Team team, int backNumber, HandHitterType hand, HitterPosition position) {
        super(name, team, backNumber);
        if (hand == null || position == null) {
            throw new IllegalArgumentException("[기입 실패 오류] : 손잡이,포지션 입력 필수");
        }
        this.handType = hand;
        this.position = position;
    }

    public HandHitterType getHandType() {
        return handType;
    }

    public HitterPosition getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "[타자] " +
                "이름: " + getName() +
                " | 팀: " + getTeam() +
                " | 등번호: "+ getBackNumber() +
                " | 타석: " + handType +
                " | 포지션: " + position;
    }
}
