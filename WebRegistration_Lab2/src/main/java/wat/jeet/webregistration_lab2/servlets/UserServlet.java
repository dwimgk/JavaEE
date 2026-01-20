package wat.jeet.webregistration_lab2.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import wat.jeet.webregistration_lab2.UsersLab;
import wat.jeet.webregistration_lab2.UsersService;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    private UsersService service;

    @Override
    public void init() {
        service = new UsersService();
    }

    @Override
    public void destroy() {
        if (service != null) service.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "home";

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><body>");
            out.println("<h2>Users (JPA)</h2>");

            if ("list".equals(action)) {
                List<UsersLab> users = service.findAll();
                out.println("<table border='1' cellpadding='5'>");
                out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Age</th><th>Date</th></tr>");
                for (UsersLab u : users) {
                    out.println("<tr>");
                    out.println("<td>" + u.getId() + "</td>");
                    out.println("<td>" + u.getName() + "</td>");
                    out.println("<td>" + u.getEmail() + "</td>");
                    out.println("<td>" + u.getAge() + "</td>");
                    out.println("<td>" + u.getRegistrationDate() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            } else {
                out.println("<p>Use index.html to create/list/delete users.</p>");
            }

            out.println("<form action='index.html'>");
            out.println("<button type='submit'>Back</button>");
            out.println("</form>");
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String methodOverride = request.getParameter("_method");
        if ("DELETE".equalsIgnoreCase(methodOverride)) {
            int id = Integer.parseInt(request.getParameter("id"));
            service.deleteById(id);
            response.sendRedirect("UserServlet?action=list");
            return;
        }

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int age = Integer.parseInt(request.getParameter("age"));

        service.create(name, email, age);
        response.sendRedirect("UserServlet?action=list");
    }
}
