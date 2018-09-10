
import common.EndPoint;
import common.Method;
import common.SupportingData;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import scala.collection.script.End;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

public class MoviesTest {

    SoftAssert softAssert = new SoftAssert();
    Method mtd = new Method();

    @BeforeClass()
    public void configure() {
        RestAssured.baseURI = SupportingData.Movie.baseURI;
    }

    @Test
    public void validateShowDataFailed() {
        mtd.validateData(EndPoint.GET_MOVIE_WRONG,SupportingData.StatusCode.notFound);
    }

    @Test
    public void validateShowDataSuccess() {
        mtd.validateData(EndPoint.GET_MOVIE,SupportingData.StatusCode.success);
    }

    @Test
    public void validateDataExist() {
        mtd.validateDataExist(EndPoint.GET_MOVIE);
    }

    @Test
    public void validateDataInsert() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type","application/json");
        JSONObject requestParams = new JSONObject();
        JSONObject data = new JSONObject();

        data.put(SupportingData.Movie.titleAttribute, SupportingData.Movie.titleText);
        data.put(SupportingData.Movie.directorAttribute, SupportingData.Movie.directorText);
        data.put(SupportingData.Movie.ratingAttribute, SupportingData.Movie.ratingText);
        requestParams.put(SupportingData.data, data);
        request.body(requestParams.toJSONString());
        Response response = request.post(EndPoint.GET_MOVIE);

        softAssert.assertEquals(response.getStatusCode(),SupportingData.StatusCode.success);
        softAssert.assertAll();

        mtd.checkInsertedData(EndPoint.GET_MOVIE,SupportingData.Movie.Attribute.director,SupportingData.Movie.directorText);
    }

    @Test
    public void validateDataWithParamSuccess() {
        Response response = mtd.responseReturn((String) mtd.getLatestID(EndPoint.GET_MOVIE),EndPoint.MOVIE_WITH_ID);

        softAssert.assertEquals(response.getStatusCode(),SupportingData.StatusCode.success);
        softAssert.assertEquals(response.path(SupportingData.Movie.Attribute.id),mtd.getLatestID(EndPoint.GET_MOVIE));
        softAssert.assertEquals(response.path(SupportingData.Movie.Attribute.title),SupportingData.Movie.titleText);
        softAssert.assertEquals(response.path(SupportingData.Movie.Attribute.director),SupportingData.Movie.directorText);
        softAssert.assertEquals(response.path(SupportingData.Movie.Attribute.rating),SupportingData.Movie.ratingText);
        softAssert.assertAll();
    }

    @Test
    public void validateDataWithParamFailed() {
        softAssert.assertEquals(mtd.responseReturn(SupportingData.Movie.idTextRandom,EndPoint.MOVIE_WITH_ID).getStatusCode(),SupportingData.StatusCode.badGateway);
        softAssert.assertAll();
    }

    //Delete always latest id
    @Test
    public void validateDeleteDataSuccess() {
        mtd.validateDataDelete(EndPoint.GET_MOVIE,EndPoint.MOVIE_WITH_ID);
    }

    @Test
    public void validateDeleteDataFailed() {
        mtd.validateDataDeleteFailed(SupportingData.Movie.idTextRandom,EndPoint.MOVIE_WITH_ID);
    }
}
