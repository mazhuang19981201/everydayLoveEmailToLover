package com.mazhuang.schedulemail.schedule;

import com.mazhuang.schedulemail.common.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyScheduled {

    @Autowired
    private SendMessage sendMessage;

    @Scheduled(cron = "3 * * * * *")
    public void sendMessage(){

        log.info("====调用定时方法");
        String message = sendMessage.getOneS();
        sendMessage.sendMessage("来自另一个自己",message);
    }

}
