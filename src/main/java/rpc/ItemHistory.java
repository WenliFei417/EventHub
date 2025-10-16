package rpc;

import db.DBConnection;
import db.DBConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/history")   // 在 IDEA 中用注解完成 URL 映射
public class ItemHistory extends HttpServlet {

    // 统一写出 JSON 的小工具
    private void writeJson(HttpServletResponse resp, int status, String json) throws IOException {
        resp.setStatus(status);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
        }
    }

    // GET /history?userId=123
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = req.getParameter("userId");
        if (userId == null || userId.isEmpty()) {
            writeJson(resp, HttpServletResponse.SC_BAD_REQUEST,
                    "{\"error\":\"missing userId\"}");
            return;
        }

        // TODO: 根据 userId 查询历史记录（数据库/缓存）
        // 示例返回
        String result = """
                {
                  "userId": "%s",
                  "items": [
                    {"id":"h1","title":"Example A"},
                    {"id":"h2","title":"Example B"}
                  ]
                }
                """.formatted(userId);

        writeJson(resp, HttpServletResponse.SC_OK, result);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            JSONObject input = RpcHelper.readJsonObject(request);
            String userId = input.getString("user_id");

            JSONArray array = input.getJSONArray("favorite");
            List<String> itemIds = new ArrayList<>();
            for (int i = 0; i < array.length(); ++i) {
                itemIds.add(array.get(i).toString());
            }

            DBConnection conn = DBConnectionFactory.getConnection();
            conn.setFavoriteItems(userId, itemIds);
            conn.close();

            RpcHelper.writeJsonObject(response,
                    new JSONObject().put("result", "SUCCESS"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            JSONObject input = RpcHelper.readJsonObject(request);
            String userId = input.getString("user_id");

            JSONArray array = input.getJSONArray("favorite");
            List<String> itemIds = new ArrayList<>();
            for (int i = 0; i < array.length(); ++i) {
                itemIds.add(array.get(i).toString());
            }

            DBConnection conn = DBConnectionFactory.getConnection();
            conn.unsetFavoriteItems(userId, itemIds);
            conn.close();

            RpcHelper.writeJsonObject(response,
                    new JSONObject().put("result", "SUCCESS"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
