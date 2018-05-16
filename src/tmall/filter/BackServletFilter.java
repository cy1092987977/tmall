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
//**�ٶ����web application ����Ϊnews,�������������������·���� 
//**http://localhost:8080/news/main/list.jsp
//** ��ִ���������д�����ӡ�����½����
//**  1�� System.out.println(request.getContextPath()); 
//**  ��ӡ�����/news 
//**    2��System.out.println(request.getServletPath()); 
//**  ��ӡ�����/main/list.jsp 
//**   3�� System.out.println(request.getRequestURI()); 
//**  ��ӡ�����/news/main/list.jsp 
//**   4�� System.out.println(request.getRealPath("/"));  
//**  ��ӡ����� F:\Tomcat 6.0\webapps\news\test  
//*********************************************************
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;//req������������ServletRequest����Ҫת��ΪHttpServletRequest���ͷ������ĳЩ����
        HttpServletResponse response = (HttpServletResponse) res;
         
        String contextPath=request.getServletContext().getContextPath();//��ȡ��·,�ο�������ע��
        String uri = request.getRequestURI();//��ȡ��·�û���ip��ַ
        uri =StringUtils.remove(uri, contextPath);//ȥ��url�е�contextpath
        if(uri.startsWith("/admin_")){     
            String servletPath = StringUtils.substringBetween(uri,"_", "_") + "Servlet";
            String method = StringUtils.substringAfterLast(uri,"_" );
            request.setAttribute("method", method);//��request������method.
            req.getRequestDispatcher("/" + servletPath).forward(request, response);//�������ת����servletpath��.jsp,��Ϊ�Ƿ������ת��������ͬһ���������Կ����ڸ�.jspͨ��requestȡ������
            return;
        }
         
        chain.doFilter(request, response);//���������У���ʾ����������һ�����������������շ��ʵ�ĳ��servlet,jsp,html�ȵ�
    }
 
    public void init(FilterConfig arg0) throws ServletException {
     
    }
}