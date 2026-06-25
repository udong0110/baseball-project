package baseball.domain.player;

import java.util.Map;

public class Pitcher extends Player {


    public Pitcher(String name, Team team, HandType hand, PlayerPosition playerPosition) {
        super(name, team, hand, playerPosition);

    }



    @Override
    public String toString() {
        return "[투수] " +
                "이름: " + getName() +
                " | 팀: " + getTeam() +
                " | 투구: " + getHandType() +
                " | 포지션: " + getPlayerPosition();

    }
}
