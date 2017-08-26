package com.bobocode.servlet;


import com.bobocode.dao.AccountDao;
import com.bobocode.model.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    private void processCreateAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Account account = buildEntity(req);
        DaoUtil.getAccountDao().save(account);
        processGetAccount(account.getId(), req, resp);
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

    private void processGetList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Account> accountList = DaoUtil.getAccountDao().findAll();
        req.setAttribute("accountList", accountList);
        req.getRequestDispatcher("/list.jsp").forward(req, resp);
    }

    private Account buildEntity(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String pass = req.getParameter("password");
        String email = req.getParameter("email");
        LocalDate birthday = LocalDate.parse(req.getParameter("birthday"));
        BigDecimal balance = BigDecimal.valueOf(Double.parseDouble(req.getParameter("balance")));

        return new Account(firstName, lastName, email, pass, birthday, balance);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp) {
    }


    private void handleNotFound(HttpServletRequest req, HttpServletResponse resp) {

    }
}
