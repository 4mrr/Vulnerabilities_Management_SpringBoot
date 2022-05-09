package vuln.zsmart.ma.vulnnosql.Controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vuln.zsmart.ma.vulnnosql.Beans.Photo;
import vuln.zsmart.ma.vulnnosql.Beans.User;
import vuln.zsmart.ma.vulnnosql.Beans.UserPrincipal;
import vuln.zsmart.ma.vulnnosql.Beans.Role;
import vuln.zsmart.ma.vulnnosql.Services.RoleService;
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

    @RequestMapping("/role.html/findDesc")
    @ResponseBody
    public Role findByDesc(String description)
    {
        return roleService.getRoleByDescription(description);
    }

    @RequestMapping(value="/role.html/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(String description) {
        Role role =  roleService.getRoleByDescription(description);
        roleService.deleteById(role.getId());
        return "redirect:/role.html";
    }

    @PostMapping("/role.html/add-role")
    public String AddNew(Role role)
    {
        roleService.save(role);
        return  "redirect:/role.html";
    }

    @RequestMapping(value="/role.html/update", method = {RequestMethod.PUT, RequestMethod.GET})
    public String update(Role role)
    {
        roleService.save(role);
        return  "redirect:/role.html";
    }

    @GetMapping("/users.html/security/user/Edit/{username}")
    public String editUser(@PathVariable String username, Model model){
        User user = myUserDetailServices.findUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getUserRoles(user));
        model.addAttribute("userNotRoles", roleService.getUserNotRoles(user));
        return "userEdit";
    }

    @RequestMapping("/users.html/security/role/assign/{username}/{roleId}")
    public String assignRole(@PathVariable String username, @PathVariable ObjectId roleId){
        User user = myUserDetailServices.findUserByUsername(username);
        roleService.assignUserRole(user.getId(), roleId);
        return "redirect:/users.html/security/user/Edit/"+username;
    }

    @RequestMapping("/users.html/security/role/unassign/{username}/{roleId}")
    public String unassignRole(@PathVariable String username, @PathVariable ObjectId roleId){
        User user =myUserDetailServices.findUserByUsername(username);
        roleService.unassignUserRole(user.getId(), roleId);
        return "redirect:/users.html/security/user/Edit/"+username;
    }
}