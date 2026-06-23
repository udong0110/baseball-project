package baseball.domain.stat;

import baseball.domain.player.Pitch;
import baseball.domain.player.PitchType;
import baseball.domain.player.PlayerPosition;
import baseball.exception.InvalidPitchMetricException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PitcherStat extends PlayerStat {

    private double era;
    private final Map<PitchType, Pitch> pitch;
    private Pitch strongPitch;

    public PitcherStat(double era, Map<PitchType,Pitch> pitch, Pitch strongPitch) {
        if (era > 100.0 || era < 0) {
            throw new InvalidPitchMetricException("era는 음수이거나 100이 넘어갈 수 없습니다.");
        }
        if (pitch == null || strongPitch == null) {
            throw new IllegalArgumentException("구종 입력실패");
        }
        this.pitch = pitch;
        this.strongPitch = strongPitch;
        this.era = era;
    }

    @Override
    public String toString() {
        List<String> pitchList = new ArrayList<>();
        for (Pitch p : pitch.values()) {
            pitchList.add(p.toString());
        }
        String pitchInfo = String.join(", ", pitchList);


        return "| era=" + era +
                " | 구종= {" + pitchInfo +"}"+
                " | 핵심구종=" + strongPitch;
    }
}
