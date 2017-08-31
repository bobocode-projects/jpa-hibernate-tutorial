package com.bobocode.servlet;


import com.bobocode.dao.UserDao;
import com.bobocode.dao.impl.UserDaoImpl;
import com.bobocode.exception.InvalidRequestParametersException;
import com.bobocode.model.Credentials;
import com.bobocode.model.User;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.bobocode.util.StringUtil.parseUrlMapping;

@WebServlet(name = "UserServlet", urlPatterns = "/user/*")
public class UserServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        userDao = new UserDaoImpl(emf);
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
        User user = createEntity(req);
        userDao.save(user);
        processGetById(req, resp, user.getId());
    }

    private void processGetById(HttpServletRequest req, HttpServletResponse resp, Long id) throws IOException, ServletException {
        User user = userDao.findOne(id);

        if (user != null) {
            req.setAttribute("user", user);
            req.getRequestDispatcher("/user.jsp").forward(req, resp);
        } else {
            handleNotFound(resp, "User not found by id=" + id);
        }
    }

    private void processGetList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> userList = userDao.findAll();
        req.setAttribute("userList", userList);
        req.getRequestDispatcher("/list.jsp").forward(req, resp);
    }

    private User createEntity(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String pass = req.getParameter("password");
        String email = req.getParameter("email");
        LocalDate birthday = LocalDate.parse(req.getParameter("birthday"));

        return new User(firstName, lastName, birthday, new Credentials(email, pass));
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
