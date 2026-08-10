package com.callshield.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "blocked_numbers")
public class BlockedNumber {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String phoneNumber;
    public String category;
    public long blockedAt;
    public Long expiresAt;
    public int attemptsCount;
    public long lastAttemptTime;
    public String notes;

    public BlockedNumber(String phoneNumber, String category) {
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.blockedAt = System.currentTimeMillis();
        this.attemptsCount = 0;
        this.lastAttemptTime = 0;
    }
}
