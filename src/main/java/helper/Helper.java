package helper;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Helper {
    public static int truncate(final String content, final int lastIndex) {
        String result = content.substring(0, lastIndex);
        if (content.charAt(lastIndex) != '.') {
            result = result.substring(0, result.lastIndexOf("."));
        }
        return result.length();
    }

    public static String generateText(String obj) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            String restUrl = URLEncoder.encode(obj, "UTF-8");
            URL url = new URL("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles=" + restUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            ObjectMapper mapper = new ObjectMapper();
            String response = "";
            //Get Response
            InputStream is = connection.getInputStream();
            Map<String, Object> map = mapper.readValue(is, Map.class);
            for (Map.Entry<String, Object> entry : map.entrySet())
                if (entry.getKey().equalsIgnoreCase("query")) {
                    LinkedHashMap<String, Object> page = (LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) entry.getValue()).get("pages");
                    LinkedHashMap<String, Object> page_details = (LinkedHashMap<String, Object>) page.get(page.keySet().iterator().next());
                    try {
                        response = page_details.get("extract").toString();
                    } catch (NullPointerException e) {
                        response = "No information has been found for this word";
                    }
                }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


}
