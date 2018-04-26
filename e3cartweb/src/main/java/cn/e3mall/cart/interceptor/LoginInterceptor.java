package cn.e3mall.cart.interceptor;

import cn.e3mall.common.util.CookieUtils;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Darwin
 * @date 2018/4/24
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // true, allow; false, intercept
        // 1. Get token from cookie.
        String token = CookieUtils.getCookieValue(request, "token");
        // 2. If there is, Not logged in, Allow
        if (StringUtils.isBlank(token)) {
            return true;
        }
        // 3. If get a token, This requires calling sso's service and fetch user information
        //    base on the token.
        E3Result result = tokenService.getUserByToken(token);
        // 4. Failed to get user info, login expired and direct release.
        if (result.getStatus() != 200) {
            return true;
        }
        // 5. If get a user-info, Logged in,
        TbUser user = (TbUser) result.getData();
        request.setAttribute("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // After handler is executed, Before returning to ModelAndView
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //Completion of Processing. After returning to ModelAndView

    }
}
