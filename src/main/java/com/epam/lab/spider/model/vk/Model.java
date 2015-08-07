package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;
import com.epam.lab.spider.integration.vk.Value;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Model {
    private static final Logger LOG = Logger.getLogger(Model.class);

    private Map<String, String> fields = new HashMap<String, String>();

    public Model() {
        super();
    }

    public Model(Node root, Class<? extends Model> model) {
        addAll(root, model);
    }

    private static Set<String> keySet(Class<?> model) {
        Set<String> keys = new HashSet<String>();
        try {
            Field[] fields = model.getFields();
            for (Field field : fields)
                keys.add((String) field.get(String.class));
        } catch (IllegalArgumentException e) {
            LOG.error(e.getLocalizedMessage(), e);
        } catch (IllegalAccessException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return keys;
    }

    protected void addAll(Node root, Class<?> model) {
        Set<String> keys = keySet(model);
        for (String key : keys) {
            add(key, root.parse(key));
        }
    }

    public void add(String key, String value) {
        if (value != null) fields.put(key, value);
    }

    public boolean isField(String key) {
        return fields.containsValue(key);
    }

    public void remove(String key) {
        fields.remove(key);
    }

    public Set<String> getKeys() {
        return fields.keySet();
    }

    public Value get(final String key) {
        return new Value() {
            Object obj = fields.get(key);

            @Override
            public boolean toBoolean() {
                switch (toByte()) {
                    case 1:
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public byte toByte() {
                try {
                    return Byte.parseByte(toString());
                } catch (NumberFormatException e) {
                    return 0;
                }
            }

            @Override
            public double toDouble() {
                try {
                    return Double.parseDouble(toString());
                } catch (NumberFormatException e) {
                    return 0;
                }
            }

            @Override
            public int toInt() {
                try {
                    return Integer.parseInt(toString());
                } catch (NumberFormatException e) {
                    return 0;
                }
            }

            @Override
            public long toLong() {
                try {
                    return Long.parseLong(toString());
                } catch (NumberFormatException e) {
                    return 0;
                }
            }

            @Override
            public URL toURL() {
                try {
                    return new URL(toString());
                } catch (MalformedURLException e) {
                    return null;
                }
            }

            @Override
            public Date toDate() {
                return new Date(toInt() * 1000L);
            }

            @Override
            public String toString() {
                return (String) obj;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {")
                .append("id='").append(get("id").toString()).append("', ")
                .append(fields.keySet()).append("}");
        return sb.toString();
    }

}
