package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Page extends Model implements Attachment {

    public static final String ID = "id";
    public static final String GROUP_ID = "group_id";
    public static final String CREATOR_ID = "creator_id";
    public static final String TITLE = "title";
    public static final String CURRENT_USER_CAN_EDIT = "current_user_can_edit";
    public static final String CURRENT_USER_CAN_EDIT_ACCESS = "current_user_can_edit_access";
    public static final String WHO_CAN_VIEW = "who_can_view";
    public static final String WHO_CAN_EDIT = "who_can_edit";
    public static final String EDITED = "edited";
    public static final String CREATED = "created";
    public static final String EDITOR_ID = "editor_id";
    public static final String VIEWS = "views";
    public static final String PARENT = "parent";
    public static final String PARENT2 = "parent2";
    public static final String SOURCE = "source";
    public static final String HTML = "html";
    public static final String VIEW_URL = "view_url";

    public Page() {
        super();
    }

    public Page(Node root) {
        super(root, Page.class);
    }

    public static List<Page> parsePage(Node root) {
        List<Page> pages = new ArrayList<Page>();
        List<Node> nodes = root.child("page");
        for (Node node : nodes)
            pages.add(new Page(node));
        return pages;
    }

    @Override
    public Type getType() {
        return Type.DOC;
    }

    @Override
    public int getOwnerId() {
        return getCreatorId();
    }

    @Override
    public int getId() {
        return get(ID).toInt();
    }

    public int getGroupId() {
        return get(GROUP_ID).toInt();
    }

    public int getCreatorId() {
        return get(CREATOR_ID).toInt();
    }

    public String getTitle() {
        return get(TITLE).toString();
    }

    public boolean getCurrentUserCanEdit() {
        return get(CURRENT_USER_CAN_EDIT).toBoolean();
    }

    public boolean getCurrentUserCanEditAccess() {
        return get(CURRENT_USER_CAN_EDIT_ACCESS).toBoolean();
    }

    public byte getWhoCanView() {
        return get(WHO_CAN_VIEW).toByte();
    }

    public byte getWhoCanEdit() {
        return get(WHO_CAN_EDIT).toByte();
    }

    public Date getEdited() {
        return get(EDITED).toDate();
    }

    public Date getCreated() {
        return get(CREATED).toDate();
    }

    public int getEditorId() {
        return get(EDITOR_ID).toInt();
    }

    public int getViews() {
        return get(VIEWS).toInt();
    }

    public String getParent() {
        return get(PARENT).toString();
    }

    public String getParent2() {
        return get(PARENT2).toString();
    }

    public String getSource() {
        return get(SOURCE).toString();
    }

    public String getHtml() {
        return get(HTML).toString();
    }

    public URL getViewUrl() {
        return get(VIEW_URL).toURL();
    }

    public static class Version extends Model {

        public static final String HID = "hid";
        public static final String LENGTH = "length";
        public static final String EDITED = "edited";
        public static final String EDITOR_ID = "editor_id";
        public static final String EDITOR_NAME = "editor_name";

        public Version() {
            super();
        }

        public Version(Node root) {
            super(root, Version.class);
        }

        public static List<Version> parseVersion(Node root) {
            List<Version> versions = new ArrayList<Version>();
            List<Node> nodes = root.child("page_version");
            for (Node node : nodes)
                versions.add(new Version(node));
            return versions;
        }

    }

}
