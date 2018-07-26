package com.lijin.mylab.utils;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class WebUtil {
	

//	public static void forwardToFailPage(HttpServletRequest req,HttpServletResponse resp,String oId, String errCode,String errMsg) throws ServletException, IOException{
//		req.setAttribute(VIEW.oId, oId);
//		req.setAttribute(MSG.resp_code, errCode);
//		req.setAttribute(MSG.respsg, errMsg);//TODO
//		log.warn("Forward to fail page["+oId+"]["+errCode+"]["+errCode+"]");
//		req.getRequestDispatcher("/page/pay_fail.jsp").forward(req, resp);
//	}
//	
//	public static void forwardToErrorPage(HttpServletRequest req,HttpServletResponse resp, String errCode,String errMsg) throws ServletException, IOException{
//		req.setAttribute(MSG.resp_code, errCode);
//		req.setAttribute(MSG.respMsg, errMsg);//TODO
//		log.warn("Forward to error page["+errCode+"]["+errCode+"]");
//		req.getRequestDispatcher("/page/error.jsp").forward(req, resp);
//	}
	
	public static void forwardToPage(HttpServletRequest req,HttpServletResponse resp, String pageUrl) throws ServletException, IOException {
		req.getRequestDispatcher(pageUrl).forward(req, resp);
	}

	
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cks = request.getCookies();
        if (cks != null && cks.length > 0) {
            for (Cookie c : cks) {
                if (c.getName().equalsIgnoreCase(key)) {
                    return c.getValue();
                }
            }
        }
        return "";
    }
    
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String key, String value){
    	addCookie(request,response,key,value);	
    }
    
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String key, String value, String path, int age){
		Cookie cookie = new Cookie(key, value);
		cookie.setSecure(request.isSecure());
		cookie.setMaxAge(age);
		if (!StringUtil.isEmpty(path)) {
			cookie.setPath(path);
		}
		response.addCookie(cookie);
    }
	

	
	public static String toAotoSubmitForm(String url, Map<String, String> resp,
			String charset) {
		StringBuilder sf = new StringBuilder();
		sf.append("<html>\n");
		sf.append("<head>\n");
		sf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
				+ charset + "\">\n");
		sf.append("<title>Please Wait...</title>\n");
		sf.append("</head>\n");
		sf.append("<body onload=\"OnLoadSubmit();\">\n");
		sf.append("<form id=\"gatewayform\" action=\"" + url
				+ "\" method=\"post\">\n");
		if(resp!=null){
			for (String key : resp.keySet()) {
				sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key
						+ "\" value=\"" + resp.get(key) + "\"/>\n");
			}	
		}

		sf.append("</form>\n");
		sf.append("<script type=\"text/javascript\">\n");
		sf.append("<!--\n");
		sf.append("function OnLoadSubmit()\n");
		sf.append("{\n");
		sf.append("document.getElementById(\"gatewayform\").submit();\n");
		sf.append("}\n");
		sf.append("//-->\n");
		sf.append("</script>\n");
		sf.append("</body>\n");
		sf.append("</html>");
		return sf.toString();
	}
	
	public static String getIP(HttpServletRequest req) {
		String remoteAddr = req.getHeader("Cdn-Src-Ip");
		if (remoteAddr == null || remoteAddr.length() == 0
				|| "unknown".equalsIgnoreCase(remoteAddr)) {
			remoteAddr = req.getHeader("x-forwarded-for");
		}
		if (remoteAddr == null || remoteAddr.length() == 0
				|| "unknown".equalsIgnoreCase(remoteAddr)) {
			remoteAddr = req.getHeader("Proxy-Client-IP");
		}
		if (remoteAddr == null || remoteAddr.length() == 0
				|| "unknown".equalsIgnoreCase(remoteAddr)) {
			remoteAddr = req.getHeader("WL-Proxy-Client-IP");

		}
		if (remoteAddr == null || remoteAddr.length() == 0
				|| "unknown".equalsIgnoreCase(remoteAddr)) {
			remoteAddr = req.getRemoteAddr();
		}
		if (null != remoteAddr && !"".equals(remoteAddr)) {
			if (remoteAddr.indexOf(",") != -1) {
				remoteAddr = remoteAddr.split(",", -1)[0];
			}
		}

		return remoteAddr;
	}



}
