package com.wqlm.rabbitmq.send.controller;

import com.alibaba.fastjson.JSONObject;
import com.wqlm.rabbitmq.send.MessageSend.OrderMessageSendAsync;
import com.wqlm.rabbitmq.send.message.OrderMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wqlm
 * @date 2019/9/3 18:47
 */
@RestController
@RequestMapping("/")
public class SendMessageController {

    @Autowired
    private OrderMessageSendAsync orderMessageSendAsync;

    /**
     * 测试发送消息并异步接收响应
     */
    @GetMapping("/test")
    public void test(){
        OrderMessage orderMessage = new OrderMessage("123", "订单123");
        // 序列化成json ，OrderMessage 也可以 implements Serializable 这样就不需要序列化成json
        String message = JSONObject.toJSONString(orderMessage);
        orderMessageSendAsync.sendMsg("exchangeName", "routingKeyValue", message);
    }
}
