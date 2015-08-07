package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.ServerLocationUtils;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.ReplaceHtmlTags;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.persistence.service.PostService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Marian Voronovskyi
 */
public class EditPostCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(EditPostCommand.class);
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService service = factory.create(PostService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        int postID = Integer.parseInt(request.getParameter("post_id"));
        Map<String, String> urlType = (Map<String, String>) request.getSession().getAttribute("files_url");
        Attachment attachment;
        Set<Attachment> attachments = new HashSet<>();
        for (Map.Entry<String, String> entry : urlType.entrySet()) {
            attachment = BasicEntityFactory.getSynchronized().createAttachment();
            attachment.setPayload(ServerLocationUtils.getServerPath(request) + entry.getKey());
            attachment.setType(Attachment.Type.valueOf(entry.getValue()));
            attachment.setDeleted(false);
            attachment.setPostId(postID);
            attachments.add(attachment);
        }
        String postText = request.getParameter("text");
        postText = ReplaceHtmlTags.reaplaceAll(postText);
        JSONObject jsonObject = new JSONObject();

        Post post = service.getById(postID);
        post.setMessage(postText);
        post.getAttachments().addAll(attachments);
        LOG.info(post);
        if (service.update(postID, post)) {
            jsonObject.put("status", "success");
            jsonObject.put("message", UTF8.encoding(bundle.getString("notification.edit.post.success")));
        } else {
            jsonObject.put("status", "error");
            jsonObject.put("message", UTF8.encoding(bundle.getString("notification.edit.post.error")));
        }
        request.getSession().removeAttribute("files_url");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObject);
    }
}
