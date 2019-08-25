package com.wqlm.rabbitmq.send.message;

/**
 * 订单的消息实体
 * @author wqlm
 * @date 2019/8/25 9:53
 */
public class OrderMessage {

    private String id;

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
