package kr.heartbeat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() { return "index"; }  
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() { return "heartbeat/login"; }  
	
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() { return "heartbeat/join"; }  

	/*@RequestMapping(value="/chart", method = RequestMethod.GET)
	public String chart() { return "heartbeat/chart"; } */
	
	/*@RequestMapping(value="/playlist", method = RequestMethod.GET)
	public String playlist() { return "heartbeat/playlist"; } */
	
//	@RequestMapping(value="/community", method = RequestMethod.GET)
//	public String community() { return "community/community"; } 
	
	@RequestMapping(value="/purchase", method = RequestMethod.GET)
	public String membership() { return "heartbeat/purchase"; } 
	
//	 @RequestMapping(value="/mypage", method = RequestMethod.GET) 
//	 public String mypage() { return "heartbeat/mypage"; }
}
