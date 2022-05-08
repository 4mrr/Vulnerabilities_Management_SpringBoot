package vuln.zsmart.ma.vulnnosql.Securityy.Controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vuln.zsmart.ma.vulnnosql.Beans.Photo;
import vuln.zsmart.ma.vulnnosql.Beans.User;
import vuln.zsmart.ma.vulnnosql.Beans.UserPrincipal;
import vuln.zsmart.ma.vulnnosql.Securityy.Models.Role;
import vuln.zsmart.ma.vulnnosql.Securityy.Services.RoleService;
import vuln.zsmart.ma.vulnnosql.Services.MyUserDetailServices;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    MyUserDetailServices myUserDetailServices;


    @GetMapping("/role.html")
    public String findAll(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model)
    {
        ObjectId id = userPrincipal.getId();
        User user = myUserDetailServices.getUserById(id).get();
        Photo photo = user.getPhoto();
        List<Role> listRole = roleService.getAll();
        model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        model.addAttribute("roles", listRole);

        return "role";
    }
    @RequestMapping("/role.html/findById")
    @ResponseBody
    public Optional<Role> findById(ObjectId id)
    {
        return  roleService.getById(id);
    }

    @RequestMapping(value="/role.html/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(ObjectId id) {
        roleService.deleteById(id);
        return "redirect:/role.html";
    }

    @PostMapping("/role.html/add-role")
    public String AddNew(Role role)
    {
        roleService.save(role);
        return  "redirect:/role.html";
    }

    @RequestMapping(value="/role.html/delete", method = {RequestMethod.PUT, RequestMethod.GET})
    public String update(Role role)
    {
        roleService.save(role);
        return  "redirect:/role.html";
    }

    @GetMapping("/users.html/security/user/Edit/{id}")
    public String editUser(@PathVariable ObjectId id, Model model){
        User user = myUserDetailServices.getUserById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getUserRoles(user));
        model.addAttribute("userNotRoles", roleService.getUserNotRoles(user.getId()));
        return "userEdit";
    }

    @RequestMapping("/users.html/security/role/assign/{userId}/{roleId}")
    public String assignRole(@PathVariable ObjectId userId, @PathVariable ObjectId roleId){
        User user = myUserDetailServices.getUserById(userId).get();
        roleService.assignUserRole(userId, roleId);
        return "redirect:/users.html/security/user/Edit/"+userId;
    }

    @RequestMapping("/users.html/security/role/unassign/{userId}/{roleId}")
    public String unassignRole(@PathVariable ObjectId userId, @PathVariable ObjectId roleId){
        User user =myUserDetailServices.getUserById(userId).get();
        roleService.unassignUserRole(userId, roleId);
        return "redirect:/users.html/security/user/Edit/"+userId;
    }
}
