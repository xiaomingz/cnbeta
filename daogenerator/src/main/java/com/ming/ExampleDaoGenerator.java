package com.ming;

import com.ming.entity.CommentItem;
import com.ming.entity.NewsDetail;
import com.ming.entity.NewsItem;
import com.ming.interf.ID;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(3, "com.ming.cnbeta.db.greendao");

        addEntity(schema, NewsItem.class);
        addEntity(schema, NewsDetail.class);
        addEntity(schema, CommentItem.class);

        new DaoGenerator().generateAll(schema, args[0]);
    }

    /**
     * @param schema
     * @param t
     */
    private static void addEntity(Schema schema, Class<?> t) {
        Entity entity = schema.addEntity(t.getSimpleName());
        entity.setTableName("tb_" + t.getSimpleName());
        Field[] fields = t.getDeclaredFields();// 获得属性

        for (Field field : fields) {

            if (Modifier.isStatic(field.getModifiers())) {//如果是静态则不作处理
                continue;
            }

            Property.PropertyBuilder builder = null;
            String type = field.getGenericType().toString();
            switch (type){
                case Config.TYPE_STRING:
                    builder = entity.addStringProperty(field.getName());
                    break;

                case Config.TYPE_INTEGER:
                    builder = entity.addIntProperty(field.getName());
                    break;

                case Config.TYPE_DOUBLE:
                    builder = entity.addDoubleProperty(field.getName());
                    break;

                case Config.TYPE_BOOLEAN:
                    builder = entity.addBooleanProperty(field.getName());
                    break;

                case Config.TYPE_SHORT:
                    builder = entity.addShortProperty(field.getName());
                    break;

                case Config.TYPE_LONG:
                    builder = entity.addLongProperty(field.getName());
                    break;

                case Config.TYPE_FLOAT:
                    builder = entity.addFloatProperty(field.getName());
                    break;

                case Config.TYPE_BYTE:
                    builder = entity.addByteProperty(field.getName());
                    break;

                case Config.TYPE_DATE:
                    builder = entity.addDateProperty(field.getName());
                    break;

            }

            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType().equals(ID.class)){
                    builder.primaryKey();
                }

            }
        }
    }
}
