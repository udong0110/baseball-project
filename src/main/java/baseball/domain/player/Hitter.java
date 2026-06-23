package baseball.domain.player;

public class Hitter extends Player {

    public Hitter(String name, Team team, HandType hand, PlayerPosition playerPosition) {
        super(name, team, hand, playerPosition);

    }


    @Override
    public String toString() {
        return "[타자] " +
                "이름: " + getName() +
                " | 팀: " + getTeam() +
                " | 타석: " + getHandType() +
                " | 포지션: " + getPlayerPosition()
                ;
    }
}
