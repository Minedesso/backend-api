package com.minedesso.backendapi.ban.domain.dtos.out;

import com.minedesso.backendapi.ban.persistence.ReasonEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Reason {

    private long reasonId;
    private String reason;

    public Reason(ReasonEntity reasonEntity) {
        this.reasonId = reasonEntity.getReasonId();
        this.reason = reasonEntity.getReason();
    }

}
