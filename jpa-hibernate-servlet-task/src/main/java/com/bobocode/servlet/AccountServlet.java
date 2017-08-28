package com.bobocode.servlet;


import com.bobocode.dao.AccountDao;
import com.bobocode.dao.impl.AccountDaoImpl;
import com.bobocode.exception.InvalidRequestParametersException;
import com.bobocode.model.Account;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.bobocode.util.StringUtil.parseUrlMapping;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@WebServlet(name = "AccountServlet", urlPatterns = "/account/*")
public class AccountServlet extends HttpServlet {
    private AccountDao accountDao;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        accountDao = new AccountDaoImpl(emf);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mappingUrl = parseUrlMapping(req);

        if (mappingUrl.isEmpty()) {
            processGetRequest(req, resp);
        } else if (mappingUrl.equals("create")) {
            req.getRequestDispatcher("/form.jsp").forward(req, resp);
        } else {
            handleInvalidUrl(resp);
        }
    }

    private void processGetRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            Long id = fetchIdParameter(req);
            if (id != null) {
                processGetById(req, resp, id);
            } else {
                processGetList(req, resp);
            }
        } catch (InvalidRequestParametersException e) {
            processBadRequest(resp, e.getMessage());
        }
    }

    /**
     * Parses input request, and tries to fetch a Long id. If id is missing, or it is not correct,
     * it throws {@link InvalidRequestParametersException}
     *
     * @param req input request
     * @return Long id
     * @throws IOException
     */
    private Long fetchIdParameter(HttpServletRequest req) throws IOException {
        String idStr = req.getParameter("id");
        Long id = null;
        if (idStr != null) {
            try {
                id = Long.parseLong(idStr);
            } catch (NumberFormatException e) {
                throw new InvalidRequestParametersException("Input parameter id in not valid");
            }
        }
        return id;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mappingUrl = parseUrlMapping(req);
        if (mappingUrl.isEmpty()) {
            processCreateAccount(req, resp);
        } else {
            handleInvalidUrl(resp);
        }
    }

    private void processCreateAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Account account = createEntity(req);
        accountDao.save(account);
        processGetById(req, resp, account.getId());
    }

    private void processGetById(HttpServletRequest req, HttpServletResponse resp, Long id) throws IOException, ServletException {
        Account account = accountDao.findOne(id);

        if (account != null) {
            req.setAttribute("account", account);
            req.getRequestDispatcher("/account.jsp").forward(req, resp);
        } else {
            handleNotFound(resp, "Account not found by id=" + id);
        }
    }

    private void processGetList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Account> accountList = accountDao.findAll();
        req.setAttribute("accountList", accountList);
        req.getRequestDispatcher("/list.jsp").forward(req, resp);
    }

    private Account createEntity(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String pass = req.getParameter("password");
        String email = req.getParameter("email");

        String birthdayString = req.getParameter("birthday");
        LocalDate birthday = isEmpty(birthdayString) ? LocalDate.now() : LocalDate.parse(birthdayString);

        String balanceString = req.getParameter("balance");
        BigDecimal balance = isEmpty(balanceString) ? BigDecimal.ZERO : BigDecimal.valueOf(Double.parseDouble(balanceString));

        return new Account(firstName, lastName, email, pass, birthday, balance);
    }

    private void handleInvalidUrl(HttpServletResponse resp) throws IOException {
        handleNotFound(resp, "Incorrect URL");
    }

    private void handleNotFound(HttpServletResponse resp, String message) throws IOException {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, message);
    }

    /**
     * Sends error response with a customer message to the client.
     *
     * @param resp
     * @param message error message
     * @throws IOException
     */
    private void processBadRequest(HttpServletResponse resp, String message) throws IOException {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
    }
}
