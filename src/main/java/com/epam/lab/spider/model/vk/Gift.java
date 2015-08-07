package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Gift extends Model {

    public static final String ID = "id";
    public static final String FROM_ID = "from_id";
    public static final String MESSAGE = "message";
    public static final String DATE = "date";
    public static final String GIFT_ID = "gift.id";
    public static final String GIFT_THUMB_256 = "gift.thumb_256";
    public static final String GIFT_THUMB_96 = "gift.thumb_96";
    public static final String GIFT_THUMB_48 = "gift.thumb_48";
    public static final String PRIVACY = "privacy";

    public Gift() {
        super();
    }

    public Gift(Node root) {
        super(root, Gift.class);
    }

    public static List<Gift> parseGift(Node root) {
        List<Gift> gifts = new ArrayList<Gift>();
        List<Node> nodes = root.child("gift_item");
        for (Node node : nodes)
            gifts.add(new Gift(node));
        return gifts;
    }

    public int getId() {
        return get(ID).toInt();
    }

    public int getFromId() {
        return get(FROM_ID).toInt();
    }

    public String getMessage() {
        return get(MESSAGE).toString();
    }

    public Date getDate() {
        return get(DATE).toDate();
    }

    public GiftItem getGift() {
        return new GiftItem() {
            @Override
            public int getId() {
                return get(GIFT_ID).toInt();
            }

            @Override
            public URL getThumb256() {
                return get(GIFT_THUMB_256).toURL();
            }

            @Override
            public URL getThumb96() {
                return get(GIFT_THUMB_96).toURL();
            }

            @Override
            public URL getThumb48() {
                return get(GIFT_THUMB_48).toURL();
            }
        };
    }

    public byte getPrivacy() {
        return get(PRIVACY).toByte();
    }

    public interface GiftItem {
        int getId();
        URL getThumb256();
        URL getThumb96();
        URL getThumb48();
    }

}
