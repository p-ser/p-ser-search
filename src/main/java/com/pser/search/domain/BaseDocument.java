package com.pser.search.domain;

import java.time.LocalDateTime;

public interface BaseDocument {
    Long getId();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
