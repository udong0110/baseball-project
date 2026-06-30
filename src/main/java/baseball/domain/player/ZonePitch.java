package baseball.domain.player;

import baseball.exception.InvalidPitchMetricException;

public class ZonePitch {

    private final Zone zone;
    private final PitchType pitchType;

    public ZonePitch(Zone zone, PitchType pitchType) {
        if (zone == null || pitchType == null) {
            throw new IllegalArgumentException("[입력 오류]: 존과 구종을 설정해야 합니다.");
        }
        if ((zone.getZoneNumber() < 1) || (zone.getZoneNumber() > 9)) {
            throw new InvalidPitchMetricException("[존 범위 설정 오류]: 1~9의 숫자를 입력해야 합니다.");
        }
        this.zone = zone;
        this.pitchType = pitchType;
    }

    public Zone getZone() {
        return zone;
    }

    public PitchType getPitchType() {
        return pitchType;
    }

    @Override
    public String toString() {
        return zone + " " + pitchType;
    }
}
