package com.epam.lab.spider.controller.command.controller;

import com.epam.lab.spider.controller.command.ActionCommand;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @author Dzyuba Orest
 */
public class GetLangJSONCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(GetLangJSONCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        int lang = Integer.parseInt(bundle.getString("categoryLangCode"));

        if (lang == 1) {
            try {

                json.put("sEmptyTable", "No data available in table");
                json.put("sInfo", "Showing _START_ to _END_ of _TOTAL_ entries");
                json.put("sInfoEmpty", "Showing 0 to 0 of 0 entries");
                json.put("sInfoFiltered", "(filtered from _MAX_ total entries)");
                json.put("sInfoPostFix", "");
                json.put("sInfoThousands", ",");
                json.put("sLengthMenu", "Show _MENU_ entries");
                json.put("sLoadingRecords", "Loading...");
                json.put("sProcessing", "Processing...");
                json.put("sSearch", "Search:");
                json.put("sZeroRecords", "No matching records found");

                JSONObject subJsonPaginate = new JSONObject();
                subJsonPaginate.put("sFirst", "First");
                subJsonPaginate.put("sLast", "Last");
                subJsonPaginate.put("sNext", "Next");
                subJsonPaginate.put("sPrevious", "Previous");
                json.put("oPaginate", subJsonPaginate);

                JSONObject subJsonAria = new JSONObject();
                subJsonAria.put("sSortAscending", ": activate to sort column ascending");
                subJsonAria.put("sSortDescending", ": activate to sort column descending");
                json.put("oAria", subJsonAria);

            } catch (Exception e) {
                LOG.error(e.getLocalizedMessage(), e);
            }
        } else {


            try {

                json.put("sEmptyTable", "Немає доступних даних в таблиці");
                json.put("sInfo", "Показую від _START_ до _END_ з _TOTAL_ рядків");
                json.put("sInfoEmpty", "Показую від 0 до 0 з 0 рядків");
                json.put("sInfoFiltered", "(відфільтровано з _MAX_ сумарних рядків)");
                json.put("sInfoPostFix", "");
                json.put("sInfoThousands", ",");
                json.put("sLengthMenu", "Показати _MENU_ рядків");
                json.put("sLoadingRecords", "Завантаження...");
                json.put("sProcessing", "Триває процес обробки інформації...");
                json.put("sSearch", "Пошук:");
                json.put("sZeroRecords", "Не знайдено відповідних даних");

                JSONObject subJsonPaginate = new JSONObject();
                subJsonPaginate.put("sFirst", "Перша");
                subJsonPaginate.put("sLast", "Остання");
                subJsonPaginate.put("sNext", "Наступна");
                subJsonPaginate.put("sPrevious", "Попередня");
                json.put("oPaginate", subJsonPaginate);

                JSONObject subJsonAria = new JSONObject();
                subJsonAria.put("sSortAscending", ": активувати для сортування за зростанням");
                subJsonAria.put("sSortDescending", ": активувати для сортування за спаданням");
                json.put("oAria", subJsonAria);

            } catch (Exception e) {
                LOG.error(e.getLocalizedMessage(), e);
            }

        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
