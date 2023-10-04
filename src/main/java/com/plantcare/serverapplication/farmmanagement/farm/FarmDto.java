package com.plantcare.serverapplication.farmmanagement.farm;

import com.plantcare.serverapplication.shared.UserDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FarmDto {

    private int id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    private String location;

    private UserDto owner;

    private int mainArduinoBoardId;
}
