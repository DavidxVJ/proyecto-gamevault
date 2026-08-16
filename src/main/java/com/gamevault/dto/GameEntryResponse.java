package com.gamevault.dto;

import com.gamevault.model.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameEntryResponse {
    private Long id;
    private String username;
    private String gameTitle;
    private GameStatus status;
    private Integer rating;
    private Integer hoursPlayed;
    private String personalReview;
}