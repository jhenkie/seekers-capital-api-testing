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

public class BookingTest {
    SoftAssert softAssert = new SoftAssert();
    Method mtd = new Method();

    @BeforeClass()
    public void configure() {
        RestAssured.baseURI = SupportingData.Booking.baseURI;
    }

    @Test
    public void validateShowDataFailed() {
        mtd.validateData(EndPoint.GET_BOOKINGS_WRONG,SupportingData.StatusCode.notFound);
    }

    @Test
    public void validateShowDataSuccess() {
        //given().get(EndPoint.GET_USER).then().statusCode(200).log().all();
        mtd.validateData(EndPoint.GET_BOOKINGS,SupportingData.StatusCode.success);
    }

    @Test
    public void validateDataExist() {
        mtd.validateDataExist(EndPoint.GET_BOOKINGS);
    }

    @Test
    public void validateDataInsert() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type","application/json");
        JSONObject requestParams = new JSONObject();
        JSONArray movies = new JSONArray();

        for (String movie : SupportingData.Booking.moviesText) {
            movies.add(movie);
        }
        JSONObject data = new JSONObject();

        data.put(SupportingData.Booking.useridAttribute, SupportingData.Booking.useridText);
        data.put(SupportingData.Booking.showtimeidAttribute, SupportingData.Booking.showtimeidText);
        data.put(SupportingData.Booking.moviesAttribute, movies);
        requestParams.put(SupportingData.data, data);
        request.body(requestParams.toJSONString());
        Response response = request.post(EndPoint.GET_BOOKINGS);

        softAssert.assertEquals(response.getStatusCode(),SupportingData.StatusCode.success);
        softAssert.assertAll();

        mtd.checkInsertedData(EndPoint.GET_BOOKINGS,SupportingData.Booking.Attribute.userid,SupportingData.Booking.useridText);
    }

}
