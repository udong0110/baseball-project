package baseball.domain.player;

import baseball.exception.InvalidPitchMetricException;

import java.util.Objects;

public class Player {

    private final String name;
    private final Team team;
    private final HandType handType;
    private final PlayerPosition playerPosition;

    public Player(String name, Team team, HandType hand, PlayerPosition playerPosition) {

        if (name==null ||name.length() > 10||name.trim().isEmpty()||team == null || hand == null || playerPosition == null) {
            throw new IllegalArgumentException("(팀) 이름 및 투타 및 포지션 입력 실패");
        }
        this.name = name;
        this.team = team;
        this.handType = hand;
        this.playerPosition = playerPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Hitter,Picther 클래스로 선수를 만들기때문에 Player 클래스로 형변환 시켜줘야함
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;
        return Objects.equals(name, player.name) &&
                team == player.team &&
                handType == player.handType
                && playerPosition==player.playerPosition;
    }


    @Override
    public int hashCode() {
        return Objects.hash(getName(), getTeam(), getHandType());
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public HandType getHandType() {
        return handType;
    }

    public PlayerPosition getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public String toString() {
        return "이름:  | " + name + " | 팀: " + team+" | 포지션: "+playerPosition;
    }
}
