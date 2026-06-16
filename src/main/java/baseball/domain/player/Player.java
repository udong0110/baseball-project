package baseball.domain.player;

import java.util.Objects;

public class Player {

    private final String name;
    private Team team;
    private final HandType handType;

    public Player(String name, Team team, HandType hand) {
        this.name = name;
        this.team = team;
        this.handType = hand;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) && team == player.team;
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
}
