package baseball.domain.player;

import java.util.Map;

public class Pitcher extends Player {

    private final HandPitcherType handType;
    private final PitcherPosition position;

    public Pitcher(String name, Team team, int backNumber, HandPitcherType handType, PitcherPosition position) {
        super(name, team, backNumber);
        if (handType == null || position == null) {
            throw new IllegalArgumentException("[기입 실패 오류] : 손잡이,포지션 입력 필수");
        }
        this.handType = handType;
        this.position = position;
    }


    public HandPitcherType getHandType() {
        return handType;
    }

    public PitcherPosition getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "[투수] " +
                "이름: " + getName() +
                " | 팀: " + getTeam() +
                " | 등번호: " + getBackNumber() +
                " | 투구: " + handType.getDescription() +
                " | 포지션: " + position + "(" + position.getPositionName() + ")";

    }
}
