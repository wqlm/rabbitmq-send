package com.wqlm.rabbitmq.send.MessageSend;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author wqlm
 * @date 2019/8/25 9:19
 */
@Component
public class OrderMessageSend {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 通过 AmqpTemplate 发送消息
     */
    public void amqpSend(Object obj) {
        // 三个参数分别是 交换机名称 路由键 json化的消息实体
        String message = JSONObject.toJSONString(obj);
        amqpTemplate.convertAndSend("exchangeName", "routingKeyValue", message);
    }

    /**
     * 通过 RabbitTemplate 发送消息
     */
    public void rabbitSend(Object obj) {
        // 三个参数分别是 交换机名称 路由键 json化的消息实体
        String message = JSONObject.toJSONString(obj);
        rabbitTemplate.convertAndSend("exchangeName", "routingKeyValue", message);
    }
}
