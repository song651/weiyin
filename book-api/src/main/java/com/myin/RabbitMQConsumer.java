//package com.myin;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//public class RabbitMQConsumer {
//
//    @RabbitListener(queues = {RabbitMQConfig.QUEUE_SYS_MSG})
//    public void watchQueue(String payload,Message message){
//        //payload是消息的载体
//        //message是具体的消息
//        //路由key是多个，可以根据message进行判读
//        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
//        log.info(routingKey);
//        log.info(payload);
//
//
//
//    }
//}
