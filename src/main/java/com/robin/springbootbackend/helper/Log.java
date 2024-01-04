package com.robin.springbootbackend.helper;


import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robin.springbootbackend.enums.LogType;
import com.robin.springbootbackend.enums.Repo;
import com.robin.springbootbackend.enums.RouteType;
import com.robin.springbootbackend.product.Product;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id")
    private UUID logId;
    @Column(name = "ip")
    private String ip;
    @Column(name = "account_id")
    private UUID accountId;
    @Column(name = "log_type")
    private LogType logType;
    @Column(name = "route_type")
    private RouteType routeType;
    @Column(name = "repo")
    private Repo repo;
    @Column(name = "time_of_action")
    private LocalDateTime timeOfAction;
    @Column(name = "action_message")
    private String actionMessage;

    public Log(String ip, UUID accountId, LogType logType, RouteType routeType, Repo repo, LocalDateTime time, String message){
        this.ip = ip;
        this.accountId = accountId;
        this.logType = logType;
        this.routeType = routeType;
        this.repo = repo;
        this.timeOfAction = time;
        this.actionMessage = message;
    }

    public UUID getLogID(){
        return logId;
    }
    
    public String getIp(){
        return ip;
    }

    public UUID getAccountId(){
        return accountId;
    }

    public String getLogType(){
        return logType.toString();
    }

    public String getRouteType(){
        return routeType.toString();
    }

    public String getRepo(){
        return repo.toString();
    }

    public String getActionMessage(){
        return actionMessage;
    }

}
