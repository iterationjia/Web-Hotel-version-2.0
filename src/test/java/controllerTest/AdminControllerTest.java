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
                "        \"email\":\"testAddManager@qq.com\",\n" +
                "        \"password\": \"123456\",\n" +
                "        \"userName\":\"testAddManager\",\n" +
                "        \"phoneNumber\": \"123\"\n" +
                "   }";
        String res = mockMvc.perform(
                post("/api/admin/addManager").contentType(MediaType.APPLICATION_JSON).content(managerJson)

        ).andReturn().getResponse().getContentAsString();

        assertTrue(new JSONObject(res).getBoolean("success"));

    }

    @org.junit.Test
    public void testEditUserInfo() throws Exception {
        //前置条件：user表中存在id为5的行
        String editJson ="{\n" +
                "        \"password\": \"1234567\",\n" +
                "        \"userName\":\"testEditUserInfo\",\n" +
                "        \"phoneNumber\": \"1243\"\n" +
                "   }";
        String res = mockMvc.perform(
                post("/api/admin/5/editUserInfo").contentType(MediaType.APPLICATION_JSON).content(editJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testGetAllManagers() throws Exception {
        String res = mockMvc.perform(
                get("/api/admin/getAllManagers").contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testGetAllUsers() throws Exception {
        String res = mockMvc.perform(
                get("/api/admin/getAllUsers").contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testGetVip() throws Exception {
        String res = mockMvc.perform(
                get("/api/admin/getVip").contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testDeleteUser() throws Exception {
        //前置条件：user表中存在id为5的行
        String res = mockMvc.perform(
                post("/api/admin/5/deleteUser").contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }
}
