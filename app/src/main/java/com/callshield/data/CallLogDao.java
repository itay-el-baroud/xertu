package com.callshield.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CallLogDao {
    @Insert
    void insert(CallLogEntry entry);

    @Query("SELECT * FROM call_logs ORDER BY attemptTime DESC")
    List<CallLogEntry> getAll();

    @Query("SELECT * FROM call_logs WHERE phoneNumber = :phone ORDER BY attemptTime DESC")
    List<CallLogEntry> getByNumber(String phone);

    @Query("SELECT COUNT(*) FROM call_logs WHERE phoneNumber = :phone")
    int getCountForNumber(String phone);

    @Query("DELETE FROM call_logs")
    void clearAll();
}
