package com.wqlm.rabbitmq.send;

import com.wqlm.rabbitmq.send.MessageSend.OrderMessageSend;
import com.wqlm.rabbitmq.send.message.OrderMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendApplicationTests {

    @Autowired
    private OrderMessageSend orderMessageSend;

    @Test
    public void contextLoads() {
        OrderMessage orderMessage = new OrderMessage("123","订单123");
        orderMessageSend.amqpSend(orderMessage);
//        orderMessageSend.rabbitSend(orderMessage);
    }

}
