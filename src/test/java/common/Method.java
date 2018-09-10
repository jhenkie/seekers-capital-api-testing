package common;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

public class Method {
    SoftAssert softAssert = new SoftAssert();
    ArrayList<HashMap<String, Object>> users;

    public RequestSpecification getRequestSpecification() {
        return RestAssured.given().contentType(ContentType.JSON);
    }

    public void validateData(String endpoint,int httpStatus) {
        Response response = given().get(endpoint);

        softAssert.assertEquals(response.getStatusCode(),httpStatus);
        softAssert.assertAll();
    }

    public void validateDataExist(String endpoint) {
        RequestSpecification requestSpec = new RequestSpecBuilder().build();
        Response response = given().spec(requestSpec).get(endpoint);
        List<HashMap<String,Object>> userList=response.jsonPath().getList(SupportingData.data);

        softAssert.assertNotNull(userList);
        softAssert.assertAll();
    }

    public Object getLatestID(String endpoint) {
        Response response = given().get(endpoint);
        this.users = response.path(SupportingData.data);
        HashMap<String, Object> lastData = this.users.get(users.size() - 1);
        return lastData.get(SupportingData.id);
    }

    public Response responseReturn(String id, String endpoint) {
        RequestSpecification requestSpec = getRequestSpecification();
        requestSpec.pathParam(SupportingData.id,id).log().all();
        Response response = given().spec(requestSpec).get(endpoint);
        return response;
    }

    public void checkInsertedData(String endpoint, String attribute, String value) {
        Response responseAfter = given().get(endpoint);

        Collection<String> c = responseAfter.path(attribute);
        assertThat(c,hasItem(value));
    }

    public void validateDataDelete(String endpoint, String parameter) {
        RequestSpecification requestSpec = getRequestSpecification().given();

        requestSpec.pathParam(SupportingData.id,getLatestID(endpoint)).log().all();
        Response response = requestSpec.delete(parameter);
        softAssert.assertEquals(response.getStatusCode(),SupportingData.StatusCode.noContent);
        softAssert.assertAll();
    }

    public void validateDataDeleteFailed(String text, String endpoint) {
        RequestSpecification requestSpec = getRequestSpecification().given();
        requestSpec.pathParam(SupportingData.id,text).log().all();
        Response response = requestSpec.delete(endpoint);
        softAssert.assertEquals(response.getStatusCode(),SupportingData.StatusCode.badGateway);
        softAssert.assertAll();
    }

}
