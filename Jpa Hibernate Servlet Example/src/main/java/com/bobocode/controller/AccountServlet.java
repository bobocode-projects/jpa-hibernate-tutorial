package com.bobocode.controller;


import com.bobocode.dao.AccountDao;
import com.bobocode.model.Account;
import com.bobocode.util.DaoUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.bobocode.util.StringUtil.getUrlMapping;
import static com.bobocode.util.StringUtil.isId;

@WebServlet(name = "AccountServlet", urlPatterns = "/account/*")
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mappingUrl = getUrlMapping(req);
        if (mappingUrl.equals("create")) {
            req.getRequestDispatcher("/create.jsp").forward(req, resp);
        } else if (mappingUrl.isEmpty()) {
            processGetList(req, resp);
        } else if (isId(mappingUrl)) {
            processGetAccount(Long.parseLong(mappingUrl), req, resp);
        } else {
            handleError(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mappingUrl = getUrlMapping(req);
        if (mappingUrl.isEmpty()) {
            processCreateAccount(req, resp);
        } else {
            handleError(req, resp);
        }
    }

    private void processCreateAccount(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void processGetAccount(long id, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        AccountDao accountDao = DaoUtil.getAccountDao();
        Account account = accountDao.findOne(id);

        if (account != null) {
            req.setAttribute("account", account);
            req.getRequestDispatcher("/account.jsp").forward(req, resp);
        } else {
            handleNotFound(req, resp);
        }
    }

    private void processGetList(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp) {
    }


    private void handleNotFound(HttpServletRequest req, HttpServletResponse resp) {

    }
}
