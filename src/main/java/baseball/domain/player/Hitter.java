package baseball.domain.player;

public class Hitter extends Player {

    private Zone hitterpowerZone;
    private Zone hitterWeakZone;

    public Hitter(String name, Team team, HandType hand, Zone hitterpowerZone, Zone hitterWeakZone) {
        super(name, team, hand);
        if (hitterpowerZone == null || hitterWeakZone == null) {
            throw new IllegalArgumentException("존 입력실패.");
        }

        this.hitterpowerZone = hitterpowerZone;
        this.hitterWeakZone = hitterWeakZone;
    }

    public Zone getHitterpowerZone() {
        return hitterpowerZone;
    }

    public Zone getHitterWeakZone() {
        return hitterWeakZone;
    }
}
