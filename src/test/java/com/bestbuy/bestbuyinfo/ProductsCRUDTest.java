package com.bestbuy.bestbuyinfo;

import com.bestbuy.testbase.ProductsTestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.collection.IsMapContaining.hasValue;

@RunWith(SerenityRunner.class)
public class ProductsCRUDTest extends ProductsTestBase {

    static String name = "Dell" + TestUtils.getRandomValue();
    static String type = "laptop"+ TestUtils.getRandomValue();
    static int price = 2999;
    static int shipping = 0;
    static String upc = "045678123456";
    static String description = "Latest Model";
    static String manufacturer = "Dell Inc.";
    static String model = "PQR123";
    static String url = "https://pisces.bbystatic.com/" + TestUtils.getRandomValue() + ".jpg;maxHeight=640;maxWidth=550";
    static String image = TestUtils.getRandomValue();
    static int productId;

    @Steps
    ProductsSteps steps;


    @Title("This will create a new product")
    @Test
    public void test001() {

        ValidatableResponse response = steps.createProduct(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        response.statusCode(201);
        productId = response.extract().path("id");
        System.out.println("Product Id number is = " + productId);
    }

    @Title("Verify if the product was added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> productMap = steps.getProductById(productId);
        Assert.assertThat(productMap, hasValue(name));


    }

    @Title("Update the product information and verify the updated information")
    @Test
    public void test003() {
        type = "Touchscreen" + TestUtils.getRandomValue();
        name = "Prime" +  TestUtils.getRandomValue();

        steps.updateProduct(productId, name, type, price, upc , shipping, description, manufacturer, model, image, url).statusCode(200);
        HashMap<String, Object> productMap = steps.getProductById(productId);
        Assert.assertThat(productMap, hasValue(name));

    }
        @Title("Delete the product and verify if the product  is deleted!")
        @Test
        public void test004() {

            steps.deleteProduct(productId).statusCode(200);
            steps.getProductInfoById(productId).statusCode(404);

        }
    }
