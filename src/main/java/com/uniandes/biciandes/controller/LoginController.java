package com.uniandes.biciandes.controller;

import com.uniandes.biciandes.config.ProductoConfig;
import com.uniandes.biciandes.dto.UserDto;
import com.uniandes.biciandes.exception.PasswordsDontMatchException;
import com.uniandes.biciandes.model.User;
import com.uniandes.biciandes.service.UserService;
import com.uniandes.biciandes.util.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LoginController {

    @Value("${biciandes.version}")
    private String biciandesVersion;
    
    @Autowired
    ProductoConfig producto;

    private S3Upload s3Upload;

    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserService userService, BCryptPasswordEncoder passwordEncoder, S3Upload s3Upload) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.s3Upload = s3Upload;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error,
                        @RequestParam(value="logout", required = false) String logout,
                        Model model, Authentication authentication, RedirectAttributes flash) {

    	System.out.println("version: "+producto.getVersion());
    	
        if(Optional.ofNullable(authentication).isPresent()) {

            //Authentication object resolved by framework
            String userEmail = Optional.of(authentication)
                    .map(Authentication::getPrincipal)
                    .map(p -> (UserDetails) p)
                    .map(UserDetails::getUsername)
                    .orElseThrow(() -> new RuntimeException("There is no authentication"));

            System.out.println(userEmail);
            flash.addFlashAttribute("info", "Ya ha inciado sesión anteriormente con "+userEmail);
            return "redirect:/";
        }
        if(error != null) {
            model.addAttribute("error", "Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
            System.out.println("Login incorrecto");
        }
        if(logout != null) {
            model.addAttribute("success", "Ha cerrado sesión con éxito!");
            System.out.println("Bye bye");
            return "redirect:/login";
        }

        return "login";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {

        //Model to map data
        model.addAttribute("userDto", new UserDto());

        model.addAttribute("titulo", "Ingrese los datos de Registro del cliente");
        model.addAttribute("isRegister", "true");
        model.addAttribute("version", biciandesVersion);   //TODO: LPS
        model.addAttribute("btnLabel", "Registrar");

        return "signup";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserDto userDto, @RequestParam(value = "file", required = false) MultipartFile picture) {
        System.out.println(userDto);

        //TODO: Check if user already exists and validate password matching
        if(!userDto.getPassword().equals(userDto.getMatchingPassword())) {
            throw new PasswordsDontMatchException("Passwords don't match");
        }

        //Bcrypting password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User newUser = userDto.toUserEntity();

        //If master to s3 bucket picture
        if (picture!=null && !picture.isEmpty()) {
            Optional.ofNullable(s3Upload.uploadFile(picture)).ifPresent(newUser::setUrlPicture);
        }

        userService.saveUser(newUser);

        return "redirect:login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

}
