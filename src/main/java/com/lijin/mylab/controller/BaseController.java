package com.lijin.mylab.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.lijin.mylab.bo.CommonBO;

public abstract class BaseController {

	@Autowired
	protected CommonBO commonBO;

}
