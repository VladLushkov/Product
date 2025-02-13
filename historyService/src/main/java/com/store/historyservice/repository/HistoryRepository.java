package com.store.historyservice.repository;

import com.store.historyservice.model.History;
import org.springframework.data.jpa.repository.*;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query(value = "select COUNT(*) from history ", nativeQuery = true)
    public Long getHistoryCount();
}
