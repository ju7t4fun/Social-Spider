package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.persistence.service.AttachmentService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Marian Voronovskyi
 */
public class DeleteAttachmentCommand implements ActionCommand {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static AttachmentService service = factory.create(AttachmentService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int attachId = Integer.parseInt(request.getParameter("attachId"));
        JSONObject jsonObject = new JSONObject();

        if (service.delete(attachId)) {
            jsonObject.put("status", "success");
            jsonObject.put("message", "Attachment successfully deleted!");
        } else {
            jsonObject.put("status", "error");
            jsonObject.put("message", "Error!");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObject);
    }
}
