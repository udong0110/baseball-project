package baseball.domain.player;

import baseball.exception.InvalidPitchMetricException;

public class Pitch {

    private PitchType pitchType;
    private int rpm;
    private int ballSpeed;

    public Pitch(PitchType pitchType, int rpm,int speed) {
        if (rpm < 0 || rpm > 3500 || speed < 0 || speed > 175) {
            throw new InvalidPitchMetricException("수치가 범위를 벗어났습니다.");
        }
        if (pitchType == null) {
            throw new IllegalArgumentException("구종입력이 되지 않았습니다.");
        }
        this.pitchType = pitchType;
        this.rpm = rpm;
        this.ballSpeed = speed;

    }

    public PitchType getPitchType() {
        return pitchType;
    }

    public int getRpm() {
        return rpm;
    }

    public int getBallSpeed() {
        return ballSpeed;
    }

    public static Pitch createPitch(PitchType pitchType, int rpm,int speed) {
        return new Pitch(pitchType, rpm, speed);
    }

    @Override
    public String toString() {
        return pitchType+"[" + rpm + "rpm]" + " [" + ballSpeed + "km]";
    }

}
