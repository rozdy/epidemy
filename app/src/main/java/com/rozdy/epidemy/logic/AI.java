package com.rozdy.epidemy.logic;

import com.rozdy.epidemy.bus.AIMoveEvent;
import com.rozdy.epidemy.bus.EventBus;
import com.rozdy.epidemy.logic.board.Board;
import com.rozdy.epidemy.logic.board.BoardPositionHelper;
import com.rozdy.epidemy.logic.cell.Cell;
import com.rozdy.epidemy.logic.game.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergejj on 11/10/2015 in the name of the Emperor!
 */
public class AI {
    public static void makeAIMove(int player) {
        for (int index = 0; index < Game.getInstance().getMaxNumberOfMoves(); index++) {
            Integer[][] movesMap = Game.getInstance().getBoard().buildMovesMap(player);
            for (int i = 0; i < movesMap.length; i++) {
                boolean moveMade = false;
                for (int j = 0; j < movesMap[i].length; j++) {
                    if (movesMap[i][j] == Board.WALL_AVAILABLE || movesMap[i][j] == Board.MARK_AVAILABLE) {
                        EventBus.getInstance().post(new AIMoveEvent(new BoardPositionHelper().calculatePositionByXY(i, j)));
                        moveMade = true;
                        break;
                    }
                }
                if (moveMade) {
                    break;
                }
            }
        }
    }

    public static void makeWeightedAIMove(int player) throws Exception {
        for (int index = 0; index < Game.getInstance().getMaxNumberOfMoves(); index++) {


            List<SubMove> subMovesToRandomizeFrom = getSubMovesToRandomizeFrom(player);

            if (subMovesToRandomizeFrom.size() == 0) {
                throw new Exception("AI has no moves");
            }

            int number = randomize(subMovesToRandomizeFrom.size());

            EventBus.getInstance().post(
                    new AIMoveEvent(new BoardPositionHelper().
                            calculatePositionByXY(
                                    subMovesToRandomizeFrom.get(number).getX(),
                                    subMovesToRandomizeFrom.get(number).getY())));
        }
    }

    public static int randomize(double size) {
        double step = 1 / size;

        return (int) (Math.random() / step);
    }

    public static List<SubMove> getSubMovesToRandomizeFrom(int player) {
        Integer[][] movesMap = Game.getInstance().getBoard().buildMovesMap(player);

        int minDistance = Game.getInstance().getBoard().getHeight() + Game.getInstance().getBoard().getWidth(); //just a big number to guarantee being out of the board
        List<SubMove> subMovesToRandomizeFrom = new ArrayList<>();

        for (int i = 0; i < movesMap.length; i++) {
            for (int j = 0; j < movesMap[i].length; j++) {
                if (movesMap[i][j] == Board.WALL_AVAILABLE || movesMap[i][j] == Board.MARK_AVAILABLE) {
                    int thisMoveDistance = getDistanceToClosestEnemyCross(player, i, j);
                    if (minDistance > thisMoveDistance) {
                        subMovesToRandomizeFrom = new ArrayList<>();
                        subMovesToRandomizeFrom.add(new SubMove(i, j, thisMoveDistance));
                        minDistance = thisMoveDistance;
                    } else {
                        if (minDistance == thisMoveDistance) {
                            subMovesToRandomizeFrom.add(new SubMove(i, j, thisMoveDistance));
                        }
                    }
                }
            }
        }
        return subMovesToRandomizeFrom;
    }

    /**
     * Goes through all other players crosses and finds the lowest distance between the available submove coordinates and the other player cross
     *
     * @param player to make a submove one day
     * @param x      of submove
     * @param y      of submove
     * @return lowest distance to the enemy cross
     */
    public static int getDistanceToClosestEnemyCross(int player, int x, int y) {
        int distance = Game.getInstance().getBoard().getHeight() + Game.getInstance().getBoard().getWidth(); //just a big number to guarantee being out of the board
        for (int otherPlayer = 0; otherPlayer < Game.getInstance().getPlayersNumber(); otherPlayer++) {
            if (otherPlayer == player) {
                continue;
            }
            List<Cell> otherPlayersMarks = Game.getInstance().getBoard().getMarksList(otherPlayer);

            for (Cell otherPlayersMark : otherPlayersMarks) {
//                double currentDistance = Math.sqrt(Math.pow(otherPlayersMark.getX() - x, 2) + Math.pow(otherPlayersMark.getY() - y, 2));
                int currentDistance = Math.max(Math.abs(otherPlayersMark.getX() - x), Math.abs(otherPlayersMark.getY() - y)); //todo take into account enemy walls
                if (currentDistance < distance) {
                    distance = currentDistance;
                }
            }
        }
        return distance;
    }

    public static class SubMove {
        int x;
        int y;
        double distance;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public SubMove(int x, int y, double distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SubMove)) return false;

            SubMove subMove = (SubMove) o;

            if (getX() != subMove.getX()) return false;
            if (getY() != subMove.getY()) return false;
            return Double.compare(subMove.getDistance(), getDistance()) == 0;

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = getX();
            result = 31 * result + getY();
            temp = Double.doubleToLongBits(getDistance());
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        @Override
        public String toString() {
            return "SubMove{" +
                    "x=" + x +
                    ", y=" + y +
                    ", distance=" + distance +
                    '}';
        }
    }
}
