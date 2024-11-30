package com.spotbiz.spotbiz_backend_springboot.component;

import com.spotbiz.spotbiz_backend_springboot.entity.SearchHistory;
import com.spotbiz.spotbiz_backend_springboot.repo.SearchHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchHistoryManager {

    @Autowired
    private SearchHistoryRepo searchHistoryRepo;

    // Runs daily at midnight
//    @Scheduled(cron = "0 0 0 * * ?")
//    public void deactivateExpiredSubscriptions() {
//        cleanupOldSearchHistory();
//    }

    // Runs on application startup
//    @PostConstruct
//    public void runOnStartup() {
//        cleanupOldSearchHistory();
//    }

    public void cleanupOldSearchHistory() {
        try {
            // Fetch distinct user IDs from the database
            List<Integer> userIds = searchHistoryRepo.findAll()
                    .stream()
                    .map(SearchHistory::getUserId)
                    .distinct()
                    .toList();

            // Cleanup older records for each user
            for (Integer userId : userIds) {
                searchHistoryRepo.deleteOlderRecordsForUser(userId);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
