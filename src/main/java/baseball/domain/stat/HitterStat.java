package baseball.domain.stat;

import baseball.domain.player.Zone;
import baseball.exception.InvalidPitchMetricException;

public class HitterStat extends PlayerStat {

    private final double ops;
    private final double avg;

    private final Zone hitterpowerZone;
    private final Zone hitterWeakZone;


    public HitterStat(double ops, double avg, Zone hitterpowerZone, Zone hitterWeakZone) {
        if (avg < 0.0 || avg > 1.0) {
            throw new InvalidPitchMetricException("타율 수치 오류(0.0 ~ 1.0 사이여야 함): " + avg);
        }
        if (ops < 0.0 || ops > 2.0) {
            throw new InvalidPitchMetricException("OPS 수치 오류(음수 불가): " + ops);
        }

        if ((hitterpowerZone == null) || (hitterpowerZone.getZoneNumber() < 1) || (hitterpowerZone.getZoneNumber() > 9)) {
            throw new IllegalArgumentException("존 입력실패.");
        }
        if ((hitterWeakZone == null) || (hitterWeakZone.getZoneNumber() < 1) || (hitterWeakZone.getZoneNumber() > 9)) {
            throw new IllegalArgumentException("존 입력실패.");
        }

        this.hitterpowerZone = hitterpowerZone;
        this.hitterWeakZone = hitterWeakZone;

        this.ops = ops;
        this.avg = avg;

    }

    public double getOps() {
        return ops;
    }

    public double getAvg() {
        return avg;
    }

    public Zone getHitterpowerZone() {
        return hitterpowerZone;
    }

    public Zone getHitterWeakZone() {
        return hitterWeakZone;
    }

    @Override
    public String toString() {
        return "| ops=" + ops +
                " | avg=" + avg+
                " | 강점=" + hitterpowerZone +
                " | 약점=" + hitterWeakZone ;
    }
}
