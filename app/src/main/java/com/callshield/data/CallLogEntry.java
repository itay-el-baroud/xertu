package com.callshield.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "call_logs")
public class CallLogEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String phoneNumber;
    public long attemptTime;
    public String type;

    public CallLogEntry(String phoneNumber, String type) {
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.attemptTime = System.currentTimeMillis();
    }
}
