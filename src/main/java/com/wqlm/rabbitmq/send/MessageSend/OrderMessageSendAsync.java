package com.wqlm.rabbitmq.send.MessageSend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * 发送消息并异步监听 ack
 *
 * @author wqlm
 * @date 2019/9/2 16:02
 */
@Component
public class OrderMessageSendAsync implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private Logger logger = LoggerFactory.getLogger(OrderMessageSendAsync.class);

    private RabbitTemplate rabbitTemplate;

    /**
     * 通过构造函数注入 RabbitTemplate 依赖
     *
     * @param rabbitTemplate
     */
    @Autowired
    public OrderMessageSendAsync(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // 设置 channel 为 confirm 模式。(参考https://juejin.im/post/5d6cccd85188251ff57f9e2a)，每个RabbitTemplate只支持一个ConfirmCallback
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 发送消息
     *
     * @param exchange   交换机
     * @param routingKey 路由建
     * @param message    消息实体
     */
    public void sendMsg(String exchange, String routingKey, Object message) {
        // 构造包含消息的唯一id的对象，id 必须在该 channel 中始终唯一
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        logger.info("ID为: {}", correlationData.getId());
        // todo 先将 message 入库，在将 message 的数据库ID 、message的消息id message的初始状态(发送中)等信息入库

        // 完成 数据落库，消息状态打标后，就可以安心发送 message
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);

        try {
            logger.info("发送消息的线程处于休眠状态， confirm 和 returnedMessage 方法依然处于异步监听状态");
            Thread.sleep(1000*15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 异步监听 消息是否到达 exchange
     *
     * @param correlationData 包含消息的唯一标识的对象
     * @param ack             true 标识 ack，false 标识 nack
     * @param cause           nack 的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        if (ack) {
            logger.info("消息投递成功,ID为: {}", correlationData.getId());
            // todo 操作数据库，将 correlationId 这条消息状态改为投递成功
            return;
        }

        logger.error("消息投递失败,ID为: {},错误信息: {}", correlationData.getId(), cause);
        // todo 操作数据库，将 correlationId 这条消息状态改为投递失败

    }

    /**
     * 异步监听 消息是否到达 queue
     * 触发回调的条件有两个：1.消息已经到达了 exchange 2.消息无法到达 queue (比如 exchange 找不到跟 routingKey 对应的 queue)
     *
     * @param message    返回的消息
     * @param replyCode  回复 code
     * @param replyText  回复 text
     * @param exchange   交换机
     * @param routingKey 路由键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        // correlationId 就是发消息时设置的 id
        String correlationId = message.getMessageProperties().getHeaders().get("spring_returned_message_correlation").toString();

        logger.error("没有找到对应队列，消息投递失败,ID为: {}, replyCode {} , replyText {}, exchange {} routingKey {}",
                correlationId, replyCode, replyText, exchange, routingKey);
        // todo 操作数据库，将 correlationId 这条消息状态改为投递失败
    }
}
