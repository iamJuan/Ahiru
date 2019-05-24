package entities;

import static utilities.GameConstants.*;

public class DuckFactory {

    public static Duck getDuck(String duckType, int xPos, int lane){
        Duck duck = null;
        if (EASY_DUCK.equalsIgnoreCase(duckType)){
            duck = new EasyDuck(xPos, lane);
        }else if(HELM_DUCK.equalsIgnoreCase(duckType)){
            duck = new HelmDuck(xPos, lane);
        }else if(KNIGHT_DUCK.equalsIgnoreCase(duckType)){
            duck = new KnightDuck(xPos, lane);
        }else if(CLOWN_DUCK.equalsIgnoreCase(duckType)){
            duck = new ClownDuck(xPos, lane);
        }else{
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return duck;
    }
}
