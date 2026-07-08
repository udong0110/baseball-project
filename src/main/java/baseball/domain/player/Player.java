package baseball.domain.player;

import baseball.exception.InvalidPitchMetricException;

import java.util.Objects;

public abstract class Player {

    private final String name;
    private final Team team;
    private final int backNumber;

    public Player(String name, Team team, int backNumber) {
        if (name == null || name.length() > 10 || name.trim().isEmpty() || team == null || backNumber <= 0 || backNumber > 999) {
            throw new IllegalArgumentException("(팀) 이름 및 등번호 기입 필수");
        }
        this.name = name;
        this.team = team;
        this.backNumber = backNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Hitter,Picther 클래스로 선수를 만들기때문에 Player 클래스로 형변환 시켜줘야함
        if (!(o instanceof Player)|| getClass() != o.getClass()) return false;  // getClass를 사용해서 Pitcher,Hitter가 같은 투타겸업 선수가 들어와도 Map에 들어가짐

        Player player = (Player) o;
        return Objects.equals(name, player.name) &&
                team == player.team &&
                backNumber == player.backNumber;

    }


    @Override
    public int hashCode() {
        return Objects.hash(getName(), getTeam(), getBackNumber());
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public int getBackNumber() {
        return backNumber;
    }
}
