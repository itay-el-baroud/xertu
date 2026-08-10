package com.callshield.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface BlockedNumberDao {
    @Insert
    void insert(BlockedNumber number);

    @Update
    void update(BlockedNumber number);

    @Delete
    void delete(BlockedNumber number);

    @Query("SELECT * FROM blocked_numbers ORDER BY blockedAt DESC")
    List<BlockedNumber> getAll();

    @Query("SELECT * FROM blocked_numbers WHERE phoneNumber = :phone LIMIT 1")
    BlockedNumber findByNumber(String phone);

    @Query("SELECT * FROM blocked_numbers WHERE expiresAt IS NOT NULL AND expiresAt < :now")
    List<BlockedNumber> getExpired(long now);

    @Query("DELETE FROM blocked_numbers WHERE expiresAt IS NOT NULL AND expiresAt < :now")
    void deleteExpired(long now);

    @Query("SELECT * FROM blocked_numbers WHERE phoneNumber LIKE '%' || :query || '%' ORDER BY attemptsCount DESC")
    List<BlockedNumber> search(String query);
}
