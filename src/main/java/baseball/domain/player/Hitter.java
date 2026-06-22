package baseball.domain.player;

public class Hitter extends Player {

    private Zone hitterpowerZone;
    private Zone hitterWeakZone;

    public Hitter(String name, Team team, HandType hand, Zone hitterpowerZone, Zone hitterWeakZone) {
        super(name, team, hand);
        if ((hitterpowerZone == null) || (hitterpowerZone.getZoneNumber() < 1) || (hitterpowerZone.getZoneNumber() > 9)) {
            throw new IllegalArgumentException("존 입력실패.");
        }
        if ((hitterWeakZone == null) || (hitterWeakZone.getZoneNumber() < 1) || (hitterWeakZone.getZoneNumber() > 9)) {
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

    @Override
    public String toString() {
        return "Hitter " +
                "이름: " + getName() +
                ", 팀: " + getTeam()+
                ", 강점 존=" + hitterpowerZone +
                ", 약점 존=" + hitterWeakZone ;
    }
}
