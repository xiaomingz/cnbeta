package com.ming.cnbeta.net;

import com.ming.cnbeta.bean.ResponseObject;
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
public class ToDefinedConverterFactory extends Converter.Factory{

    public static ToDefinedConverterFactory create() {
        return new ToDefinedConverterFactory();
    }

//    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(final Type type, Annotation[] annotations) {
//        if (String.class.equals(type)){
//
//        }else {
//            return new GsonResponseBodyConverter<>(gson, type);
//        }
        return new Converter<ResponseBody, ResponseObject<?>>() {

            @Override public ResponseObject<?> convert(ResponseBody value) throws IOException {
                LogUtils.i("adas",value.string());
                ResponseObject<?> result = jsonUtils.toBean(value.string(), type);
                return result;
            }
        };
    }

    @Override public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
//        if (Content.class.equals(type)) {
//            return new Converter<Content, RequestBody>() {
//                @Override public RequestBody convert(Content value) throws IOException {
////                    LogUtils.i("ToStringConverterFactory",jsonUtils.toJson(value));
//                    String cryptoContent = "";
//                    try {
//                        cryptoContent = CryptoUtils.AesEncrypt(value.getContent(), Config.SECRET_KEY);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
////                    LogUtils.i("SnackClient","加密数据" + cryptoContent);
//                    value.setContent(cryptoContent);
//                    return RequestBody.create(JSON, jsonUtils.toJson(value));
//                }
//            };
//        }
        return null;
    }
}
