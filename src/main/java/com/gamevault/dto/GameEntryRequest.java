package com.gamevault.dto;

import com.gamevault.model.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//tiene userId y gameId, números — solo identificadores, porque eso es lo único que el cliente necesita mandar
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