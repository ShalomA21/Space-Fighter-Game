package Game;

public class Action {
    public int thrust;
    public int turn;
    public boolean shoot;

    public Action(int thrust, int turn, boolean shoot) {
        this.thrust = thrust;
        this.turn = turn;
        this.shoot = shoot;
    }

    public Action(){}

}
