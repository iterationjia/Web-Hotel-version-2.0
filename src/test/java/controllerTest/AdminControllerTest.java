package controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.hotel.HotelApplication;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelApplication.class)
@Transactional // 这个标签是把测试中对数据库的操作当作一个事务，使测试不对数据库造成污染，去掉就会实际修改数据库
public class AdminControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @org.junit.Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @org.junit.After
    public void tearDown() {

    }

    @org.junit.Test
    public void testAddManager() throws Exception {
        String managerJson ="{\n" +
                "        \"email\":\"test@qq.com\",\n" +
                "        \"password\": \"123456\",\n" +
                "        \"userName\":\"test\",\n" +
                "        \"phoneNumber\": \"123\",\n" +
                "        \"credit\": 10000\n" +
                "        }";

        String res = mockMvc.perform(
                post("/api/admin/addManager").contentType(MediaType.APPLICATION_JSON).content(managerJson)

        ).andReturn().getResponse().getContentAsString();

        assertTrue(new JSONObject(res).getBoolean("success"));

    }
}
