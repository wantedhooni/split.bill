package com.revy.api_server.endpoint.req;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Revy on 2023.12.09
 */


@Getter
@Setter
@NoArgsConstructor
public class PageReq {
    @Min(1)
    private int page = 1;

    @Min(1)
    private int size = 10;
}
