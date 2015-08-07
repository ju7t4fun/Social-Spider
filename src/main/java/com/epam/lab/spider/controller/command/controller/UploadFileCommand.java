package com.epam.lab.spider.controller.command.controller;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.FileType;
import com.epam.lab.spider.controller.utils.UTF8;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Marian Voronovskyi
 */
public class UploadFileCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(UploadFileCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");

        String jsonError = "";
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("application/json");
        Map<String, String> urlType = (Map<String, String>) request.getSession().getAttribute("files_url");

        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                String fileName;
                String filePath;
                FileType.Type type;
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        fileName = new File(item.getName()).getName();
                        type = FileType.getType(fileName);
                        if (type != null) {
                            filePath = context.getRealPath(type.getPath());
                            SecureRandom random = new SecureRandom();
                            fileName = new BigInteger(130, random).toString(32) +
                                    FileType.parseFileFormat(fileName);
                            File file = new File(filePath);
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            item.write(new File(filePath + File.separator + fileName));

                            urlType.put(type.getPath() + "/" + fileName, type.toString());
                            //filesUrl.add(type.getPath() + "/" + fileName);
                            LOG.debug("File uploaded successfully");
                            response.getWriter().print(new JSONObject().put("success", UTF8.encoding(bundle.getString("notification.upload.file.success"))));
                        } else {
                            jsonError = UTF8.encoding(bundle.getString("notification.wrong.file.format"));
                        }
                    }
                }
            } else {
                jsonError = UTF8.encoding(bundle.getString("notification.request.error"));
            }
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage(), e);
            jsonError = UTF8.encoding(bundle.getString("notification.upload.file.error"));
        } finally {
            if (!urlType.isEmpty()) {
                LOG.debug(urlType);
                request.getSession().setAttribute("files_url", urlType);
            }
            if (!jsonError.isEmpty()) {
                response.getWriter().print(new JSONObject().put("error", jsonError));
            }
        }
    }
}
