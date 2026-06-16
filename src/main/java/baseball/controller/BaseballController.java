package baseball.controller;

import baseball.domain.player.*;
import baseball.domain.stat.PlayerStat;
import baseball.repository.PlayerRepository;


import java.util.Scanner;

public class BaseballController {
    private Scanner scanner = new Scanner(System.in);
    private PlayerRepository repository = new PlayerRepository();


    private void registerPlayer() {
        System.out.println("선수 유형을 선택하세요");
        System.out.print("1. 투수 || 2. 타자");
        int playerType = scanner.nextInt();
        scanner.nextLine();
        if (playerType == 1) {
            registerPitcher();
        } else if (playerType == 2) {
            registerHitter();
        } else {
            System.out.println("투수,타자 외의 잘못된 입력입니다.");
        }
    }

    private void registerHitter() {
        Team matchedTeam = null;
        HandType matchedHandType = null;
        Zone matchedPowerZone = null;
        Zone matchedWeakZone = null;

        // 타자 정보 입력
        System.out.print("타자 이름을 입력하세요: ");
        String hitterName = scanner.nextLine();
        System.out.print("팀 영문명을 입력하세요 (lotte, doosan 등): ");
        String teamInputName = scanner.nextLine().toUpperCase();

        matchedTeam = Team.valueOf(teamInputName);

        System.out.print("(왼손, 오른손, 양손)중 타자의 주손을 입력하세요");
        String handInputType = scanner.nextLine().toUpperCase();
        matchedHandType = HandType.valueOf(handInputType);

        System.out.println("타자의 강점 존을 입력하세요(1~9) :");
        int powerZoneInputNumber = scanner.nextInt();
        matchedPowerZone = Zone.findFromNNumber(powerZoneInputNumber);

        System.out.print("타자의 약점 존을 입력하세요(1~9) :");
        int weakZoneInputNumber = scanner.nextInt();
        matchedWeakZone = Zone.findFromNNumber(weakZoneInputNumber);
        scanner.nextLine();

        // 타자 스탯 입력
        System.out.print("타자의 타율을 입력하세요(0~1.0사이) : ");
        double avg = scanner.nextDouble();

        System.out.print("타자의 OPS를 입력하세요(0~2.0 사이) : ");
        double ops = scanner.nextDouble();
        scanner.nextLine();


    }




    private void registerPitcher() {

    }


    private void selectPlayer(String playerName, String teamName) {

    }

    public void printAllPlayer() {

    }

    public void pitchingAnalyze() {

    }

    public void run() {
        while (true) {
            try {
                System.out.println("1. 선수 등록 || 2. 선수 조회 || 3. 전체 선수 조회  || 4. 시뮬레이션 || 5. 종료 ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> {
                        registerPlayer();
                    }
                    case 2 -> {
                        System.out.print("이름을 입력하세요: ");
                        String playerName = scanner.nextLine();
                        System.out.println("팀이름을 입력하세요: ");
                        String teamName = scanner.nextLine();
                        selectPlayer(playerName, teamName);
                    }
                    case 3 -> {
                        printAllPlayer();
                    }
                    case 4 -> {
                        pitchingAnalyze();
                    }
                    case 5 -> {
                        System.out.println("프로그램 정상종료.");
                        return;
                    }
                    default -> System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요.");
                }
            } catch (IllegalArgumentException | java.util.InputMismatchException e) {
                System.out.println("\n[입력 오류] " + e.getMessage());
                System.out.println("메인 메뉴로 돌아갑니다. 다시 시도해 주세요.");
                scanner.nextLine();
            }

        }
    }

}

