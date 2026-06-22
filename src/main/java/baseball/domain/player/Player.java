package baseball.domain.player;

import java.util.Objects;

public class Player {

    private final String name;
    private Team team;
    private final HandType handType;

    public Player(String name, Team team, HandType hand) {
        if (name == null || name.trim().isEmpty() || name.length() > 10) {
            throw new IllegalArgumentException("이름 입력 실패.");
        }
        if (team == null || hand == null) {
            throw new IllegalArgumentException("팀 이름 혹은 주손잡이 입력 실패");
        }
        this.name = name;
        this.team = team;
        this.handType = hand;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(getName(), player.getName()) && getTeam() == player.getTeam() && getHandType() == player.getHandType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, team);
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

    @Override
    public String toString() {
        return "이름: " + name + ", 팀: " + team;
    }
}
