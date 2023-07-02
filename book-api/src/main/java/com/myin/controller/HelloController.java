package com.myin.controller;

//import com.myin.RabbitMQConfig;
import com.myin.exceptions.GraceException;
import com.myin.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags= "HelloController测试")
@RequestMapping("hello")
public class HelloController {
    @Autowired
    public RabbitTemplate rabbitTemplate;

//    @GetMapping("producer")
//    public Object producer() throws Exception {
//        rabbitTemplate.convertAndSend(
//                RabbitMQConfig.EXCHANGE_MSG,
//                "sys.msg.send",
//                "我发了一个消息");
//
//        /**
//         * 路由规则
//         * route-key
//         * display.*.*
//         * display.a.b
//         * display.public.msg
//         * display.a.b.c不能匹配，一个*代表一个占位符
//         *
//         * display.#
//         * display.a.b.v.c.d.l都行
//         * 代表多个占位符，开发中*多
//         *
//         */
//        return GraceJSONResult.ok();
//    }
//
//    @GetMapping("producer2")
//    public Object producer2() throws Exception {
//        rabbitTemplate.convertAndSend(
//                RabbitMQConfig.EXCHANGE_MSG,
//                "sys.msg.delete",
//                "我删除一个消息");
//
//        return GraceJSONResult.ok();
//    }
}
