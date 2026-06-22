package baseball.domain.player;

public enum PitchType {

    FASTBALL("직구"),
    CUTTER("커터"),
    CURVEBALL("커브"),
    SINKER("싱커"),
    SLIDER("슬라이더"),
    CHANGEUP("체인지업"),
    FORKBALL("포크"),
    TWOSEAM("투심"),
    KNUCKLEBALL("너클");


    private final String pitchType;

    PitchType(String pitchType) {
        this.pitchType = pitchType;
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
