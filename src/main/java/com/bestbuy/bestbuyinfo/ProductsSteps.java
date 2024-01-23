package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.model.ProductsPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class ProductsSteps {

    @Step("Creating product with name : {0}, type : {1}, price : {2}, shipping : {3}, upc : {4}, description : {5}, manufacturer : {6}, model : {7} , url : {8}, image : {9} ")
    public ValidatableResponse createProduct(String name, String type, double price, int shipping, String upc, String description, String manufacturer, String model, String url, String image) {
        ProductsPojo productPojo = new ProductsPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .post(EndPoints.CREATE_PRODUCTS)
                .then().log().all();
    }

    @Step("Getting the product information with product ID : {0}")
    public HashMap<String, Object> getProductById(int productId) {

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("productID", productId)
                .when()
                .get(Path.PRODUCTS + EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().statusCode(200)
                .extract().path("");
    }

    @Step("Updating product information with name : {0}, type {1}, description {2}, manufacturer {3}")
    public ValidatableResponse updateProduct(int productId, String name, String type, int price, String upc, int shipping,String description, String manufacturer,String model, String image, String url) {
        ProductsPojo productPojo = new ProductsPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setShipping(shipping);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .pathParam("productID", productId)
                .when()
                .body(productPojo)
                .patch(Path.PRODUCTS + EndPoints.UPDATE_PRODUCT_BY_ID)
                .then().log().all();
    }

    @Step("Deleting product information with productId : {0}")
    public ValidatableResponse deleteProduct(int productId) {
        return SerenityRest.given()
                .pathParam("productID", productId)
                .when()
                .delete(Path.PRODUCTS + EndPoints.DELETE_PRODUCT_BY_ID)
                .then().log().all();

    }
    @Step("Getting product information with productId : {0}")
    public ValidatableResponse getProductInfoById(int productId){
        return SerenityRest.given()
                .pathParam("productID", productId)
                .when()
                .get(Path.PRODUCTS + EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().log().all();
    }

}