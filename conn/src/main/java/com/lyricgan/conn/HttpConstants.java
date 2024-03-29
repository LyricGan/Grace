package com.lyricgan.conn;

interface HttpConstants {
    String HTTP_TAG = "http_tag";
    String UTF_8 = "UTF-8";
    /** 请求超时时间 */
    int SOCKET_TIMEOUT = 30 * 1000;
    /** 连接超时时间 */
    int CONNECTION_TIMEOUT = 30 * 1000;
    // 网络响应返回码
    int SERVER_SUCCESS = 200;
    int URL_NULL = -1;
    int EXCEPTION = -2;
    int PARSE_ERROR = -3;
}
