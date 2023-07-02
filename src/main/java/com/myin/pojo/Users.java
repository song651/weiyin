package com.myin.pojo;

import java.io.Serializable;
import javax.persistence.*;

public class Users implements Serializable {
    @Column(name = "USER")
    private String user;

    @Column(name = "CURRENT_CONNECTIONS")
    private Long currentConnections;

    @Column(name = "TOTAL_CONNECTIONS")
    private Long totalConnections;

    private static final long serialVersionUID = 1L;

    /**
     * @return USER
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return CURRENT_CONNECTIONS
     */
    public Long getCurrentConnections() {
        return currentConnections;
    }

    /**
     * @param currentConnections
     */
    public void setCurrentConnections(Long currentConnections) {
        this.currentConnections = currentConnections;
    }

    /**
     * @return TOTAL_CONNECTIONS
     */
    public Long getTotalConnections() {
        return totalConnections;
    }

    /**
     * @param totalConnections
     */
    public void setTotalConnections(Long totalConnections) {
        this.totalConnections = totalConnections;
    }
}