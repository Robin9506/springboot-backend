package com.robin.springbootbackend.helper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface LogRepository extends JpaRepository<Log, UUID> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Log (ip, accountId, logType, routeType, repo, timeOfAction, actionMessage) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)")
    public void PostLog(String ip, UUID AccountId, String LogType, String RouteType, String Repo, LocalDateTime time, String message);

}
