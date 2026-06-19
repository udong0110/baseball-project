package baseball.domain.stat;

public class PitcherStat extends PlayerStat {

    private double era;

    public PitcherStat(double era) {
        if (era > 100.0 || era < 0) {
            throw new IllegalArgumentException("era는 음수이거나 100이 넘어갈 수 없습니다.");
        }
        this.era = era;
    }
}
