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
public class AccountControllerTest {

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
    public void testGetUserInfo() throws Exception {
        //前置：数据库User表中存在行，id为5
        String res = mockMvc.perform(
                get("/api/user/5/getUserInfo").contentType(MediaType.APPLICATION_JSON)
                // 这里是模拟向后端发起一个post请求 url写上，JSON传对象，内容就是orderJson
                // 如果是get请求就写get，后边content啥的应该不用写
                // 请求的参数如果在url里，那就直接在url写上对应的数据
                /*
                如果是get请求**传参**(不是url传参)，则写get("url").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("key","value")
                有几个param就在后面点几个param，不过这种方式似乎只有搜索酒店用到了
                 */
        ).andReturn().getResponse().getContentAsString();
        // 这里是接收请求的返回值，也就是ResponseVO，以字符串形式
        //System.out.println(res);
        assertTrue(new JSONObject(res).getBoolean("success"));
        // ResponseVO里面有Boolean success, String message, Object content三个成员，我判断success是不是true就知道测试是否成功
    }

    @org.junit.Test
    public void testRegister() throws Exception {
        String userJson ="{\n" +
                "        \"email\":\"testAddUser@qq.com\",\n" +
                "        \"password\": \"123456\",\n" +
                "        \"userName\":\"testAddUser\",\n" +
                "        \"phoneNumber\": \"123\",\n" +
                "        \"UserType\": \"User\"\n" +

                "   }";
        String res = mockMvc.perform(
                post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(userJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testLogin() throws Exception {
        String userJson ="{\n" +
                "        \"email\":\"333@qq.com\",\n" +
                "        \"password\": \"123456\"\n" +
                "   }";
        String res = mockMvc.perform(
                post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(userJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testUpdateUserInfo() throws Exception {
        String userJson ="{\n" +
                "        \"password\": \"1234567\",\n" +
                "        \"userName\":\"testUpdateUserInfo\",\n" +
                "        \"phoneNumber\": \"123\",\n" +
                "        \"avatarurl\": \"https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png\"\n" +
                "   }";
        String res = mockMvc.perform(
                post("/api/user/5/userInfo/update").contentType(MediaType.APPLICATION_JSON).content(userJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testCreditSet() throws Exception {
        String userJson ="{\n" +
                "        \"email\":\"333@qq.com\",\n" +
                "        \"credit\": 10001\n" +
                "   }";
        String res = mockMvc.perform(
                post("/api/user/creditSet").contentType(MediaType.APPLICATION_JSON).content(userJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testLvSet() throws Exception {
        String userJson ="{\n" +
                "        \"email\":\"333@qq.com\",\n" +
                "        \"lv\": 1\n" +
                "   }";
        String res = mockMvc.perform(
                post("/api/user/lvSet").contentType(MediaType.APPLICATION_JSON).content(userJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testGetAccountByEmail() throws Exception {
        String userJson ="{\n" +
                "        \"email\":\"333@qq.com\"\n" +
                "   }";
        String res = mockMvc.perform(
                post("/api/user/getAccountByEmail").contentType(MediaType.APPLICATION_JSON).content(userJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testGetUserImage() throws Exception {
        String res = mockMvc.perform(
                get("/api/user/5/getUserImg").contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

//    @org.junit.Test
//    public void testUpdateUserImage() throws Exception {
//        String userJson ="{\n" +
//                "        \"email\":\"333@qq.com\"\n" +
//                "   }";
//        String res = mockMvc.perform(
//                post("/api/5/updateUserImg").contentType(MediaType.APPLICATION_JSON).content(userJson)
//        ).andReturn().getResponse().getContentAsString();
//        assertTrue(new JSONObject(res).getBoolean("success"));
//    }
}
