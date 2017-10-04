package taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PagesTag extends TagSupport implements DynamicAttributes{
    private Map<String,Object> map = new HashMap<>();
    private  int activated;
    private int length;
    private static int countInOnePage = 30;
    @Override
    public int doStartTag() throws JspException {
        activated = (int)map.get("activated");
        length = (int)map.get("length");
        StringBuffer buffer = new StringBuffer();
        int count = length % countInOnePage == 0 ? length / countInOnePage : (length / countInOnePage) + 1;
        buffer.append("<form action=\"\">");
        for (int i = 1; i <= count; i++) {
            buffer.append("<input type=\"submit\" name=\"currentPage\" value=\"" + i + "\"");
            if(i == activated)
                buffer.append("id=\"currentPage\"");
            buffer.append("/>");
        }
        buffer.append("</form>");
        buffer.append("<br/>");
        try {
            pageContext.getOut().print(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public void setDynamicAttribute(String uri, String name, Object value) throws JspException {
        map.put(name,value);
    }
}
