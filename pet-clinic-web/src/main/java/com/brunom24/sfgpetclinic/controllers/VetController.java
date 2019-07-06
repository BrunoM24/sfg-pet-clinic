package com.brunom24.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VetController {

	@RequestMapping({"/vets", "/vets/index", "/vets/intex.html"})
	public String listVets(){

		return "vets/index";
	}

}
