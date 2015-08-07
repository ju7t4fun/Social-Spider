package com.epam.lab.spider.model.entity.impl.persistence;

import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.model.entity.impl.PostImpl;
import com.epam.lab.spider.persistence.service.AttachmentService;
import com.epam.lab.spider.persistence.service.ServiceFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Yura Kovalik
 */
public class PersistenceBindPostImpl extends PostImpl {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static AttachmentService attachmentService = PersistenceBindPostImpl.factory.create(AttachmentService.class);

    public Set<Attachment> getAttachments() {
        if (super.getAttachments() == null) {
            if (getId() == null)
                setAttachments(new HashSet<Attachment>());
            else
                setAttachments(new HashSet<>(attachmentService.getByPostId(getId())));
        }
        return super.getAttachments();
    }
}
