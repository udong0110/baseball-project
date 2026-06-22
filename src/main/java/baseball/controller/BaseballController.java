package baseball.controller;

import baseball.domain.player.*;
import baseball.domain.stat.PlayerStat;
import baseball.repository.PlayerRepository;
import baseball.factory.PlayerCreateFactory;


import java.util.Map;
import java.util.Scanner;

public class BaseballController {
    private Scanner scanner = new Scanner(System.in);
    private PlayerRepository repository = new PlayerRepository();


    private void registerPlayer() {
        System.out.println("선수 유형을 선택하세요");
        System.out.println("1. 투수 || 2. 타자");
        int playerType = scanner.nextInt();
        scanner.nextLine();
        // 투수일 때, 외부 클래스에서 선수,선수스탯 생성 후 보관
        if (playerType == 1) {
            PlayerCreateFactory pitcherCreateFactory = PlayerCreateFactory.createPitcher();
            repository.save(pitcherCreateFactory.getPlayer(), pitcherCreateFactory.getPlayerStat());
    
        // 타자일 때, 동일
        } else if (playerType == 2) {
            PlayerCreateFactory hitterCreateFactory = PlayerCreateFactory.createHitter();
            repository.save(hitterCreateFactory.getPlayer(), hitterCreateFactory.getPlayerStat());

        } else {
            System.out.println("투수,타자 외의 잘못된 입력입니다.");
        }
    }


    private void selectPlayer(String playerName, Team teamName, HandType handType) {
        Player player = new Player(playerName, teamName, handType);
        PlayerStat playerStat = repository.findByPlayer(player);
        if (playerStat == null) {
            System.out.println("해당 선수가 존재하지 않습니다.");
        } else {
            System.out.println(player+", "+playerStat);
        }

    }

    public void printAllPlayer() {
        // PlayerRepository에 findAll메서드 이용해서 실질적인 Map에 접근
        Map<Player, PlayerStat> repositoryAll = repository.findAll();

        for (Map.Entry<Player, PlayerStat> playerPlusStatEntry : repositoryAll.entrySet()) {
            System.out.print(playerPlusStatEntry.getKey()+ ", " + playerPlusStatEntry.getValue());
        }
        System.out.println();
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
                        HandType matchedHandType = null;
                        Team matchedTeam = null;

                        System.out.print("이름을 입력하세요: ");
                        String playerName = scanner.nextLine();
                        System.out.print("팀이름을 입력하세요(lotte,doosan등): ");
                        String teamName = scanner.nextLine().toUpperCase();
                        matchedTeam = Team.valueOf(teamName);
                        System.out.print("(왼손, 오른손, 양손)중 선수의 주손잡이를 선택하세요: ");
                        String handType = scanner.nextLine();
                        matchedHandType = HandType.findType(handType);

                        selectPlayer(playerName, matchedTeam, matchedHandType);
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
                scanner.nextLine();    // nextInt에 숫자 외 입력시 버퍼제거용
            }

        }
    }

}

