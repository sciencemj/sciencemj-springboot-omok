package com.sciencemj.omok.web;

import com.sciencemj.omok.GameControl;
import com.sciencemj.omok.domain.Map;
import com.sciencemj.omok.domain.Message;
import com.sciencemj.omok.domain.MessageType;
import com.sciencemj.omok.domain.OMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageHandler {

    private final OMapService oMapService;
    private final GameControl g = new GameControl();

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public Map message(Message message){
        log.info("message received, message:{}", message.getMsg());
        Map map = oMapService.findById(message.getId());
        if (map.getWin() != -1){
            map.init();
        }
        if (message.getType().equals(MessageType.STONE)) {
            String[] cordinateS = message.getMsg().split(" ");
            int x = Integer.parseInt(cordinateS[0]);
            int y = Integer.parseInt(cordinateS[1]);
            if (addStone(x, y, message.getSender(), message.getId())) {
                int result = g.isEnd(map, x, y);
                map.setWin(result);
                map.changeTurn();
            }
        }else if (message.getType().equals(MessageType.JOIN)){
            log.info("joined!");
        }
        return map;
    }
    public boolean addStone(int x, int y, int sender, Long id){
        int[][] map = oMapService.findById(id).getMap();
        if (map[y][x] == 0) {
            if (sender == 0) {
                oMapService.findById(id).getMap()[y][x] = 1;
                return true;
            } else if (sender == 1) {
                oMapService.findById(id).getMap()[y][x] = 2;
                return true;
            }
        }else {
            return false;
        }
        return false;
    }

}
