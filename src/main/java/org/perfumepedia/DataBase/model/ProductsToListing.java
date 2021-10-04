package org.perfumepedia.DataBase.model;

public interface ProductsToListing {
    Integer getIdProduct();
    String getProductName();
    Integer getIdBrand();
    String getNameBrand();
    String getNameType();
    String getShortType();
    String getImg();
    String getAlt();
    Double getScore();
    Integer getCountComments();
}
