package com.baokaicong.sm.aspect;


import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Log;
import com.baokaicong.sm.bean.entity.User;
import com.baokaicong.sm.exception.SMException;
import com.baokaicong.sm.service.LogService;
import com.baokaicong.sm.service.RoleService;
import com.baokaicong.sm.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;


@Aspect
@Component
public class ControllerAuthAspect {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LogService logService;

    /**
     * 只对controller单包做拦截
     * 不扫描子包
     */
    @Pointcut("execution(public * com.baokaicong.sm.controller.*.*(..))")
    public void aspect() {

    }

    @Before("aspect()")
    public void doBefore(JoinPoint joinPoint){
        try {
            Method method=((MethodSignature)joinPoint.getSignature()).getMethod();
            Authority authority=method.getAnnotation(Authority.class);
            if(authority!=null){
                if(authority.open()){
                    System.out.println("权限开放，放行");
                    return;
                }
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                Object uid=request.getAttribute("uid");
                if(uid==null || uid.toString()==""){
                    throw new SMException("用户令牌不存在","401");
                }
                Log log=new Log()
                        .setLevel(1)
                        .setTime(System.currentTimeMillis()+"")
                        .setUid(uid.toString());
                String str=null;
                try{
                    str=buildArgsString(joinPoint.getArgs());
                }catch (Exception e){
                    str="";
                }
                String info="请求操作："+
                        joinPoint.getSignature().getDeclaringType().getSimpleName()+
                        "->"+
                        method.getName()+
                        ", 参数列表["+ str +"]";
                if(checkAuth(authority.value(),uid.toString())){
                    System.out.println("权限验证通过");
                    log.setInfo("[info]"+info);
                    logService.insert(log);
                }else{
                    log.setInfo("[denied]"+info);
                    logService.insert(log);
                    throw new SMException("用户不具备目标请求权限","403");
                }
            }else{
                throw new SMException("用户不具备目标请求权限","403");
            }
        } catch (Exception e) {
            throw new SMException("用户不具备目标请求权限","403");
        }
    }

    @AfterReturning(returning = "ret", pointcut = "aspect()")
    public void doAfterReturning(Object ret) {

    }

    private boolean checkAuth(String auid,String uid){
        User user=userService.queryByUid(uid);
        if(user==null){
            throw new SMException("用户令牌不存在","401");
        }
        List<Auth> list=roleService.listAuthByRid(user.getRid());
        for(Auth auth:list){
            if(auth.getAuid().equals(auid)){
                return true;
            }
        }
        return false;
    }

    private String buildArgsString(Object[] args){
        if(args==null){
            return "";
        }
        StringBuilder sb=new StringBuilder();
        for(Object obj:args){
            sb.append(obj.toString()).append(",");
        }
        return sb.toString();
    }
}
