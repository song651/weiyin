//package com.myin;
//import com.rabbitmq.client.AMQP;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.ExchangeBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.Exchange;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//    /**
//     * 模型
//     * 定义交换机
//     * 定义队列
//     * 定义之后创建交换机
//     * 创建队列
//     * 队列和交换机的绑定
//     */
//
//    /**
//     * 代码
//     */
//    public static final String EXCHANGE_MSG = "exchange_sag";
//    public static final String QUEUE_SYS_MSG = "queue_sys_msg";
//
//
//    @Bean(EXCHANGE_MSG)
//    public Exchange exchange(){
//        return ExchangeBuilder              //构建交换机
//                .topicExchange(EXCHANGE_MSG)//常用的，根据规则进行监听//构建交换机//参考官网
//                .durable(true)              //设置持久化重启后依然存在
//                .build();
//    }
//
//
//    @Bean(QUEUE_SYS_MSG)
//    public Queue queue(){
//        return new Queue(QUEUE_SYS_MSG,true);
//    }
//
//
//    @Bean
//    public Binding binding(@Qualifier(EXCHANGE_MSG) Exchange exchange, @Qualifier(QUEUE_SYS_MSG) Queue queue){
//        return BindingBuilder
//                .bind(queue)
//                .to(exchange)
//                .with("sys.msg.*")   //定义路由规则
//                .noargs();
//    }
//}
