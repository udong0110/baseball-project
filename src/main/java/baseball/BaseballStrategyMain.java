package baseball;

import baseball.controller.BaseballController;

public class BaseballStrategyMain {

    public static void main(String[] args) {
        BaseballController controller = new BaseballController();

        try {
            controller.run();
        } catch (Exception e) {
            System.out.println("예상못한 예외가 발생하였습니다."+e.getMessage());
        }
    }
}
