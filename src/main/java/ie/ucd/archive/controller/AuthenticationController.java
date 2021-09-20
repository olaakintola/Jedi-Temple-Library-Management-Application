package ie.ucd.archive.controller;

import ie.ucd.archive.model.User;
import ie.ucd.archive.model.UserRepository;
import ie.ucd.archive.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class AuthenticationController {
    @Autowired
    private UserSession userSession;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(Model model) {
        setUpNavBar(model);
        //model.addAttribute("title", "Archive User: Login");
        if (userSession.getLoginFailed()) {
            model.addAttribute("error", "Username or Password invalid");
            userSession.setLoginFailed(false);
        }
        return "login.html";
    }

    @PostMapping("/login")
    public void doLogin(String username, String password, HttpServletResponse response) throws Exception {
        //System.out.println("Username: " + username + " Password: " + password);
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            userSession.setUser(user.get());
            userSession.setLoginFailed(false);
            //System.out.println(userSession.getUser().user_details());
            response.sendRedirect("/user");
            //TODO add user.isadmin() logic check to login as given user
        } else {
            userSession.setLoginFailed(true);
            response.sendRedirect("/");
        }
    }

    @PostMapping("/signedup")
    public void signedup(String signUpType, String username, String age, String address, String password, HttpServletResponse response) throws IOException {
        try {
            // checking valid integer using parseInt() method
            Integer.parseInt(age);
            //System.out.println(age + " is a valid integer number");
        } catch (NumberFormatException e) {
            //System.out.println(age + " is not a valid integer number");
        }
        Integer user_age = Integer.parseInt(age);
        User newUser = new User(username, password, signUpType, address, 0, true, user_age < 18);
        userRepository.save(newUser);
        userSession.setUser(newUser);
        userSession.setLoginFailed(false);
        //System.out.println("New User : \n" + userSession.getUser().user_details());
        response.sendRedirect("/user?id=" + newUser.getId());
    }


    @PostMapping("/updateaccount")
    public void updateAccount(String signUpType, String username, String address, String password, HttpServletResponse response) throws IOException {
        if (!userSession.getLoginFailed()) {
            //System.out.println(userSession.getUser().user_details());
            User update_user = userSession.getUser();
            if(signUpType != null)  {
                if(!signUpType.equals("")) {
                    update_user.setRole(signUpType);
                }
            }
            if(username != null) {
                if(!username.equals("")) {
                    update_user.setUsername(username);
                }
            }
            if(address != null) {
                if(!address.equals("")) {
                    update_user.setAddress(address);
                }
            }
            if(password != null) {
                if(!password.equals("")) {
                    update_user.setPassword(password);
                }
            }
            //System.out.println("Here are the parameters:" + signUpType + "_" + username + "_" + address + "_" + password);
            userRepository.save(update_user);
            userSession.setUser(update_user);
            userSession.setLoginFailed(false);
            //System.out.println("Updated User :\n" + userSession.getUser().user_details());
            response.sendRedirect("/user?id=" + userSession.getUser().getId());
        } else {
            //System.out.println("NO USER :");
            response.sendRedirect("/signup");
        }
    }

    @PostMapping("/changeuser")
    public void changeUser(String search, HttpServletResponse response) throws IOException {
        //System.out.println("-----SEARCH PARAM: " + search);
        Optional<User> found_user = userRepository.findUserByUsername(search);
        //System.out.println(userSession.getUser().user_details());
        if (found_user.isPresent()) {
            //System.out.println("--------------UPDATE ACTION--------------");
            userSession.setUser(found_user.get());
            //System.out.println("CURRENT USER:");
            //System.out.println(userSession.getUser().user_details());
        } //else System.out.println("NO USER FOUND");
        response.sendRedirect("/user");
    }

    @GetMapping("/changebyid")
    public void changeById(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        //System.out.println("-----SEARCH PARAM: " + id);
        Optional<User> found_user = userRepository.findById(id);
        //System.out.println("CURRENT USER:");
        //System.out.println(userSession.getUser().user_details());
        if (found_user.isPresent()) {
            //System.out.println("--------------UPDATE ACTION--------------");
            userSession.setUser(found_user.get());
            //System.out.println("CURRENT USER:");
            //System.out.println(userSession.getUser().user_details());
        }// else System.out.println("NO USER FOUND");
        response.sendRedirect("/user");
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) throws Exception {
        userSession.setUser(null);
        userSession.setLoginFailed(true);
        response.sendRedirect("/");
    }

    private void setUpNavBar(Model model) {
        String presentUserRole;
        if (userSession.getUser() == null) presentUserRole = "noUser";
        else presentUserRole = userSession.getUser().getRole();
        model.addAttribute("userRole", presentUserRole);
        //System.out.println("setting up navigation bar:" + presentUserRole);
    }
}
