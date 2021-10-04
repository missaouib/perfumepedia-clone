package org.perfumepedia.DataBase.dto;

import lombok.Builder;
import lombok.Data;
import org.perfumepedia.DataBase.model.ProductImage;

import java.util.List;

@Builder
@Data
public class ListingProductDto {
    private Long idProduct;
    private String productName;
    private Integer idBrand;
    private String nameBrand;
    private String nameType;
    private String shortType;
    private ProductImage productImage;
    private Double score;
    private Integer countComments;
}
