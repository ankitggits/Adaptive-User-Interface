/**
 * 
 */
package com.aui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aui.pojo.ResponseData;

/**
 * @author sinankit
 *
 */
@Controller
public class HomeController {
	
	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String test() {
		return "aui-api";
	}
	
	@RequestMapping(value = {"/user/{name}"}, method = RequestMethod.GET)
	public @ResponseBody ResponseData test(@PathVariable(value="name") String testName) {
		System.out.println("we got ur name: "+testName);
		ResponseData data = new ResponseData();
		data.setMessage("yeeah it worked, we got ur name: "+testName);
		return data;
	}
	
	@RequestMapping(value = {"/username/{name}"}, method = RequestMethod.GET)
	public ModelAndView testMVC(@PathVariable(value="name") String testName) {
		System.out.println("we got ur name: "+testName);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("aui-api");
		modelAndView.addObject("name", testName);
		return modelAndView;
	}
	
}
