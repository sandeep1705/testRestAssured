
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.time.Clock;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HttpBinTest {

    static final Assertion assertion=new Assertion();
    static final String baseUri = "https://httpbin.org";
    static final int successful = 200;
    String hostValue;
    String verifiedStatusCode = "Verified status code.";

    @Test(enabled = true)
    public void testGetRequest() {
        RestAssured.baseURI = baseUri;
        Response response=RestAssured.get();
        assertion.assertEquals(response.getStatusCode(),successful,verifiedStatusCode);
    }

    @Test(enabled = true)
    public void testPostRequest() {
        Response response = RestAssured.post(baseUri+"/post");
        hostValue = response.jsonPath().get("headers.Host");
        assertion.assertEquals(response.getStatusCode(),successful,verifiedStatusCode);
        assertion.assertEquals(hostValue,"httpbin.org","Verified Host value from response");
    }

    @Test(enabled = true)
    public void testDelayRequest() {
        Response response = RestAssured.get(baseUri+"/delay/5");
        hostValue = response.jsonPath().get("headers.Host");
        assertion.assertEquals(response.getStatusCode(),successful,verifiedStatusCode);
        assertion.assertEquals(hostValue,"httpbin.org","Verified Host value from response");
    }

    @Test(enabled = true)
    public void testNegativeScenario() {
        Response response = RestAssured.get(baseUri+"/delay/negative");
        assertion.assertEquals(response.getStatusCode(),500,"Verified status code.");
    }

    @Test(enabled = true)
    public void testAuthorisedScenario() {
        String username = "myuser";
        String password = "mypassword";
        Response response = RestAssured.get(baseUri+"/basic-auth/{user}/{passwd}", username, password);;
        assertion.assertEquals(response.getStatusCode(),401,"Verified status code.");
    }
    }