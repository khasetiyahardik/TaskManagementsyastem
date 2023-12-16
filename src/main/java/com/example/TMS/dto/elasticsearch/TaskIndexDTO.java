package com.example.TMS.dto.elasticsearch;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TaskIndexDTO {
    private Long took;
    private Boolean timed_out;

    private ShardsDTO _shards;

    private  HitsDTO hits;


}
