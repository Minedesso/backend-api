package com.minedesso.backendapi.ban.domain.dtos.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MojangPlayer {

    private String id;
    private String name;

    public UUID toUUID() {
        if (id == null) return null;

        String hex = id.replace("-", "");
        if (hex.length() == 32) {
            String formatted = hex.
                    replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{12})",
                            "$1-$2-$3-$4-$5");
            return UUID.fromString(formatted);
        }
        return UUID.fromString(id);
    }

}
