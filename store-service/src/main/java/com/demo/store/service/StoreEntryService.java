package com.demo.store.service;

import com.demo.store.document.StoreEntry;
import com.demo.store.repository.StoreEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreEntryService {
    private final StoreEntryRepository storeEntryLogRepository;

    public Optional<StoreEntry> getLastEntryRecord(Long storeId) {
        return storeEntryLogRepository.findFirstByStoreIdOrderByPickupDateDesc(storeId);
    }

    public void saveStoreEntryLog(StoreEntry storeEntry) {

        log.info("Saved store log entry log");
        storeEntryLogRepository.save(storeEntry);
    }
}
