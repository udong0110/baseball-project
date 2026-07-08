    package baseball.analyzer;

    import baseball.domain.player.*;
    import baseball.domain.stat.HitterStat;
    import baseball.domain.stat.PitcherStat;
    import baseball.exception.PlayerNotFound;

    import java.util.Map;

    public class PitchingAnalyzer {

        // 현재 클래스에 변수가 너무많음 여러 클래스로 쪼개서 이 클래스에서 조립하는 방식으로 리팩토링 필요

        // 타자, 투수 정보
        private final Hitter hitter;
        private final Pitcher pitcher;
        private final PitcherStat pitcherStat;
        private final HitterStat hitterStat;

        // 안타확률, 범타확률
        private double hitPercent;
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
            if (hitterStat == null || pitcherStat == null) {
                throw new IllegalArgumentException("[스탯 입력 실패] 스탯 정보를 확인하지 못했습니다.");
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
            System.out.println();
            System.out.println("선수 데이터 분석 중......");
            System.out.println("가상 시뮬레이션 중......");
            System.out.println();
            System.out.println("========================================================");
            System.out.println("  데이터 매칭 기반 매치업 기댓값 산출");
            System.out.println("========================================================");

            // 투타유형으로 안타 기댓값 조정하는 메서드
            compareHandType();
            // rpm, 구속 각각 리그 평균과 비교하여 보정치, 안타 기댓값 조정하는 메서드
            compareRpmSpeed();
            // 타자의 강/약점 구종에 따라 보정치, 안타 기댓값 조정하는 메서드
            compareZonePitch();

            // 표본이 적지만 타율이 0할인 타자는 2할로 조정
            // 타석수가 많지만 타율이 0할인 선수는 없기때문에 2할로 조정
            // 후에 타석수 필드를 추가해 정교하게 리팩토링 예정
            if (hitPercent <= 0.00) {
                hitPercent = 0.2;
            }
            outPercent = 1 - hitPercent;

            System.out.println("--------------------------------------------------------");
            System.out.printf("  안타 확률: %.1f%%%n", hitPercent * 100);
            System.out.printf("  범타 확률: %.1f%%%n", outPercent * 100);
            System.out.println("========================================================");
            System.out.println();
        }

        public void solutionDesign() {
            // 타자를 위한 솔루션
            hittingSolution();
            // 투수를 위한 솔루션
            pitchingSolution();
        }

        private void hittingSolution() {
            // 투수의 구위(finalscore)룰 보고 차등 접근법
            // 타자의 강점구종을 노려치는 방법
            System.out.println("================== 타자 타격 전략 솔루션 ==================");

            double finalScore = getFinalScore();

            // 구위 기반 솔루션
            System.out.print("  타격 접근법: ");
            if (finalScore >= 1.0) {
                System.out.println("상대 투수의 구위가 뛰어납니다. 배트를 짧게 쥐고 끈질기게 커트하며 실투나 볼넷을 유도하세요.");
            } else if (finalScore <= -1.0) {
                System.out.println("상대 투수의 구위가 밋밋합니다. 배트를 길게 잡고 적극적인 풀스윙으로 대형 장타를 노리세요.");
            } else {
                System.out.println("투수의 구위가 평범합니다. 평소 본인의 스윙 타이밍을 유지하며 중심 타격을 의식하세요.");
            }

            // 강점 구종 기반 솔루션
            System.out.print("  노림수 전략: ");
            if (pitchTypes.containsKey(hitterPowerType)) {
                System.out.println("투수의 보유 구종에 타자의 절대 강점인 " + hitterPowerType + "이(가) 있습니다. 이 공이 들어오면 주저하지 말고 타격하세요.");
            } else {
                System.out.println("상대 투수는 타자의 강점 구종을 던지지 않습니다. 특정 구종을 찍어놓기보다 스트라이크 존에 들어오는 실투를 놓치지 마세요.");
            }
            System.out.println("========================================================");
            System.out.println();
        }

        private void pitchingSolution() {
            System.out.println("================== 투수 피칭 디자인 솔루션 ==================");
            System.out.println("  " + pitcher.getName() + "(" + pitcher.getHandType().getDescription() + ") 피칭 디자인 분석");
            System.out.println();

            System.out.print("  상대 타자 분석: ");
            if (this.hitPercent >= 0.33) {
                System.out.println(hitter.getName() + "(" + hitter.getHandType().getDescription() + ")의 안타 확률이 극도로 높습니다. 무조건 유인구로 어렵게 승부하세요.");
            } else if (this.hitPercent >= 0.30) {
                System.out.println(hitter.getName() + "(" + hitter.getHandType().getDescription() + ")의 안타 확률이 높으므로 실투를 조심하고 신중하게 접근할 필요가 있습니다.");
            } else if (this.hitPercent >= 0.25) {
                System.out.println(hitter.getName() + "(" + hitter.getHandType().getDescription() + ")은(는) 무난한 타자입니다. 자신 있게 스트라이크 존 구석을 공략하세요.");
            } else {
                System.out.println(hitter.getName() + "(" + hitter.getHandType().getDescription() + ")은(는) 현재 타이밍이 맞지 않습니다. 정면 승부를 추천합니다.");
            }
            System.out.println("--------------------------------------------------------");

            countPitching();   // 초구/카운트 솔루션
            finalPitching();    // 결정구 솔루션
        }

        private void finalPitching() {
            System.out.println("  [위닝 샷 공략]");
            String hitterWeakZone = hitterStat.getWeakZonePitch().getZone().getZoneHeight();

            if (pitchTypes.containsKey(hitterWeakType)) {
                System.out.println("  - 약점 공략: " + hitterWeakZone + " 위치에 " + hitterWeakType + "을 던져 삼진을 유도하세요.");
            } else {
                System.out.println("  - 데이터 분석: " + pitcher.getName() + "이(가) 보유한 구종 중 타자의 약점 구종이 존재하지 않습니다.");
                System.out.println("  - 대체 전술: 타자의 약점 코스인 " + hitterWeakZone + " 위치에 " + hitterPowerType + " 외에 구종 가치가 높은 구종을 던지세요.");
            }
            System.out.println("========================================================");
            System.out.println();
        }

        private void countPitching() {
            System.out.println("  [초구 및 볼카운트 공략]");
            HandPitcherType pitcherHand = pitcher.getHandType();
            HandHitterType hitterHand = hitter.getHandType();

            double finalScore = getFinalScore();
            String hitterPowerZone = hitterStat.getPowerZonePitch().getZone().getZoneHeight();

            if (hitterHand == HandHitterType.SWITCH) {
                hitterHand = (pitcherHand == HandPitcherType.RIGHT) ? HandHitterType.LEFT : HandHitterType.RIGHT;
            }

            String recommendedZoneHeight = "하단";
            String recommendedDirection = (hitterHand == HandHitterType.RIGHT) ? "좌측" : "우측";

            String recommendedZone = recommendedDirection + " " + recommendedZoneHeight;


            zoneAdvice(finalScore, hitterPowerZone, recommendedZone);
            System.out.println();

            if (pitchTypes.containsKey(hitterPowerType)) {
                System.out.println("  - 상성 분석: " + pitcher.getName() + "이(가) 보유한 구종 중 타자의 강점 구종이 존재합니다.");
                System.out.println("  - 전술 가이드: " + hitterPowerType + " 외의 구종으로 상대하는 것이 좋습니다.");
            } else {
                System.out.println("  - 상성 분석: " + pitcher.getName() + "이(가) 보유한 구종 중 타자의 강점 구종이 존재하지 않습니다.");
                System.out.println("  - 전술 가이드: 주무기인 " + pitcherPowerType + "을(를) 과감하게 던지는 것이 좋습니다.");
            }
            System.out.println("--------------------------------------------------------");
        }

        private void zoneAdvice(double finalScore, String hitterPowerZone, String recommendedZone) {
            if (finalScore >= 3.0) {
                System.out.println("  - 구위 분석: 구위가 워낙 압도적이라 타자가 알고도 못 칩니다.");
                System.out.println("  - 코스 지시: 타자의 핫존을 무시하고 강력한 구위로 몸쪽 승부를 펼쳐 배트를 부러뜨리세요. 혹은 몸쪽 높은 곳에 변화구를 집어넣어 타자의 허를 찔러보세요.");

            } else if (finalScore >= 1.0) {
                if (hitterPowerZone.equals(recommendedZone)) {
                    System.out.println("  - 구위 분석: 투수의 구위가 뛰어나지만, 하필 바깥쪽 코스가 상대 타자의 핫존입니다!");
                    System.out.println("  - 코스 지시: 정면 승부는 도박입니다. 허를 찌르는 몸쪽 꽉 찬 역방향 승부나, 하이 패스트볼로 헛스윙을 유도하세요.");
                } else {
                    System.out.println("  - 구위 분석: 리그 상위권의 묵직한 공끝을 믿고 영리하게 들어갈 타이밍입니다.");
                    System.out.println("  - 코스 지시: " + recommendedZone + " 바깥쪽으로 가볍게 카운트를 잡되, 제구가 잡힌다면 몸쪽 승부로 타자를 움츠리게 만드세요.");
                }

            } else if (finalScore <= -1.0) {
                if (hitterPowerZone.equals(recommendedZone)) {
                    System.out.println("  - 구위 분석: 투수의 공끝이 가볍고, 타자는 바깥쪽 낮은 코스에 매우 강합니다.");
                    System.out.println("  - 코스 지시: 바깥쪽 낮은 코스와 몸쪽 승부는 최대한 피하세요. 한가운데 실투는 곧바로 장타로 이어집니다.\n  바깥쪽 높은 곳으로 유인구를 던져 타자의 방망이를 이끌어 내거나, 스트라이크 존 보더라인을 아슬아슬하게 걸치도록 투구하여 타자의 정타를 피하세요.");
                } else {
                    System.out.println("  - 구위 분석: 현재 투수의 힘이 많이 떨어져 있어 한가운데 실투는 곧바로 실점입니다.");
                    System.out.println("  - 코스 지시: 정타를 피하는 게 최우선이므로, 몸쪽은 피하고 " + recommendedZone + " 바깥쪽 낮은 보더라인 구석구석에 공을 던지거나 존 밖으로 도망가는 유인구로 수싸움을 하세요.");
                }

            } else {
                if (hitterPowerZone.equals(recommendedZone)) {
                    System.out.println("  - 구위 분석: 정석인 바깥쪽 코스가 타자의 강점 존(" + hitterPowerZone + ")과 겹쳐서 고민이 깊어지는 상황입니다.");
                    System.out.println("  - 코스 지시: 밋밋하게 가다간 방망이 중심에 걸립니다. 타자의 시야와 가장 가까운 몸쪽 라인에 실투 없이 기습적으로 찔러 넣어 땅볼을 유도하세요.");
                } else {
                    System.out.println("  - 구위 분석: 투수의 구위가 리그 평균입니다. 주자 상황에 따라 과감하거나 조심스럽게 피칭할 필요가 있습니다.");
                    System.out.println("  - 코스 지시: 가장 실점 확률이 낮고 안전한 " + recommendedZone + " 바깥쪽 코스를 스트라이크 존 넓게 활용하여 정석대로 카운트를 선점하세요.");
                }
            }
        }

        private void compareZonePitch() {
            System.out.println();
            boolean hitterWinMatched = (pitcherPowerType == hitterPowerType);
            boolean pitcherWinMatched = (pitcherPowerType == hitterWeakType);

            if (hasPowerMatch) {
                if (hitterWinMatched) {
                    System.out.println("  [타자 강점 매칭] 투수의 주 구종이 타자의 강점 구종에 걸리므로 안타 기댓값이 5% 증가합니다.");
                    hitPercent *= 1.05;
                } else {
                    System.out.println("  [타자 강점 매칭] 투수의 보유 구종이 타자의 강점 구종에 걸리므로 안타 기댓값이 2% 증가합니다.");
                    hitPercent *= 1.02;
                }
            }
            if (hasWeakMatch) {
                if (pitcherWinMatched) {
                    System.out.println("  [타자 약점 매칭] 투수의 주 구종이 타자의 약점 구종에 걸리므로 안타 기댓값이 10% 감소합니다.");
                    hitPercent *= 0.9;
                } else {
                    System.out.println("  [타자 약점 매칭] 투수의 보유 구종이 타자의 약점 구종에 걸리므로 안타 기댓값이 5% 감소합니다.");
                    hitPercent *= 0.95;
                }
            }
            if (!hasPowerMatch && !hasWeakMatch) {
                System.out.println("  구종에 따른 강점 및 약점이 존재하지 않아 안타 기댓값 증감이 이뤄지지 않습니다.");
            }
        }

        private void compareRpmSpeed() {
            double finalScore = getFinalScore();

            if (finalScore >= 3.0) {
                System.out.println("  [구위 분석] 투수의 구위가 리그 최상위권이므로 안타 기댓값이 8% 감소합니다.");
                hitPercent *= 0.92;
            } else if (finalScore >= 1.0) {
                System.out.println("  [구위 분석] 투수의 구위가 리그 상위권이므로 안타 기댓값이 4.5% 감소합니다.");
                hitPercent *= 0.955;
            } else if (finalScore <= -3) {
                System.out.println("  [구위 분석] 투수의 구위가 리그 최하위권이므로 안타 기댓값이 8% 증가합니다.");
                hitPercent *= 1.08;
            } else if (finalScore <= -1) {
                System.out.println("  [구위 분석] 투수의 구위가 리그 하위권이므로 안타 기댓값이 4.5% 증가합니다.");
                hitPercent *= 1.045;
            } else {
                System.out.println("  [구위 분석] 투수의 구위가 리그 평균이므로 안타 기댓값의 변화가 없습니다.");
            }
        }

        private double getFinalScore() {
            double ballSpeed = strongPitch.getBallSpeed();
            double avgSpeed = pitcherPowerType.getAvgSpeed();
            double rpm = strongPitch.getRpm();
            double avgRpm = pitcherPowerType.getAvgRpm();

            double speedDiff = ballSpeed - avgSpeed;
            double rpmDiff = rpm - avgRpm;
            double speedScore = speedDiff / 2;
            double rpmScore = rpmDiff / 70;
            return (speedScore * 0.6) + (rpmScore * 0.4);
        }

        public void compareHandType() {
            HandPitcherType pitcherHand = pitcher.getHandType();
            HandHitterType hitterHand = hitter.getHandType();

            if (pitcherHand == HandPitcherType.RIGHT && hitterHand == HandHitterType.RIGHT) {
                System.out.println("  [플래툰 분석] 투수 " + pitcher.getName() + " (우투), 타자 " + hitter.getName() + " (우타)이므로 안타 기댓값이 2% 감소합니다.");
                hitPercent *= 0.98;
            } else if (pitcherHand == HandPitcherType.LEFT && hitterHand == HandHitterType.LEFT) {
                System.out.println("  [플래툰 분석] 투수 " + pitcher.getName() + " (좌투), 타자 " + hitter.getName() + " (좌타)이므로 안타 기댓값이 4% 감소합니다.");
                hitPercent *= 0.96;
            } else {
                System.out.println("  [플래툰 분석] 역상성 매치업이므로 안타 기댓값이 2% 증가합니다.");
                hitPercent *= 1.02;
            }
        }
    }