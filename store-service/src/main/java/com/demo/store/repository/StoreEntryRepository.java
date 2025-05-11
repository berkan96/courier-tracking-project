package com.demo.store.repository;

import com.demo.store.document.StoreEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StoreEntryRepository extends MongoRepository<StoreEntry, Long> {
    Optional<StoreEntry> findFirstByStoreIdOrderByCreatedDateDesc(Long storeId);

}
