package com.github.zxbu.webdavteambition.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import net.sf.webdav.exceptions.WebdavException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectMapper xmlMapper = new XmlMapper();

    public static String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new WebdavException(e);
        }
    }

    public static <T> T readValue(String json, TypeReference<T> valueTypeRef)  {
        try {
            return objectMapper.readValue(json, valueTypeRef);
        } catch (IOException e) {
            throw new WebdavException(e);
        }
    }


    public static <T> T readValue(String json, Class<T> valueType)  {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            throw new WebdavException(e);
        }
    }

    static {
        // 忽略未知的字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 读取不认识的枚举时，当null值处理
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,true);
        // 通过spi注册支持的modules（对象映射器），如JavaTimeModule等
        objectMapper.findAndRegisterModules();
//        // 日期以时间戳序列化
//        objectMapper.enable(
//                // date以时间戳序列化，单独列出是因为springboot默认给禁用了
//                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
//        ).disable(
//                // 禁用写 nanos
//                SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS
//                // 使用系统默认时区
//        ).setTimeZone(TimeZone.getDefault());
    }

    public static String xmlToJson(String xmlStr)  {
        try {
            Object jsonObject = xmlMapper.readValue(xmlStr, Object.class);
            return toJson(jsonObject);
        } catch (Exception e) {
            throw new WebdavException(e);
        }

    }

    public static Object getJsonNodeValue(String json, String path) {
        try {
            JsonNode jsonNode = getJsonNode(json);
            List<PathToken> pathTokens = getPathTokens(path);
            for (PathToken pathToken : pathTokens) {
                if (pathToken.getType() == PathType.KEY) {
                    jsonNode = jsonNode.get(pathToken.getValue());
                }
                if (pathToken.getType() == PathType.NUMBER) {
                    jsonNode = jsonNode.get(Integer.parseInt(pathToken.getValue()));
                }
            }
            return objectMapper.treeToValue(jsonNode, Object.class);
        } catch (Exception e) {
            throw new WebdavException(e);
        }
    }



    private static List<PathToken> getPathTokens(String path) {
        List<PathToken> pathTokenList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean escape = false;
        for (char c : path.toCharArray()) {
            if (c == '[') {
                if (sb.length() > 0) {
                    pathTokenList.add(new PathToken(PathType.KEY, sb.toString()));
                }
                sb.setLength(0);
            } else if (c == ']') {
                if (sb.length() > 0) {
                    pathTokenList.add(new PathToken(PathType.NUMBER, sb.toString()));
                }
                sb.setLength(0);
            } else if (c == '.') {
                if (escape) {
                    sb.append(c);
                } else {
                    if (sb.length() > 0) {
                        pathTokenList.add(new PathToken(PathType.KEY, sb.toString()));
                    }
                    sb.setLength(0);
                }

            } else if (c == '\\') {
                escape = true;
            } else {
                sb.append(c);
            }
        }
        if (sb.length() > 0) {
            pathTokenList.add(new PathToken(PathType.KEY, sb.toString()));
        }
        return pathTokenList;
    }




    private static JsonNode getJsonNode(String responseBody) {
        try {
            return objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            throw new WebdavException(e);
        }
    }

    private enum PathType {
        KEY, NUMBER;
    }

    private static class PathToken {
        private PathType type; // 0 是key， 1 是索引数字
        private String value;

        public PathToken(PathType type, String value) {
            this.type = type;
            this.value = value;
        }

        public PathType getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }
}
