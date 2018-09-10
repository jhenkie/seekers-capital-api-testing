

import common.EndPoint;
import common.Method;
import common.SupportingData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

public class ShowtimesTest {
    SoftAssert softAssert = new SoftAssert();
    Method mtd = new Method();

    @BeforeClass()
    public void configure() {
        RestAssured.baseURI = SupportingData.Showtimes.baseURI;
    }

    @Test
    public void validateShowDataFailed() {
        mtd.validateData(EndPoint.GET_SHOWTIMES_WRONG,SupportingData.StatusCode.notFound);
    }

    @Test
    public void validateShowDataSuccess() {
        //given().get(EndPoint.GET_USER).then().statusCode(200).log().all();
        mtd.validateData(EndPoint.GET_SHOWTIMES,SupportingData.StatusCode.success);
    }

    @Test
    public void validateDataExist() {
        mtd.validateDataExist(EndPoint.GET_SHOWTIMES);
    }

    @Test
    public void validateDataInsert() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type","application/json");
        JSONObject requestParams = new JSONObject();
        JSONArray movies = new JSONArray();

        for (String movie : SupportingData.Showtimes.moviesText) {
            movies.add(movie);
        }
        JSONObject data = new JSONObject();

        data.put(SupportingData.Showtimes.dateAttribute, SupportingData.Showtimes.dateText);
        data.put(SupportingData.Showtimes.moviesAttribute, movies);
        requestParams.put(SupportingData.data, data);
        request.body(requestParams.toJSONString());
        Response response = request.post(EndPoint.GET_SHOWTIMES);

        softAssert.assertEquals(response.getStatusCode(),SupportingData.StatusCode.success);
        softAssert.assertAll();

        mtd.checkInsertedData(EndPoint.GET_SHOWTIMES,SupportingData.Showtimes.Attribute.date,SupportingData.Showtimes.dateText);
    }

    //Unfortunately this Endpoint is not working
    @Test
    public void validateDataWithParam() {
        Response response = mtd.responseReturn((String) mtd.getLatestID(EndPoint.GET_SHOWTIMES),EndPoint.SHOWTIMES_WITH_ID);

        softAssert.assertEquals(response.getStatusCode(),SupportingData.StatusCode.success);
        softAssert.assertEquals(response.path(SupportingData.Showtimes.Attribute.id),mtd.getLatestID(EndPoint.GET_SHOWTIMES));
        softAssert.assertEquals(response.path(SupportingData.Showtimes.Attribute.date),SupportingData.Showtimes.dateText);
        softAssert.assertEquals(response.path(SupportingData.Showtimes.Attribute.movies),SupportingData.Showtimes.moviesText);
        softAssert.assertAll();
    }

    //Unfortunately this Endpoint is not working
    @Test
    public void validateDataWithParamFailed() {
        softAssert.assertEquals(mtd.responseReturn(SupportingData.Showtimes.idTextRandom,EndPoint.SHOWTIMES_WITH_ID).getStatusCode(),SupportingData.StatusCode.badGateway);
        softAssert.assertAll();
    }

    //Delete always latest id
    @Test
    public void validateDeleteDataSuccess() {
        mtd.validateDataDelete(EndPoint.GET_SHOWTIMES,EndPoint.SHOWTIMES_WITH_ID);
    }

    @Test
    public void validateDeleteDataFailed() {
        mtd.validateDataDeleteFailed(SupportingData.Showtimes.idTextRandom,EndPoint.SHOWTIMES_WITH_ID);
    }

}
