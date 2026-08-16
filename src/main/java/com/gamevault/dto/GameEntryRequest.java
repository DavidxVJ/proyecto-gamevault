package com.gamevault.dto;

import com.gamevault.model.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameEntryRequest {
    private Long userId;
    private Long gameId;
    private GameStatus status;
    private Integer rating;
    private Integer hoursPlayed;
    private String personalReview;
}