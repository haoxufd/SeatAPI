import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;


public class SeatAPI {
    public static ObjectMapper mapper = new ObjectMapper();
    public static OkHttpClient client = new OkHttpClient();
    public static void main(String[] args) {
        // 查看JB201教室在18-21点的每个座位是否可用
        // IP和端口应该填之后运行我们后台程序的服务器的IP地址和端口
        // 目前写安卓端的话, 可以先定义个 IP_PORT 常量, 方便之后修改
        Request request = new Request.Builder()
                .url("http://127.0.0.1:5000/seat/status/JB201/18-21")
                .build();
        try {
            // 访问"http://127.0.0.1:5000/seat/status/JB201/18-21"
            Response response = client.newCall(request).execute();
            // 后台返回的结果是json格式字符串, 需要进行解析
            String jsonString = response.body().string();
            Map<Object, Object> map = mapper.readValue(jsonString, Map.class);
            // 解析完成后, map中的每一个entry都是一个键值对, 键是String类型, 值是Boolean类型
            // 比如, Key="1", Value=false, 表示该教室中的1号座位在这个时间段不可用
            // 安卓端根据解析出的所有座位的可用情况来展示一个界面,比如可以用绿色按钮表示可用座位, 红色按钮表示不可用座位
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
