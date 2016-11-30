
import com.imos.common.rest.HttpMethod;
import com.imos.common.rest.RestClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pintu
 */
public class RestTest {

    private RestClient restClient;
    private List<String> allDataPath;
    private List<String> currentDataPath;

    @Before
    public void setUp() {
        restClient = new RestClient();
        restClient.setBaseUrl("http://10.0.0.10:8085/");
        restClient.configure();

        allDataPath = new ArrayList<>();
        allDataPath.add("temphumid");
        allDataPath.add("data");
        allDataPath.add("day");
        allDataPath.add("%time");

        currentDataPath = new ArrayList<>();
        currentDataPath.add("temphumid");
        currentDataPath.add("data");
        currentDataPath.add("current");
    }

    @Test
    public void test() {
        
        allDataPath.set(3, String.valueOf(new Date().getTime()));
        restClient.setPaths(currentDataPath);
        restClient.setPaths(allDataPath);
        restClient.setUrlPath();
        restClient.setHttpMethod(HttpMethod.GET);
        Assert.assertEquals("", "", restClient.execute());
    }

}
