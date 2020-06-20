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
        String res = mockMvc.perform(
                get("/api/user/0/getUserInfo").contentType(MediaType.APPLICATION_JSON)
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
}
