package ru.fetisov.TaskTrecker.store.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AskDTO {
    boolean answer;
    public static AskDTO makeDefault(Boolean answer){
        return builder()
                .answer(answer)
                .build();
    }
}
