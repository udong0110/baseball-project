package baseball.analyzer;

import baseball.domain.player.*;

import baseball.domain.stat.HitterStat;
import baseball.domain.stat.PitcherStat;
import baseball.exception.PlayerNotFound;

import java.util.Map;

public class PitchingAnalyzer {

    // 현재 클래스에 변수가 너무많음 여러 클래스로 쪼개서 이 클래스에서 조립하는 방식으로 리팩토링 필요

    // 타자, 투수 정보
    private final Hitter hitter ;
    private final Pitcher pitcher ;
    private final PitcherStat pitcherStat;
    private final HitterStat hitterStat;

    // 안타확률, 범타확률
    private  double hitPercent;
    private double outPercent;

    // 검증 로직을 위한 필드변수
    private final boolean hasPowerMatch;
    private final boolean hasWeakMatch;
    private final PitchType hitterPowerType; // 타자의 강점 구종
    private final PitchType hitterWeakType; // 타자의 약점 구종
    private final PitchType pitcherPowerType; // 투수의 강점 구종
    private final Map<PitchType, Pitch> pitchTypes; // 투수가 가지고 있는 구종
    private final Pitch strongPitch; // 투수의 강점 구종중 스피드,rpm을 가져오기 위한 변수




    public PitchingAnalyzer(Hitter hitter, HitterStat hitterStat, Pitcher pitcher, PitcherStat pitcherStat) {
        if (hitter == null || pitcher == null) {
            throw new PlayerNotFound("[검색 오류] 선수를 찾지 못하였습니다.");
        }
        this.hitter = hitter;
        this.pitcher = pitcher;
        this.hitterStat = hitterStat;
        this.pitcherStat = pitcherStat;
        this.hitPercent = hitterStat.getAvg();

        hitterPowerType = hitterStat.getPowerZonePitch().getPitchType(); // 타자의 강점 구종
        hitterWeakType = hitterStat.getWeakZonePitch().getPitchType(); // 타자의 약점 구종
        strongPitch = pitcherStat.getStrongPitch();
        pitcherPowerType = strongPitch.getPitchType();  // 투수의 강점 구종
        pitchTypes = pitcherStat.getPitch();//투수가 가지고 있는 구종

        this.hasPowerMatch = pitchTypes.containsKey(hitterPowerType);
        this.hasWeakMatch = pitchTypes.containsKey(hitterWeakType);

    }


    // avg를 기준으로 안타확률 조정
    public void simulationPitching() {
        System.out.println("선수 데이터 분석 중......");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("가상 시뮬레이션 중......");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("데이터 매칭 기반 매치업 확률: ");


        // 투타유형으로 안타 기댓값 조정하는 메서드
        compareHandType();
        // rpm, 구속 각각 리그 평균과 비교하여 보정치, 안타 기댓값 조정하는 메서드
        compareRpmSpeed();
        // 타자의 강/약점 구종에 따라 보정치, 안타 기댓값 조정하는 메서드
        compareZonePitch();


        // 표본이 적지만 타율이 0할인 타자는 초기화
        // 후에 타석수 필드를 추가해 정교하게 리팩토링 예정
        if (hitPercent <= 0.00) {
            hitPercent = 0.05;
        }
        outPercent = 1 - hitPercent;
        System.out.printf("-> 안타확률 = %.1f%%%n", hitPercent * 100);
        System.out.printf("-> 범타확률 = %.1f%%%n", outPercent * 100);
    }


    public void solutionDesign() {
        //  타자를 위한 솔루션
        //  타자의 강점 구종
        hittingSolution();


        // 투수를 위한 솔루션
        // 타자의 약점, 강점 존과 구종을 보고 해당 위치와 구종을 피하거나 결정구로 던짐
        pitchingSolution();
    }

    private void hittingSolution() {

    }

    private void pitchingSolution() {

        System.out.println(pitcher.getName() + "(" + pitcher.getHandType() + ")" + " 피칭 디자인 분석 중...\n\n\n\n\n\n");
        if (this.hitPercent >= 0.33) {
            System.out.println(hitter.getName() + "(" + hitter.getHandType() + ")의 안타 확률이 극도로 높습니다!! 무조건 유인구로 어렵게 승부하세요!!\n");
        } else if (this.hitPercent >= 0.30) {
            System.out.println(hitter.getName() + "(" + hitter.getHandType() + ")의 안타 확률이 높으므로 실투를 조심하고 신중하게 접근할 필요가 있습니다.\n");
        } else if (this.hitPercent >= 0.25) {
            System.out.println(hitter.getName() + "(" + hitter.getHandType() + ")은(는) 무난한 타자입니다. 자신 있게 스트라이크 존 구석을 공략하세요.\n");
        } else {
            System.out.println(hitter.getName() + "(" + hitter.getHandType() + ")은(는) 현재 타이밍이 맞지 않습니다. 정면 승부를 추천합니다.\n");
        }

        countPitching();

        finalPitching();
    }

    private void finalPitching() {
        System.out.println("==================위닝 샷 공략======================");
        // 타자의 약점존을 공략하는 방식으로 설정
        // 추가적으로 타자의 약점 구종을 타자의 약점 존으로 던지는 방식
        String hitterWeakZone = hitterStat.getWeakZonePitch().getZone().getZoneHeight();
        if (pitchTypes.containsKey(hitterWeakType)) {
            System.out.println("[약점 공략] "+ hitterWeakZone + "의 위치에 " + hitterWeakType + "을 던져 삼진을 유도하세요.");
        } else {
            System.out.println(pitcher.getName() + " 이(가) 보유한 구종 중 타자의 약점 구종이 존재하지 않습니다.");
            System.out.println("타자의 약점 코스인" + hitterWeakZone + "의 위치에" + hitterPowerType + " 외에 가장 자신있는 구종을 던지세요.");
        }
    }

    private void countPitching() {
        System.out.println("==================초구/카운트 공략======================");
        // 우투우투, 우투좌타(우투양타), 좌투좌타, 좌투우타(좌투양타) 4가지 케이스로 나누기
        HandPitcherType pitcherHand = pitcher.getHandType();
        HandHitterType hitterHand = hitter.getHandType();

        double finalScore = getFinalScore();
        String hitterPowerZone = hitterStat.getPowerZonePitch().getZone().getZoneHeight();
        String hitterWeakZone = hitterStat.getWeakZonePitch().getZone().getZoneHeight();

        if (hitterHand == HandHitterType.SWITCH) {
            hitterHand = (pitcherHand == HandPitcherType.RIGHT) ? HandHitterType.LEFT : HandHitterType.RIGHT;
        }

        String recommendedZone = "";

        if (hitterHand == HandHitterType.RIGHT) {
            recommendedZone = "좌측";  // 투수기준 좌측 타자기준 바깥쪽
            zoneAdvice(finalScore, hitterPowerZone, recommendedZone);
        } else {
            recommendedZone = "우측";  // 투수기준 우측 타자기준 좌측
            zoneAdvice(finalScore, hitterPowerZone, recommendedZone);
        }


        if (pitchTypes.containsKey(hitterPowerType)) {
            System.out.println(pitcher.getName() + "이(가) 보유한 구종 중 타자의 강점 구종이 존재합니다.");
            System.out.println(hitterPowerType+" 외의 구종으로 상대하는 것이 좋습니다.");
        } else {
            System.out.println(pitcher.getName() + "이(가) 보유한 구종 중 타자의 강점 구종이 존재하지 않습니다." );
            System.out.println(pitcherPowerType + "을(를) 과감하게 던지는 것이 좋습니다.");
        }


    }

    private void zoneAdvice(double finalScore, String hitterPowerZone, String recommendedZone) {
        if (finalScore >= 3.0) {
            System.out.println("타자의 핫존을 무시하고 구위를 믿고 몸쪽으로 과감하게 던지는 피칭이 좋습니다.");
        } else if (finalScore >= 1.0) {
            System.out.println(recommendedZone+" 바깥쪽으로 던지는 것을 추천하나 몸쪽을 신중하게 공략하는 것도 좋은 선택지입니다.");
        } else if (finalScore < 1.0) {
            if (hitterPowerZone.contains(recommendedZone)) {
                System.out.println("바깥쪽이 타자의 강점이고 구위가 좋지 못하므로 몸쪽으로 던지는게 좋습니다. 다만 절대 실투가 되지 않게 바짝 붙여 던지는 것을 추천합니다.");
            } else {
                System.out.println(recommendedZone+" 바깥쪽으로 안전하게 던지는 것을 추천합니다.");
            }
        }
    }

    private void compareZonePitch () {
            // 투수 보유 구종 중 타자의 강/약점 구종에 걸릴 때 차등 보정치 적용
            // 투수가 타자보다 유리하다는 전제로 보정치 적용
            boolean highVolatilityMatched = (pitcherPowerType == hitterPowerType) || (pitcherPowerType == hitterWeakType); // 부 구종보다 주 구종에 걸릴 경우, 증감 변동성이 높음


            // 투수 보유 구종에 타자 강/약점 구종이 존재할 때 안타 기댓값 상승
            // 1-1. 타자의 강점 구종이 투수 구종에 포함될 때
            if (hasPowerMatch) {
                if (highVolatilityMatched) {
                    // 타자의 강점 구종이 투수의 주 구종이라면 어쩔수 없이 던지지만 승부를 피한다는 선택지가 있으므로 5% 증가 적용
                    System.out.println("[타자 강점 매칭] 투수의 주 구종이 타자의 강점 구종에 걸리므로 안타 기댓값이 5% 증가합니다.");
                    hitPercent *= 1.05;
                    System.out.println();
                } else {
                    // 타자의 강점 구종이 투수의 부 구종이라면 피한다는 선택지가 있으므로 2% 증가 적용
                    System.out.println("[타자 강점 매칭] 투수의 보유 구종이 타자의 강점 구종에 걸리므로 안타 기댓값이 2% 증가합니다.");
                    hitPercent *= 1.02;
                    System.out.println();
                }
            }
            // 1-2 타자의 약점 구종이 투수 구종에 포함될 때
            if (hasWeakMatch) {
                if (highVolatilityMatched) {
                    // 타자의 약점 구종이 투수의 주 구종이라면 투수는 주 구종으로 승부를 볼 확률이 높아서 10% 감소 적용
                    System.out.println("[타자 약점 매칭] 투수의 주 구종이 타자의 약점 구종에 걸리므로 안타 기댓값이 10% 감소합니다.");
                    hitPercent *= 0.9;
                    System.out.println();
                } else {
                    System.out.println("[타자 약점 매칭] 투수의 보유 구종이 타자의 약점 구종에 걸리므로 안타 기댓값이 5% 감소합니다.");
                    hitPercent *= 0.95;
                    System.out.println();
                }
            }
            if (!hasPowerMatch && !hasWeakMatch) {
                System.out.println("구종에 따른 강/약점이 존재하지 않아 안타 기댓값 증감이 이뤄지지 않습니다.");
            }
        }

        private void compareRpmSpeed () {
            // speed와 rpm은 각구종을 비교하지 않고 주구종을 비교
            // (리팩토링) 평균으로 내기
            double finalScore = getFinalScore();

            if (finalScore >= 3.0) {
                System.out.println("투수의 구위가 리그 최상위권이므로 안타 기댓값이 8% 감소합니다.");
                hitPercent *= 0.92;
                System.out.println();
            } else if (finalScore >= 1.0) {
                System.out.println("투수의 구위가 리그 상위권이므로 안타 기댓값이 4.5% 감소합니다.");
                hitPercent *= 0.955;
                System.out.println();
            } else if (finalScore <= -3) {
                System.out.println("투수의 구위가 리그 최하권이므로 안타 기댓값이 8% 증가합니다.");
                hitPercent *= 1.08;
            } else if (finalScore <= -1) {
                System.out.println("투수의 구위가 리그 하위권이므로 안타 기댓값이 4.5% 증가합니다.");
                hitPercent *= 1.045;
            } else {
                System.out.println("투수의 구위가 리그 평균이므로 안타 기댓값의 변화가 없습니다.");
            }
        }

        private double getFinalScore () {
            double ballSpeed = strongPitch.getBallSpeed();
            double avgSpeed = pitcherPowerType.getAvgSpeed();
            double rpm = strongPitch.getRpm();
            double avgRpm = pitcherPowerType.getAvgRpm();

            // speed와 rpm을 평균에서 빼서 5단계 보정치를 주게 한다.
            // 구속은 +2km , Rpm은 +70rpm을 기준으로 상위권을 나눌 것이며 구속에 0.6, rpm에 0.4 가중치를 둘 것(rpm과 speed를 각각 다르게 보정하는 이유는 타자 스탯 데이터에 rpm보다 구속에 더 영향을 받기 때문)
            // 최종적으로 finalScore가 1을 기준으로 1이상은 상위권~최상위권 1미만은 평균~최하위권으로 나눌 것임

            double speedDiff = ballSpeed - avgSpeed;
            double rpmDiff = rpm - avgRpm;
            double speedScore = speedDiff / 2;
            double rpmScore = rpmDiff / 70;
            return (speedScore * 0.6) + (rpmScore * 0.4);
        }

        public void compareHandType () {
            HandPitcherType pitcherHand = pitcher.getHandType();
            HandHitterType hitterHand = hitter.getHandType();

            if (pitcherHand == HandPitcherType.RIGHT && hitterHand == HandHitterType.RIGHT) {
                System.out.println("투수 " + pitcher.getName() + "(우투), 타자 " + hitter.getName() + " (우타)이므로 안타 기댓값이 2% 감소합니다.");
                hitPercent *= 0.98;
                System.out.println();

            } else if (pitcherHand == HandPitcherType.LEFT && hitterHand == HandHitterType.LEFT) {
                System.out.println("투수 " + pitcher.getName() + "(좌투), 타자 " + hitter.getName() + "(좌타)이므로 안타 기댓값이 4% 감소합니다.");
                hitPercent *= 0.96;
                System.out.println();

            } else {
                System.out.println("역상성 매치업이므로 안타 기댓값이 2% 증가합니다.");
                hitPercent *= 1.02;
                System.out.println();

            }
        }
    }


