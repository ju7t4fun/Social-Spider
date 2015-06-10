package com.epam.lab.spider.model.vk;

import java.util.Locale;

public interface Attachment {

    Type getType();
    int getOwnerId();
    int getId();

    public static enum Type {

        PHOTO, POSTED_PHOTO, VIDEO, AUDIO,
        DOC, GRAFFITI, LINK, NOTE, APP, POLL,
        PAGE, ALBUM, PHOTOS_LIST;

        public static Type getByName(String name) {
            Type[] attachments = Type.values();
            for (Type attachment : attachments)
                if (attachment.toString().equals(name))
                    return attachment;
            return null;
        }

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.US);
        }

    }

}
