package vuln.zsmart.ma.vulnnosql.Controller;

import net.bytebuddy.utility.RandomString;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vuln.zsmart.ma.vulnnosql.AppConfiguration.Utility;
import vuln.zsmart.ma.vulnnosql.Beans.*;
import vuln.zsmart.ma.vulnnosql.Services.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    SocialMediaService socialMediaService;
    @Autowired
    MyUserDetailServices myUserDetailServices;
    @Autowired
    VulnerabilityService vulnerabilityService;
    @Autowired
    PhotoService photoService;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    RoleService roleService;
    @Autowired
    ConfirmationTokenRegistrationService confirmationTokenRegistrationService;

    @GetMapping("/profile.html")
    public String tables(@AuthenticationPrincipal UserPrincipal userPrincipal ,Model model)
    {
        ObjectId id = userPrincipal.getId();
        User user = myUserDetailServices.getUserById(id).get();
        Photo photo = user.getPhoto();
        List<User> list = myUserDetailServices.getAllUsers();
        model.addAttribute("users",list);
        model.addAttribute("author",user);
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }
        return "userEdit";
    }

    @GetMapping("/users.html")
    public String users(@AuthenticationPrincipal UserPrincipal userPrincipal,Model model)
    {
        ObjectId id_u = userPrincipal.getId();
        User user = myUserDetailServices.getUserById(id_u).get();
        Photo photo = user.getPhoto();
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }
        List<User> list = myUserDetailServices.getAllUsers();
        model.addAttribute("users",list);
        model.addAttribute("author",user);
        return "users";
    }

    @PostMapping("/users.html/AddNew")
    public String AddNew(User user, @RequestParam("image") MultipartFile img,Model model)throws IOException
    {

        User testUser = myUserDetailServices.getUserByEmail(user.getEmail());

        if(testUser != null)
        {
            model.addAttribute("errorEmail","This Email already exists !");
        }
        else
        {
            ObjectId id_p = myUserDetailServices.addPhotoSansTitle(img);
            Photo photo = myUserDetailServices.getPhoto(id_p);
            Role role = roleService.getRoleByDescription("USER");
            user.setPhoto(photo);
            List<Role> list = user.getRoles();
            list = new ArrayList<>();
            list.add(role);
            user.setRoles(list);
            myUserDetailServices.save(user);

            ConfirmationTokenRegistration confirmationToken = new ConfirmationTokenRegistration(user);

            confirmationTokenRegistrationService.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("support@vuln.com");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8082/register.html/confirm-account?token="+confirmationToken.getConfirmationToken());

            confirmationTokenRegistrationService.sendEmaill(mailMessage);

            model.addAttribute("emailId", user.getEmail());;
            model.addAttribute("msgAddUser","User Has Been Added Successfully !");
        }
            return  "users";
    }

    @RequestMapping("/users.html/find")
    @ResponseBody
    public User findByUname(String username)
    {
        return myUserDetailServices.findUserByUsername(username);
    }

    @RequestMapping("/users.html/id")
    @ResponseBody
    public Optional<User> findById(ObjectId id)
    {
        return myUserDetailServices.getUserById(id);
    }

   @GetMapping("/resume.html")
    public String getttresume(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model)
    {
        ObjectId id = userPrincipal.getId();
        User user = myUserDetailServices.getUserById(id).get();
        Photo photo = user.getPhoto();
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }
        return "resume";
    }
    @GetMapping("/users.html/update/byUsername/{username}")
    public String doudou(@AuthenticationPrincipal UserPrincipal userPrincipal,@PathVariable String username, Model model)
    {
        ObjectId id = userPrincipal.getId();
        User user1 = myUserDetailServices.getUserById(id).get();
        User user = myUserDetailServices.findUserByUsername(username);
        Photo photo = user1.getPhoto();
        if(photo!=null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }
        model.addAttribute("userEDIT",user);
        return "usersUpdate";
    }


    @RequestMapping(value="/users.html/testEdit/update")
    public String update(User user) {

        Optional<User> user1 = myUserDetailServices.getUserById(user.getId());
        if (user1.isPresent())
        {
            User _user = user1.get();
            _user.setFirstName(user.getFirstName());
            _user.setLastName(user.getLastName());
            _user.setUsername(user.getUsername());
            _user.setAdress(user.getAdress());
            _user.setEmail(user.getEmail());
            _user.setToken(user.getToken());
            _user.setVulnerbilites(user.getVulnerbilites());
            _user.setRoles(user.getRoles());
            _user.setPassword(user.getPassword());
            _user.setPhoto(user.getPhoto());
            _user.setSocialMedia(user.getSocialMedia());
            _user.setEnabled(user.getEnabled());
            myUserDetailServices.save(_user);

            return "redirect:/users.html/update/byUsername/"+_user.getUsername();
        }else
        {
            return "users";
        }

    }

    @RequestMapping(value="/users.html/updateSocialMedia", method = {RequestMethod.PUT, RequestMethod.GET})
    public String update(@AuthenticationPrincipal UserPrincipal userPrincipal,SocialMedia socialMedia) {
        ObjectId id= userPrincipal.getId();
        User user = myUserDetailServices.getUserById(id).get();
        socialMedia.setUserProfie(user);
        user.setSocialMedia(socialMedia);
        socialMediaService.save(socialMedia);
        myUserDetailServices.save(user);
        return "redirect:/users.html";
    }

    @RequestMapping(value="/users.html/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(String username) {
        User user = myUserDetailServices.findUserByUsername(username);
        Photo photo = user.getPhoto();
        List<Vulnerbilite> vulnerbilites = user.getVulnerbilites();
        for (Vulnerbilite vulnerbilite : vulnerbilites)
        {
            vulnerabilityService.deleteVulnById(vulnerbilite.getId());
        }
        photoService.deletePhotoById(photo.getId());
        myUserDetailServices.deleteUserById(user.getId());
        return "redirect:/users.html";
    }
    @RequestMapping(value="/users.html/delete/id", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(ObjectId id) {
        User user = myUserDetailServices.getUserById(id).get();
        Photo photo = user.getPhoto();
        List<Vulnerbilite> vulnerbilites = user.getVulnerbilites();
        for (Vulnerbilite vulnerbilite : vulnerbilites)
        {
            vulnerabilityService.deleteVulnById(vulnerbilite.getId());
        }
        photoService.deletePhotoById(photo.getId());
        myUserDetailServices.deleteUserById(user.getId());
        return "redirect:/users.html";
    }


    @GetMapping("/usersUpdate.html")
    public String getpage(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model)
    {
        ObjectId id  = userPrincipal.getId();
        User user = myUserDetailServices.getUserById(id).get();
        Photo photo = user.getPhoto();
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }
        return "usersUpdate";
    }


    @GetMapping("/forgot-password.html")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password.html")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            myUserDetailServices.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL (request) + "/reset-password.html?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "forgot-password";
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("support@vuln.com", "Vuln Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }


    @GetMapping("/reset-password.html")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {

        User user = myUserDetailServices.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
        return "reset-password";
    }

    @PostMapping("/reset-password.html")
    public String processResetPassword(HttpServletRequest request, Model model) {

        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = myUserDetailServices.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            myUserDetailServices.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "redirect:/login.html";
    }

    @PostMapping("/myprofile.html/add-photo")
    public String addPhoto(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam("title") String title, @RequestParam("image") MultipartFile image, Model model) throws IOException {

        ObjectId id_p = myUserDetailServices.addPhoto(title, image);
        Photo photo = myUserDetailServices.getPhoto(id_p);
        ObjectId id_u = userPrincipal.getId();
        User author = myUserDetailServices.getUserById(id_u).get();

        if(author.getPhoto() != null)
        {
            author.setPhoto(null);
        }
        author.setPhoto(photo);
        myUserDetailServices.save(author);
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }
        return "redirect:/myprofile.html";
    }


    @RequestMapping("/profile.html/findByUsername")
    @ResponseBody
    public Optional<User> findByUsernameProfile(String username)
    {
        return Optional.ofNullable(myUserDetailServices.findUserByUsername(username));
    }

    @RequestMapping(value="/profile.html/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteUsernameProfile(String username) {
        User user = myUserDetailServices.findUserByUsername(username);
        Photo photo = user.getPhoto();
        List<Vulnerbilite> vulnerbilites = user.getVulnerbilites();
        for (Vulnerbilite vulnerbilite : vulnerbilites)
        {
            vulnerabilityService.deleteVulnById(vulnerbilite.getId());
        }
        photoService.deletePhotoById(photo.getId());
        myUserDetailServices.deleteUserById(user.getId());
        return "redirect:/logout";
    }

    @GetMapping("/myprofile.html")
    public String myprofile(@AuthenticationPrincipal UserPrincipal userPrincipal ,Model model)
    {
        ObjectId id = userPrincipal.getId();
        User user = myUserDetailServices.getUserById(id).get();
        Photo photo = user.getPhoto();
        List<Role> allRoles = user.getRoles();
        List<Vulnerbilite> list = user.getVulnerbilites();
        model.addAttribute("vulns",list);
        model.addAttribute("author",user);
        model.addAttribute("roles",allRoles);
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }
        return "myprofile";
    }
    
    @GetMapping("/users.html/view/user/{username}")
    public String viewUser(@AuthenticationPrincipal UserPrincipal userPrincipal,@PathVariable String username, Model model){
        User user = myUserDetailServices.findUserByUsername(username);
        ObjectId id= userPrincipal.getId();
        User pricipale = myUserDetailServices.getUserById(id).get();
        model.addAttribute("principal",pricipale);
        Photo photo = pricipale.getPhoto();
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }
        model.addAttribute("authorResume", user);
        model.addAttribute("userRoles", roleService.getUserRoles(user));
        model.addAttribute("vulnerabilities", user.getVulnerbilites());
        return "resume";
    }

    @GetMapping("/resume.html/{title}")
    public String getExploit(@AuthenticationPrincipal UserPrincipal userPrincipal,@PathVariable String title,Model model)
    {
        Vulnerbilite vulnerbilite = vulnerabilityService.getVulnByTitle(title);
        ObjectId id= userPrincipal.getId();
        User pricipale = myUserDetailServices.getUserById(id).get();

        model.addAttribute("principal",pricipale);
        model.addAttribute("vulnerbilite",vulnerbilite);
        Photo photo = pricipale.getPhoto();
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }


        return "exploit";
    }

}
