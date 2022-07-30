package cn.bossma.springdemo.web.hateoas.client.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.util.Arrays;

public class CustomConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {
    private final static long DEFAULT_SECONDS = 30;

    @Override
    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        return Arrays.stream(httpResponse.getHeaders(HTTP.CONN_KEEP_ALIVE))
                .filter(h -> h.getName().equalsIgnoreCase("timeout")
                        && StringUtils.isNumeric(h.getValue()))
                .findFirst()
                .map(h -> NumberUtils.toLong(h.getValue(), DEFAULT_SECONDS))
                .orElse(DEFAULT_SECONDS);
    }
}
