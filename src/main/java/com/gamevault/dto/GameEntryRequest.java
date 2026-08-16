package com.gamevault.dto;

import com.gamevault.model.GameStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//tiene userId y gameId, números — solo identificadores, porque eso es lo único que el cliente necesita mandar
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameEntryRequest {

    @NotNull(message = "El id del usuario es obligatorio")
    private Long userId;

    @NotNull(message = "El id del juego es obligatorio")
    private Long gameId;

    @NotNull(message = "El estado es obligatorio")
    private GameStatus status;

    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 10, message = "La calificación máxima es 10")
    private Integer rating;

    @Min(value = 0, message = "Las horas jugadas no pueden ser negativas")
    private Integer hoursPlayed;

    private String personalReview;
}