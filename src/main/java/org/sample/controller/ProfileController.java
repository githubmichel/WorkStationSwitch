package org.sample.controller;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.sample.controller.exceptions.InvalidDateException;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.*;
import org.sample.controller.service.AdService;
import org.sample.controller.service.UserService;
import org.sample.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sun.misc.IOUtils;

@Controller
public class ProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    AdService adService;
    @Autowired
    UserService userService;
    
    
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(HttpServletRequest request) {
        ModelAndView model;
        if(request.isUserInRole("ROLE_PERSONA_USER") && userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getIsNew()) {
            return "redirect:/newProfile";
        }
        else if(request.isUserInRole("ROLE_PERSONA_USER")) {
            return "redirect:/editProfile";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public Object editProfile(HttpServletRequest request) {
    	if(!request.isUserInRole("ROLE_PERSONA_USER")) {
    		return "redirect:/";
    	} else if(userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getIsNew()) {
            return "redirect:/profile";
        }
        ModelAndView model = new ModelAndView("profile");
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUserByEmail(userMail);
        model.addObject("profileForm", userService.fillProfileForm(user));
        model.addObject("user", user);
        model.addObject("profile", user);
       
        return model;
    }

    @RequestMapping(value = "/editProfile/{profileId}", method = RequestMethod.GET)
    public Object editProfileId(HttpServletRequest request, @PathVariable Long profileId) {
        User user = userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        User profile = userService.getUser(profileId);
        if(!request.isUserInRole("ROLE_PERSONA_USER") || !user.getIsAdmin()) {
            return "redirect:/";
        } else if(user.getIsNew()) {
            return "redirect:/profile";
        }
        ModelAndView model = new ModelAndView("profile");
        model.addObject("profileForm", userService.fillProfileForm(profile));
        model.addObject("user", user);
        model.addObject("profile", profile);
        model.addObject("apartments",adService.getApartmentsByUser(user.getEmail()));
        model.addObject("shApartments",adService.getShApartmentsByUser(user.getEmail()));
        return model;
    }

    @RequestMapping(value = "/newProfile", method = RequestMethod.GET)
    public Object newProfile(HttpServletRequest request) {
        if(!request.isUserInRole("ROLE_PERSONA_USER")) {
            return "redirect:/";
        }
        ModelAndView model = new ModelAndView();
        SecurityContext ctx = SecurityContextHolder.getContext();
        model.addObject("profileForm", new ProfileForm());
        model.addObject("user",userService.loadUserByEmail(ctx.getAuthentication().getName()));
        return model;
    }

    @RequestMapping(value = "/saveProfile", method = RequestMethod.POST)
    public String saveProfile(HttpServletRequest request, @Valid ProfileForm profileForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if(!request.isUserInRole("ROLE_PERSONA_USER")) {
            return "redirect:/";
        } else if(userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getIsNew()) {
            return "redirect:/profile";
        }
        SecurityContext ctx = SecurityContextHolder.getContext();
        profileForm.setEmail(ctx.getAuthentication().getName());
        System.out.println(profileForm.getFirstName());
        userService.saveFrom(profileForm);
        redirectAttributes.addFlashAttribute("Profile saved.");
        return "redirect:/profile";
    }

    @RequestMapping(value = "/saveNewProfile", method = RequestMethod.POST)
    public String saveNewProfile(HttpServletRequest request, @Valid NewProfileForm newProfileForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if(!request.isUserInRole("ROLE_PERSONA_USER")) {
            return "redirect:/";
        }
        SecurityContext ctx = SecurityContextHolder.getContext();
        newProfileForm.setEmail(ctx.getAuthentication().getName());
        userService.saveFrom(newProfileForm);
        redirectAttributes.addFlashAttribute("Profile created.");
        return "redirect:/profile";
    }
    
    @RequestMapping(value = "/security-error", method = RequestMethod.GET)
    public String securityError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("page_error", "You do not have permission to do that!");
        return "redirect:/";
    }
    
    @RequestMapping(value="/showProfile/{personId}", method = RequestMethod.GET)
    public Object showProfile(HttpServletRequest request, @PathVariable("personId") Long pId){
    	if(!request.isUserInRole("ROLE_PERSONA_USER")) {
    		return "redirect:/";
    	} else if(userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getIsNew()) {
            return "redirect:/profile";
        }
    	User user = userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	ModelAndView model = new ModelAndView("showProfile");
    	model.addObject("profile", userService.getPerson(pId));
    	model.addObject("user", user);
    	return model;
    }
    
    
    @RequestMapping(value = "/profileImage", method = RequestMethod.POST)
    public Object profileImgUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
    	User user = userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(!request.isUserInRole("ROLE_PERSONA_USER")) {
            return "redirect:/";
        } else if(user.getIsNew()) {
            return "redirect:/profile";
        }
        String message =  saveImage(file, Long.toString(user.getPerson().getId()), "profileImg");
        if(message.equals("You successfully uploaded the image")){
        	user = userService.imageSaved(user);
        }
    	ModelAndView model = new ModelAndView("profile");
        model.addObject("profileForm", userService.fillProfileForm(user));
        model.addObject("user", user);
        model.addObject("profile", user);
        model.addObject("message", message);
        return model;
    }
    
    @RequestMapping(value = "/removeProfileImage/{personId}", method = RequestMethod.GET)
    Object upload(HttpServletRequest request, @PathVariable Long personId){
    	User user = userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(!request.isUserInRole("ROLE_PERSONA_USER")) {
            return "redirect:/";
        } else if(user.getIsNew()) {
            return "redirect:/profile";
        }
    	
    	userService.removeImage(personId);
    	
    	return "redirect:/editProfile";
    }

	private String saveImage(MultipartFile file, String name, String directory) {
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator +".."+ File.separator+"src" + File.separator +"main" + File.separator + "webapp" + File.separator + directory);
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                if(extension.equals("jpg") || extension.equals("jpeg") ){
                	File serverFile = new File(dir.getAbsolutePath() + File.separator + name + ".jpg" );
                	BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                	stream.write(bytes);
                	stream.close();
 
                	LOGGER.info("Server File Location=" + serverFile.getAbsolutePath());
 
                	return "You successfully uploaded the image" ;
                }
                else{
                	return "Image must be jpg format";
                }
                
            } catch (Exception e) {
                return "You failed to upload the image => " + e.getMessage();
            }
        } else {
            return "You failed to upload the image because the file was empty.";
        }
	}
    
   

}


