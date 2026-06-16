package baseball.domain.player;

import java.util.List;

public class Pitcher extends Player {

    private List<PitchType> pitchType;
    private PitchType strongPitchType;


    public Pitcher(String name, Team team, HandType hand, List<PitchType> pitchType, PitchType strongPitchType) {
        super(name, team,hand);
        this.pitchType = pitchType;
        this.strongPitchType = strongPitchType;
    }

    public List<PitchType> getPitchType() {
        return pitchType;
    }

    public PitchType getStrongPitchType() {
        return strongPitchType;
    }
}
