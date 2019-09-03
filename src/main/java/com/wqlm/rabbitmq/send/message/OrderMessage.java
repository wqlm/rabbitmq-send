package com.wqlm.rabbitmq.send.message;

import java.io.Serializable;

/**
 * 订单的消息实体
 *
 * @author wqlm
 * @date 2019/8/25 9:53
 */
public class OrderMessage implements Serializable {

    /**
     * 业务id，在业务系统中的唯一。比如 订单id、支付id、商品id ，消息消费端可以通过该 id 避免消息重复消费
     */
    private String id;

    // 其他业务字段
    private String name;

    public OrderMessage() {
    }

    public OrderMessage(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
