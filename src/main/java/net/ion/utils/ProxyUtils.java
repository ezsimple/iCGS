package net.ion.utils;

import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ProxyUtils {
	private final static Logger logger = LoggerFactory.getLogger(ProxyUtils.class);
    public static String getHtml(String url)throws Exception {
        Document doc = Jsoup.connect(url).get();
        for(Element e : doc.select("a"))
            e.attr("href", e.absUrl("href"));
        for(Element e : doc.select("img"))
            e.attr("src", e.absUrl("src"));
        return doc.html();
    }
    public static String getText(String url, String field)throws Exception {
        Document doc = Jsoup.connect(url).get();
        return doc.select(field).text();
    }
    public static String getText(String url, Map<String, Object> parameterMap, String field)throws Exception {
		StringBuilder buf = new StringBuilder();
		if (parameterMap != null && !parameterMap.isEmpty()) {
			for (String key : parameterMap.keySet()) {
				String val =  URLEncoder.encode(parameterMap.get(key).toString(),"UTF-8");
				buf.append(key+"="+val);
				buf.append("&");
			}
		}
		String query = StringUtils.removeEnd(buf.toString(), "&");
		url += "?"+query;
		logger.info(url);
        String json = Jsoup.connect(url).ignoreContentType(true).execute().body();
        logger.info(json);
        return getResAsJson(json).get(field).getAsString();
    }
    private static JsonObject getResAsJson(String response) {
        return new JsonParser().parse(response).getAsJsonObject();
    }
}
