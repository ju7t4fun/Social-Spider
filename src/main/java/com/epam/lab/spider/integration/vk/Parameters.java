package com.epam.lab.spider.integration.vk;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Parameters {
    private static final Logger LOG = Logger.getLogger(Parameters.class);

    private List<Field> fields = new ArrayList<>();
    private Request.Method method = Request.Method.GET;
    private Response.Type type = Response.Type.XML;

    public Parameters add(Field field) {
        if (!isField(field.name))
            fields.add(field);
        return this;
    }

    public Parameters add(String name, String value) {
        return add(new Field(name, value));
    }

    public Parameters add(String name, byte value) {
        return add(new Field(name, value));
    }

    public Parameters add(String name, int value) {
        return add(new Field(name, value));
    }

    public Parameters add(String name, long value) {
        return add(new Field(name, value));
    }

    public Parameters add(String name, boolean value) {
        return add(new Field(name, value));
    }

    public Parameters add(String name, double value) {
        return add(new Field(name, value));
    }

    public boolean isField(String name) {
        for (Field field : fields) {
            if (field.name.equals(name))
                return true;
        }
        return false;
    }

    public String get(String name) {
        for (Field field : fields) {
            if (field.name.equals(name))
                return field.value;
        }
        return null;
    }

    public void remove(String name) {
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).name.equals(name))
                fields.remove(i);
        }
    }

    public List<String> getKeys() {
        List<String> keys = new ArrayList<>();
        for (Field field : fields) {
            keys.add(field.name);
        }
        return keys;
    }

    public Request.Method getRequestMethod() {
        return method;
    }

    public void setRequestMethod(Request.Method method) {
        this.method = method;
    }

    public Response.Type getResponseType() {
        return type;
    }

    public void setResponseType(Response.Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {")
                .append("request=").append(method.toString()).append(", ")
                .append("response=").append(type.toString()).append(", ")
                .append("fields=").append(fields).append("}");
        return sb.toString();
    }

    public class Field {

        private String name, value;

        public Field(String name, String value) {
            try {
                this.name = URLEncoder.encode(name, "UTF-8");
                this.value = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOG.error(e.getLocalizedMessage(), e);
            }
        }

        public Field(String name, byte value) {
            this(name, String.valueOf(value));
        }

        public Field(String name, int value) {
            this(name, String.valueOf(value));
        }

        public Field(String name, long value) {
            this(name, String.valueOf(value));
        }

        public Field(String name, boolean value) {
            this(name, String.valueOf(value));
        }

        public Field(String name, double value) {
            this(name, String.valueOf(value));
        }

        @Override
        public String toString() {
            return name + "='" + value + "'";
        }

    }

}
