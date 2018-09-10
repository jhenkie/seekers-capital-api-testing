
import common.EndPoint;
import common.Method;
import common.SupportingData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UserTest {
    SoftAssert softAssert = new SoftAssert();
    Method mtd = new Method();

    @BeforeClass()
    public void configure() {
        RestAssured.baseURI = SupportingData.User.baseURI;
    }

    @Test
    public void validateShowDataFailed() {
        mtd.validateData(EndPoint.GET_USER_WRONG,SupportingData.StatusCode.notFound);
    }

    @Test
    public void validateShowDataSuccess() {
        mtd.validateData(EndPoint.GET_USER,SupportingData.StatusCode.success);
    }

    @Test
    public void validateDataExist() {
        mtd.validateDataExist(EndPoint.GET_USER);
    }

    @Test
    public void validateDataInsert() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type","application/json");
        JSONObject requestParams = new JSONObject();
        JSONObject data = new JSONObject();

        data.put(SupportingData.User.nameAttribute, SupportingData.User.nameText);
        data.put(SupportingData.User.lastNameAttribute, SupportingData.User.lastNameText);
        requestParams.put(SupportingData.data, data);
        request.body(requestParams.toJSONString());
        Response response = request.post(EndPoint.GET_USER);

        softAssert.assertEquals(response.getStatusCode(),SupportingData.StatusCode.success);
        softAssert.assertAll();

        mtd.checkInsertedData(EndPoint.GET_USER,SupportingData.User.Attribute.nameAttribute,SupportingData.User.nameText);
    }

    //Delete always latest id
    @Test
    public void validateDeleteDataSuccess() {
        mtd.validateDataDelete(EndPoint.GET_USER,EndPoint.USER_DELETE);
    }

    @Test
    public void validateDeleteDataFailed() {
        mtd.validateDataDeleteFailed(SupportingData.User.idTextRandom,EndPoint.USER_DELETE);
    }

}
