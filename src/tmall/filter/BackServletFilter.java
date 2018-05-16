package tmall.filter;
 
import java.io.IOException;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.lang.StringUtils;
 
public class BackServletFilter implements Filter {
 
    public void destroy() {
         
    }
//*********************************************************
//**假定你的web application 名称为news,你在浏览器中输入请求路径： 
//**http://localhost:8080/news/main/list.jsp
//** 则执行下面向行代码后打印出如下结果：
//**  1、 System.out.println(request.getContextPath()); 
//**  打印结果：/news 
//**    2、System.out.println(request.getServletPath()); 
//**  打印结果：/main/list.jsp 
//**   3、 System.out.println(request.getRequestURI()); 
//**  打印结果：/news/main/list.jsp 
//**   4、 System.out.println(request.getRealPath("/"));  
//**  打印结果： F:\Tomcat 6.0\webapps\news\test  
//*********************************************************
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;//req参数的类型是ServletRequest，需要转换为HttpServletRequest类型方便调用某些方法
        HttpServletResponse response = (HttpServletResponse) res;
         
        String contextPath=request.getServletContext().getContextPath();//获取来路,参考上述备注。
        String uri = request.getRequestURI();//获取来路用户的ip地址
        uri =StringUtils.remove(uri, contextPath);//去出url中的contextpath
        if(uri.startsWith("/admin_")){     
            String servletPath = StringUtils.substringBetween(uri,"_", "_") + "Servlet";
            String method = StringUtils.substringAfterLast(uri,"_" );
            request.setAttribute("method", method);//在request中设置method.
            req.getRequestDispatcher("/" + servletPath).forward(request, response);//服务端跳转到“servletpath”.jsp,因为是服务端跳转，都属于同一次请求，所以可以在改.jsp通过request取出来。
            return;
        }
         
        chain.doFilter(request, response);//过滤器放行，表示继续运行下一个过滤器，或者最终访问的某个servlet,jsp,html等等
    }
 
    public void init(FilterConfig arg0) throws ServletException {
     
    }
}