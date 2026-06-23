package baseball.domain.player;

public enum     Team {
    LOTTE("롯데 자이언츠"),
    HANHWA("한화 이글스"),
    DOOSAN("두산 베어스"),
    KIWOOM("키움 히어로즈"),
    LG("엘지 트윈스"),
    SAMSUNG("삼성 라이온즈"),
    KT("케이티 위즈"),
    SSG("SSG 랜더스"),
    NC("엔씨 다이노스"),
    KIA("기아 타이거즈");

    private String teamName;


    Team(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public static Team findTeamByKorean(String inputTeamName) {
        for (Team team : Team.values()) {
            if (team.teamName.equals(inputTeamName)) {
                return team;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return teamName;
    }
}
