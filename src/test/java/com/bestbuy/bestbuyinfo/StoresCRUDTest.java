package com.bestbuy.bestbuyinfo;

import com.bestbuy.testbase.StoresTestBase;
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
public class StoresCRUDTest extends StoresTestBase {
    static int storeId;
    static String name = "Primark"+ TestUtils.getRandomValue();
    static String type = "Store"+ TestUtils.getRandomValue();
    static String address = "123 High Road"+ TestUtils.getRandomValue();
    static String address2 = "Wembley"+ TestUtils.getRandomValue();
    static String city = "London";
    static String state = "UK";
    static String zip = "12345";
    static double lat = 56.58 ;
    static double lng = 10.26;;
    static String hours = "Mon: 9-9; Tue: 9-9; Wed: 9-9; Thurs: 9-9; Fri: 9-9; Sat: 9-9; Sun: 11-4";

    @Steps
    StoresSteps steps;

    @Title("This test will create a new store")
    @Test
    public void test001() {
        ValidatableResponse response = steps.createStore(name,type,address,address2,city,state,zip,lat,lng,hours);
        response.log().all().statusCode(201);
        storeId = response.extract().path("id");
        System.out.println("Store ID number is: " + storeId);

    }
    @Title("Verify if the store was added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> storeMap = steps.getStoreById(storeId);
        Assert.assertThat(storeMap, hasValue(name));

    }
    @Title("Update store by store ID")
    @Test
    public void test003() {
        name = "GOLDBox" + TestUtils.getRandomValue();
        type = "Shop" + TestUtils.getRandomValue();
        steps.updateStoreById(storeId, name, type, address, address2, city, state, zip, lat, lng, hours).statusCode(200);

        HashMap<String, Object> storeMap = steps.getStoreById(storeId);

        Assert.assertThat(storeMap, hasValue(name));
    }
    @Title("Delete store by store ID")
    @Test
    public void test004() {
        steps.deleteStoreById(storeId).statusCode(200);

    }
    }
