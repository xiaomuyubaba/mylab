package com.lijin.mylab.controller;

import com.lijin.mylab.utils.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.Date;

@Controller
public class MainController extends BaseController {

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("today", new Date());
		return "main";
	}

}
