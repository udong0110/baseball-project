package baseball.domain.player;

import java.util.List;

public class Pitcher extends Player {

    private List<PitchType> pitchType;
    private PitchType strongPitchType;


    public Pitcher(String name, Team team, HandType hand, List<PitchType> pitchType, PitchType strongPitchType) {
        super(name, team,hand);
        if (pitchType == null || strongPitchType == null) {
            throw new IllegalArgumentException("구종 입력실패");
        }
        this.pitchType = pitchType;
        this.strongPitchType = strongPitchType;
    }

    public List<PitchType> getPitchType() {
        return pitchType;
    }

    public PitchType getStrongPitchType() {
        return strongPitchType;
    }

    @Override
    public String toString() {
        return "Pitcher " +
                "이름: " + getName() +
                ", 팀: " + getTeam()+
                ", 구종=" + pitchType +
                ", 주무기=" + strongPitchType;
    }
}
