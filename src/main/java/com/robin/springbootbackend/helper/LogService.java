package com.robin.springbootbackend.helper;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;
import java.time.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LogService {

    private final LogRepository logRepository;
    private LocalDateTime localDateTime = LocalDateTime.now();

    @Autowired
    public LogService(LogRepository logRepository)
    {
        this.logRepository = logRepository;
    }

    public void LogAction(Log log){
        UUID accountId = null;
        if(log.getAccountId() != null){
            accountId = log.getAccountId();
        }
        
        this.logRepository.PostLog(log.getIp(), accountId, log.getLogType(), log.getRouteType(), log.getRepo(), localDateTime, log.getActionMessage());
    }
}
