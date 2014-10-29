package org.sample.controller;

import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.AdForm;
import org.sample.controller.pojos.SignupForm;
import org.sample.controller.pojos.TeamCreationForm;
import org.sample.controller.service.SampleService;
import org.sample.model.Ad;
import org.sample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {

    @Autowired
    SampleService sampleService;
   
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView main() {
    	ModelAndView model = new ModelAndView("main");
        return model;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
    	ModelAndView model = new ModelAndView("index");

    	model.addObject("signupForm", new SignupForm());
        model.addObject("teams", sampleService.getAllTeams());
        return model;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search() {
    	ModelAndView model = new ModelAndView("search");

    	model.addObject("searchForm", new SignupForm());
        model.addObject("teams", sampleService.getAllTeams());
        return model;
    }
    
    @RequestMapping(value="/searchresults/{adId}",	method=RequestMethod.GET)
    public	ModelAndView displayAd(@PathVariable	String	adId)	{
    	ModelAndView model = new ModelAndView("showAd");
    	Long lAdId = Long.parseLong(adId);
    	Ad ad = sampleService.getAd(lAdId);
    	model.addObject("ad", ad);
    	return model;
    }
    
    @RequestMapping(value = "/new-ad", method = RequestMethod.GET)
    public ModelAndView newAd(){
    	ModelAndView model = new ModelAndView("newAd");
    	
    	model.addObject("adForm", new AdForm());
    	
    	return model;
    }
    
    @RequestMapping(value="/makeAd", method = RequestMethod.POST)
    public ModelAndView makeAd(AdForm adForm, BindingResult result){
    	ModelAndView model;    	
    	if (!result.hasErrors()) {
            try {
            	sampleService.saveFrom(adForm);
            	model = new ModelAndView("viewAd");
                model.addObject("message","Ad added!");
                model.addObject("adForm", adForm);
            } catch (InvalidUserException e) {
            	model = new ModelAndView("newAd");
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView("index");
        }   	
    	return model;
    }
    
    @RequestMapping(value="/editAd", method = RequestMethod.POST)
    public ModelAndView editAd(AdForm adForm, BindingResult result){
    	ModelAndView model;    	
    	if (!result.hasErrors()) {
            model = new ModelAndView("newAd");
            Ad oldAd = sampleService.getAd(adForm.getId());
            adForm.setDescription(oldAd.getDescription());
            model.addObject("oldAd", oldAd);
            model.addObject("adForm", adForm);
        } 
    	else {
        	model = new ModelAndView("index");
        }   	
    	return model;
    }
    
    
    
    
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid SignupForm signupForm, BindingResult result, RedirectAttributes redirectAttributes) {
    	ModelAndView model;    	
    	if (!result.hasErrors()) {
            try {
            	sampleService.saveFrom(signupForm);
            	model = new ModelAndView("show");
                model.addObject("message","Sign Up Complete!");
            } catch (InvalidUserException e) {
            	model = new ModelAndView("index");
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView("index");
        }   	
    	return model;
    }

    @RequestMapping(value = "/new-team", method = RequestMethod.GET)
    public ModelAndView newTeam() {
        ModelAndView model = new ModelAndView("newTeam");
        model.addObject("teamCreationForm", new TeamCreationForm());
        return model;
    }

    @RequestMapping(value = "/create-team", method = RequestMethod.POST)
    public ModelAndView createTeam(@Valid TeamCreationForm teamCreationForm, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView model;
        if(!result.hasErrors()) {
            try {
                sampleService.saveTeamFrom(teamCreationForm);
                model = new ModelAndView("show");
                model.addObject("message","Team creation complete!");
            } catch (Exception e) {
                model = new ModelAndView("newTeam");
                model.addObject("page_error", e.getMessage());
            }
        }
        else {
            model = new ModelAndView("newTeam");
        }
        return model;
    }

    @RequestMapping(value = "/profile.jsp", method = RequestMethod.GET)
    public ModelAndView profile(String userId) {
        ModelAndView model = new ModelAndView("profile");
        Long lUserId = Long.parseLong(userId);
        User user = sampleService.getUser(lUserId);
        model.addObject("user",user);
        return model;
    }
    
    @RequestMapping(value = "/security-error", method = RequestMethod.GET)
    public String securityError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("page_error", "You do not have permission to do that!");
        return "redirect:/";
    }

}


