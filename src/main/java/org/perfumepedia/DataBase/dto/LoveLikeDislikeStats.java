package org.perfumepedia.DataBase.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoveLikeDislikeStats {
    private Integer loveQuantity;
    private Integer likeQuantity;
    private Integer disLikeQuantity;
}
