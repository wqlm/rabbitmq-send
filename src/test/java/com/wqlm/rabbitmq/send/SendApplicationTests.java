package com.wqlm.rabbitmq.send;

import com.wqlm.rabbitmq.send.message.OrderMessage;
import com.wqlm.rabbitmq.send.service.SendMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendApplicationTests {

    @Autowired
    private SendMessageService service;

    @Test
    public void contextLoads() {
        OrderMessage orderMessage = new OrderMessage("123","订单123");
        service.amqpSend(orderMessage);
//        service.rabbitSend(orderMessage);
    }

}
