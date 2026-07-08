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
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;
        return Objects.equals(name, player.name) &&
                team == player.team &&
                backNumber == player.backNumber;
                // 이게 Player계열은 검증하지만 Hitter인지 Pitcher인지는 검증해주지 않는다
                // 따라서 같은팀 같은 이름 같은 등번호인 타자 투수를 저장소에 넣으려면 하나가 나머지를 덮어버릴 수 있다
                // 투타겸업인 선수가 나오면 리팩토링
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
