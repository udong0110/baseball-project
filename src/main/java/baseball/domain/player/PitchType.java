package baseball.domain.player;

public enum PitchType {

    FASTBALL("직구", 145.0, 2250.0),
    CUTTER("커터", 139.5, 2350.0),
    CURVEBALL("커브", 118.0, 2600.0),
    SINKER("싱커", 138.5, 2000.0),
    SLIDER("슬라이더", 133.5, 2470.0),
    CHANGEUP("체인지업", 131.5, 1700.0),
    FORKBALL("포크", 130.0, 1250.0),
    TWOSEAM("투심", 142.0, 2180.0),
    KNUCKLEBALL("너클", 105.0, 300.0);


    private final String pitchType;
    private final double avgSpeed;
    private final double avgRpm;


    PitchType(String pitchType, double avgSpeed, double avgRpm) {
        this.pitchType = pitchType;
        this.avgSpeed = avgSpeed;
        this.avgRpm = avgRpm;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public double getAvgRpm() {
        return avgRpm;
    }

    public String getPitchType() {
        return pitchType;
    }

    public static PitchType findPitchTypeByKorean(String inputPitchType) {
        for (PitchType pitchType : PitchType.values()) {
            if (pitchType.getPitchType().equals(inputPitchType)) {
                return pitchType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return pitchType;
    }
}
