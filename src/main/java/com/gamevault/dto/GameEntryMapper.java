package com.gamevault.dto;

import com.gamevault.model.GameEntry;

public class GameEntryMapper {

    //Mismo patrón static que vimos con GameMapper
    public static GameEntryResponse toResponse(GameEntry entry) {
        return new GameEntryResponse(
                entry.getId(),
                entry.getUser().getUsername(),
                entry.getGame().getTitle(),
                entry.getStatus(),
                entry.getRating(),
                entry.getHoursPlayed(),
                entry.getPersonalReview()
        );
    }
}