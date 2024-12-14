package ru.soa.demo;

import jakarta.ejb.Stateless;

@Stateless
public class PingBean {

    public String ping() {
        return "pong";
    }
}
