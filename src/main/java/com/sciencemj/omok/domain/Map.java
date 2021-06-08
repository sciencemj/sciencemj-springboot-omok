package com.sciencemj.omok.domain;

import lombok.*;

@Getter
@Setter
public class Map {
    private Long id;
    private int[][] map;
    private int turn = 0;
    private int win = -1;
    public Map(Long id, int[][] map){
        this.id = id;
        this.map = map;
    }
    public int changeTurn(){
        if (turn == 0){
            turn = 1;
        }else {
            turn = 0;
        }
        return turn;
    }

    public void init() {
        map = new int[21][21];
        turn = 0;
        win = -1;
    }
}
