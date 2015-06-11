package com.epam.lab.spider.controller.vk;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class NodeXml implements Node {

    private Element root;
    private List<Node> child = null;

    public NodeXml(Document doc) {
        root = doc.getDocumentElement();
    }

    public NodeXml(org.w3c.dom.Node node) {
        root = (Element) node;
    }

    @Override
    public String name() {
        return root.getTagName();
    }

    @Override
    public Value value() {
        return new Value() {
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
                try {
                    return root.getTextContent();
                } catch (NullPointerException e) {
                    return null;
                }
            }
        };
    }

    @Override
    public boolean hasChild() {
        if (child().size() > 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean hasChild(String name) {
        if (child(name).size() > 0)
            return true;
        else
            return false;
    }

    @Override
    public List<Node> child() {
        if (child == null) {
            child = new LinkedList<Node>();
            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() == 1)
                    child.add(new NodeXml(nodes.item(i)));
            }
        }
        return child;
    }

    @Override
    public List<Node> child(String name) {
        this.child();
        List<Node> selected = new LinkedList<Node>();
        for (Node child : this.child)
            if (child.name().equals(name))
                selected.add(child);
        return selected;
    }

    @Override
    public String parse(String key) {
        try {
            String value;
            int index = key.indexOf('.');
            if (index > -1) {
                value = child(key.substring(0, index)).get(0).parse(key.substring(index + 1));
            } else {
                value = child(key).get(0).value().toString();
            }
            return value;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("com.vk.Node {")
                .append("name='").append(name()).append("', ");
        if (hasChild()) {
            String args = null;
            for (Node child : this.child)
                args = args == null ? child.name() : args + ", " + child.name();
            sb.append("child=[").append(args).append("]}");
        } else
            sb.append("value='").append(value().toString()).append("'}");
        return sb.toString();
    }

}
