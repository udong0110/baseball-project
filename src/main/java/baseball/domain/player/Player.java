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
        if (this == o) return true;
        // Hitter,Picther 클래스로 선수를 만들기때문에 Player 클래스로 형변환 시켜줘야함
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;
        return Objects.equals(name, player.name) &&
                team == player.team &&
                handType == player.handType;
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

    @Override
    public String toString() {
        return "이름: " + name + ", 팀: " + team;
    }
}
