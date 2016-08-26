package com.ming.cnbeta.net;

import com.ming.cnbeta.bean.Content;
import com.ming.cnbeta.utils.LogUtils;
import com.ming.cnbeta.utils.jsonUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * 自定义转换器
 * Created by ming on 16/2/17.
 */
public class ToStringConverterFactory extends Converter.Factory{

    public static ToStringConverterFactory create() {
        return new ToStringConverterFactory();
    }

//    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(final Type type, Annotation[] annotations) {
        if (String.class.equals(type)){
            return new Converter<ResponseBody, String>() {
                @Override public String convert(ResponseBody value) throws IOException {
                    return value.string();
                }
            };
        }
        return null;

    }

    @Override public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        if (Content.class.equals(type)) {
            return new Converter<Content, RequestBody>() {
                @Override public RequestBody convert(Content value) throws IOException {
                    LogUtils.i("toRequestBody",jsonUtils.toJson(value));
                    return RequestBody.create(CONTENT_TYPE, jsonUtils.toJson(value));
                }
            };
        }
        return null;
    }
}
