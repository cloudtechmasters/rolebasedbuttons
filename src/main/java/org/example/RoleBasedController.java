package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RoleBasedController {

    @GetMapping({"/dashboard", "/dashboard.html"})  // Handle both /dashboard and /dashboard.html
    public String dashboard(HttpServletRequest request, HttpServletResponse response, Model model) {
        String grpCode = request.getHeader("GrpCode");  // Use "GrpCode" header

        if (grpCode == null) {
            // Return 400 Bad Request if GrpCode is missing
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "error";  // Return an error page (or handle accordingly)
        }

        // Role determination based on GrpCode header
        String role = null;

        if ("GRP_ADMIN".equalsIgnoreCase(grpCode)) {
            role = "ADMIN";
        } else if ("GRP_CARE".equalsIgnoreCase(grpCode)) {
            role = "CARE";
        } else if ("GRP_FRONTLINE".equalsIgnoreCase(grpCode)) {
            role = "FRONTLINE";
        } else {
            // Return 400 Bad Request if GrpCode is invalid
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "error";  // Return an error page (or handle accordingly)
        }

        // Pass the determined role to the Thymeleaf view
        model.addAttribute("role", role);

        return "dashboard";  // Render dashboard.html based on the role
    }
}
