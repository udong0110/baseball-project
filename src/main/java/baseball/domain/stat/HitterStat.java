package baseball.domain.stat;

import baseball.domain.player.Zone;
import baseball.domain.player.ZonePitch;
import baseball.exception.InvalidPitchMetricException;

public class HitterStat extends PlayerStat {

    private final double ops;
    private final double avg;

    private final ZonePitch powerZonePitch;
    private final ZonePitch weakZonePitch;


    public HitterStat(double ops, double avg, ZonePitch powerZonePitch, ZonePitch weakZonePitch) {
        if (avg < 0.0 || avg > 1.0) {
            throw new InvalidPitchMetricException("타율 수치 오류(0.0 ~ 1.0 사이여야 함): " + avg);
        }
        if (ops < 0.0 || ops > 2.0) {
            throw new InvalidPitchMetricException("OPS 수치 오류(음수 불가): " + ops);
        }

        if ((powerZonePitch == null) || (weakZonePitch == null)) {
            throw new IllegalArgumentException("[약점/강점 입력 실패]");
        }

        if (powerZonePitch.equals(weakZonePitch)) {
            throw new IllegalArgumentException("약점과 강점이 같으면 안됩니다.");
        }
        this.powerZonePitch = powerZonePitch;
        this.weakZonePitch = weakZonePitch;

        this.ops = ops;
        this.avg = avg;

    }

    public double getOps() {
        return ops;
    }

    public double getAvg() {
        return avg;
    }

    public ZonePitch getPowerZonePitch() {
        return powerZonePitch;
    }

    public ZonePitch getWeakZonePitch() {
        return weakZonePitch;
    }

    @Override
    public String toString() {
        return "| ops=" + ops +
                " | avg=" + avg+
                " | 강점=" + powerZonePitch +
                " | 약점=" + weakZonePitch;
    }
}
