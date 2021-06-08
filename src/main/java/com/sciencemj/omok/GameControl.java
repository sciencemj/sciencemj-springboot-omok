package com.sciencemj.omok;

import com.sciencemj.omok.domain.Map;

public class GameControl {
    Pair<Integer,Integer>[] direction = new Pair[]{new Pair<>(-1, -1),new Pair<>(0, -1),
            new Pair<>(1, -1),new Pair<>(-1, 0),new Pair<>(1, 0),new Pair<>(-1, 1),
            new Pair<>(0, 1),new Pair<>(1, 1),};
    int c;
    public int isEnd(Map map, int x, int y){
        int[][] map_ = map.getMap();
        for (int i = -1;i < 2;i++){
            for (int j = -1;j < 2;j++){
                if(!(i == 0 && j == 0)){
                    if(map_[y+j][x+i] == map_[y][x]){
                        c = search(getDir(i,j), map_, x, y, map_[y][x],0) + search(Math.abs(getDir(i,j)-7), map_,x,y, map_[y][x], 0);
                        if (c >= 4){
                            return map_[y][x];
                        }
                    }
                }
            }
        }
        return -1;
    }
    public int search(int dir,int[][] map,int x,int y, int player, int count){
        int y_ = y + direction[dir].right;
        int x_ = x + direction[dir].left;
        if (map[y_][x_] == player){
            count++;
            return search(dir,map,x_,y_,player,count);
        }
        return count;
    }
    public int getDir(int x, int y){
        String s = x + " " + y;
        switch (s){
            case "1 0":
                return 4;
            case "0 1":
                return 6;
            case "1 1":
                return 7;
            case "-1 0":
                return 3;
            case "0 -1" :
                return 1;
            case "-1 -1":
                return 0;
            case "-1 1":
                return 5;
            case  "1 -1":
                return 2;
            default:
                return -1;
        }
    }
}
