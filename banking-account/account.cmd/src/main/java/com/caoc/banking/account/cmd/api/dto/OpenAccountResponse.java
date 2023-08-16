package com.caoc.banking.account.cmd.api.dto;

import com.caoc.banking.account.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAccountResponse extends BaseResponse {
    private String id;
    public OpenAccountResponse(String message, String id) {
        super(message);
        this.id = id;
    }
}