package vuln.zsmart.ma.vulnnosql.Controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vuln.zsmart.ma.vulnnosql.Beans.*;
import vuln.zsmart.ma.vulnnosql.Services.MyUserDetailServices;
import vuln.zsmart.ma.vulnnosql.Services.PhotoService;
import vuln.zsmart.ma.vulnnosql.Services.RoleService;
import vuln.zsmart.ma.vulnnosql.Services.VulnerabilityService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class ApplicationController {

    @Autowired
    VulnerabilityService vulnerabilityService;
    @Autowired
    MyUserDetailServices myUserDetailServices;
    @Autowired
    PhotoService photoService;
    @Autowired
    RoleService roleService;

    @GetMapping("/index.html")
    public String getHome(@AuthenticationPrincipal UserPrincipal userPrincipal,Model model)
    {
        int platform_A=0;
        int platform_J=0;
        int platform_P=0;
        int platform_H=0;
        int platform_L=0;
        int platform_W=0;
        int platform_I=0;

        List<Vulnerbilite> list = vulnerabilityService.getAllVuln();
        List<String> platforms = new ArrayList<>();
        platforms.add(String.valueOf(Platform.Hardware));
        platforms.add(String.valueOf(Platform.Android));
        platforms.add(String.valueOf(Platform.Linux));
        platforms.add(String.valueOf(Platform.Windows));
        platforms.add(String.valueOf(Platform.IOS));
        platforms.add(String.valueOf(Platform.JAVA));
        platforms.add(String.valueOf(Platform.PHP));

        int numberV = vulnerabilityService.getAllVuln().size();
        int numberP = 7;
        int numberA = myUserDetailServices.getAllUsers().size();

        for (int i = 0 ; i< numberV ; i++ )
        {
            if (list.get(i).getPlatform() == Platform.Android){ platform_A +=1;}
            else if (list.get(i).getPlatform() == Platform.IOS){ platform_I +=1;}
            else if (list.get(i).getPlatform() == Platform.PHP){ platform_P +=1;}
            else if (list.get(i).getPlatform() == Platform.Linux){ platform_L +=1;}
            else if (list.get(i).getPlatform() == Platform.Windows){ platform_W +=1;}
            else if (list.get(i).getPlatform() == Platform.Hardware){ platform_H +=1;}
            else { platform_J +=1;}
        }
        model.addAttribute("no_V",numberV);
        model.addAttribute("no_A",numberA);
        model.addAttribute("no_P",numberP);
        model.addAttribute("platforms",platforms);
        ObjectId id_u = userPrincipal.getId();
        User user = myUserDetailServices.getUserById(id_u).get();
        Photo photo = user.getPhoto();
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }

        model.addAttribute("platform_java",platform_J);
        model.addAttribute("platform_windows",platform_W);
        model.addAttribute("platform_linux",platform_L);
        model.addAttribute("platform_hardware",platform_H);
        model.addAttribute("platform_php",platform_P);
        model.addAttribute("platform_ios",platform_I);
        model.addAttribute("platform_android",platform_A);

        return "index";
    }

    @GetMapping("/login.html")
    public String getLogin()
    {
        return "login";
    }

    @GetMapping("/logout")
    public String getLogout()
    {
        return "logout";
    }

    @GetMapping("/register.html")
    public String getRegister()
    {
      return "register";
    }

    @PostMapping("/register.html/add-user")
    public String postRegister(User user , @RequestParam("image") MultipartFile img,Model model) throws IOException {
        ObjectId id_p = myUserDetailServices.addPhotoSansTitle(img);
        Photo photo = myUserDetailServices.getPhoto(id_p);
        Role role = roleService.getRoleByDescription("USER");
        user.setPhoto(photo);
        List<Role> list = user.getRoles();
        list = new ArrayList<>();
        list.add(role);
        user.setRoles(list);
        myUserDetailServices.save(user);
        model.addAttribute("loginMessage","You Successfully registered! You can now Login");
        return "redirect:/login.html";
    }
    @GetMapping("/tables.html")
    public String tables(@AuthenticationPrincipal UserPrincipal userPrincipal ,Model model)
    {
        List<Vulnerbilite> list = vulnerabilityService.getAllVuln();
        model.addAttribute("vulns",list);
        model.addAttribute("total",list.size());
        ObjectId id_u = userPrincipal.getId();
        User user = myUserDetailServices.getUserById(id_u).get();
        Photo photo = user.getPhoto();
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }

        model.addAttribute("user", user);
        return "tables";
    }

   @GetMapping("/AddVuln.html")
    public String AddVulnerability(@AuthenticationPrincipal UserPrincipal userPrincipal,Model model)
    {
        ObjectId id_u = userPrincipal.getId();
        User user = myUserDetailServices.getUserById(id_u).get();
        Photo photo = user.getPhoto();
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }

        return "AddVuln";
    }

    @PostMapping("/AddVuln.html/Add-Vuln")
    public String addNewVulnerability(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model,Vulnerbilite vulnerbilite) {
        ObjectId id = userPrincipal.getId();
        User author = myUserDetailServices.getUserById(id).get();
        vulnerbilite.setAuthor(author);
        author.getVulnerbilites().add(vulnerbilite);
        vulnerabilityService.save(vulnerbilite);
        myUserDetailServices.save(author);
        model.addAttribute("user",author);
        return "redirect:/AddVuln.html";
    }
    @GetMapping("/404.html")
    public String page404()
    {
        return "404";
    }

    @RequestMapping("/tables.html/findById")
    @ResponseBody
    public Optional<Vulnerbilite> findById(ObjectId id)
    {
        return vulnerabilityService.findVulnById(id);
    }

    @RequestMapping("/tables.html/findByCVE")
    @ResponseBody
    public Vulnerbilite findById(String title)
    {
        return vulnerabilityService.getVulnByTitle(title);
    }

    @RequestMapping(value="/tables.html/update", method = {RequestMethod.PUT, RequestMethod.GET})
    public String update(Vulnerbilite vulnerbilite) {
        vulnerabilityService.save(vulnerbilite);
        return "redirect:/tables.html";
    }
    @RequestMapping(value="/tables.html/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(String title) {
        Vulnerbilite vulnerbilite = vulnerabilityService.getVulnByTitle(title);
        vulnerabilityService.deleteVulnById(vulnerbilite.getId());
        return "redirect:/tables.html";
    }



    @GetMapping("/platforms.html")
    public String getPLatforms(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model)
    {
        ObjectId id_u = userPrincipal.getId();
        User user = myUserDetailServices.getUserById(id_u).get();
        Photo photo = user.getPhoto();
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }
        model.addAttribute("user", user);
        return "platforms";
    }

    @GetMapping("/accessDenied.html")
    public String getAccessDenied(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model)
    {
        ObjectId id_u = userPrincipal.getId();
        User user = myUserDetailServices.getUserById(id_u).get();
        Photo photo = user.getPhoto();
        if(photo != null)
        {
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }
        model.addAttribute("user", user);

        return "accessDenied";
    }
}
