<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ page import="com.util.*" %>
<%@ page import="com.product.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
//get username in session
String username = (String)session.getAttribute("username");
//get all parameters in servlet ProductionAction(ResultSet in query)
List<Map<String,Object>> list = (List<Map<String,Object>>)request.getAttribute("listProduct");
DividePage dividePage = (Dividepage)request.getAttribute("dividePage");
String productName = (String)request.getAttribute("productName");
if (list == null) {
	ProductService service = new ProductDao();
	int totalRecord = service.getItemCoun("");
	dividePage = new DividePage(5,totalRecord,1);
	int start = dividePage.fromIndex();
	int end = dividePage.toIndex();
	list = service.listProduct("", start, end);
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div>
    <table width="60%" align="center">
        <tr>
            <td align="left"><font size="2">Welcome, <%=username%><br /><a href="javacript:logout();">Log out</a> </font> </td>
        </tr>
        <tr>
            <td align="center">
                <form name="form2" action="" method="post">
                    <table>
                        <tr>
                            <td colspan="2">Search the product:</td>
                        </tr>
                        <tr>
                            <td>Produce name:</td>
                            <td><input type="text" name="proname" value="<%=productName!=null?productName:""%>"/></td>
                        </tr>
                        <tr>
                            <td onclose="2" align="center">
                                <button type="button" onclick="searchProduct();">Search</button>
                                <button type="button" onclick="javasrcipt:location.href='<%=path%>'/addProduct.jsp">Add</button>
                            </td>
                        </tr>
                    </table>
                </form>
            </td>
        </tr>
        <tr>
            <td height=50></td>
        </tr>
        <tr>
            <td>Results:</td>
        </tr>
        <tr>
            <td>
                <form name="form1" action="" method="post">
                    <table border="1" width="100%">
                        <tr align="center">
                            <td width="10%"><input type="checkbox" name="checkall" onclick="selectAll(this.checked)"></td>
                            <td width="30%">Product name</td>
                            <td width="30%">Product address</td>
                            <td width="30%">Product price</td>
                        </tr>
                        <%
                            if (list != null && list.isEmpty()) {
                                for (Map<String, Object> map : list) {
                        %>
                        <tr align="center">
                            <td width="10%"><input type="checkbox" name="ids" value="<%=map.get("proid")%>"></td>
                            <td width="30%"><%=map.get("proname")%></td>
                            <td width="30%"><%=map.get("proaddress")%></td>
                            <td width="30%"><%=map.get("proprice")%></td>
                        </tr>
                        <%
                                }
                            }
                            else {
                        %>
                        <tr align="center">
                            <td width=10%><input type="checkbox" name="" /></td>
                            <td width=30%></td>
                            <td width=30%></td>
                            <td width=30%></td>
                        </tr>
                        <%
                        }
                        %>

                    </table>
                </form>
            </td>
        </tr>
        <tr>
            <td>
                <button type="button" onclick="del();">Delete</button>
                <button type="button" onclick="view();">View details</button>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="colspan">
                In total <%=dividePage.getPageCount()%> pages
                <a href="javascript:first();">head</a>
                <a href="javascript:forward();">pre</a>
                <a href="javascript:next();">next</a>
                <a href="javascript:end();">last</a>
                Jump to <select name="select" onchange="changePage(this.value)">
                <%
                int pageCount = dividePage.getPageCount();
                if (pageCount > 0) {
                    for (int i = 1; i <= pageCount; i++) {
                %>
                <option value="<%=i%>" <%= (i==dividePage.getCurrentPage()?"selected":"")%>> <%=i%></option>
                <%
                
                    }
                }
                else {
                	%>
                	<option value="1">1</option>
                	<%
                }
                %>
            </select>
            </td>
        </tr>
    </table>
</div>
<script>
	function searchProduct() {
		var th = document.form2;
		th.action = "<%=path%>/ProductAction?action_flag=search";
		th.submit();
	}
	
    function first() {
    	window.location.href = "<%=path%>/ProductAction?action_flag=search&pageNum=1";
    }
    
    function forward() {
    	window.location.href = "<%=path%>/ProductAction?action_flag=search&pageNum=<%=dividePage.getCurrentPage()-1%>";	
    }
    
    function next() {
    	window.location.href = "<%=path%>/ProductAction?action_flag=search&pageNum=<%=dividePage.getCurrentPage()+1%>";	
    }
    
    function end() {
    	window.location.href = "<%=path%>/ProductAction?action_flag=search&pageNum=<%=dividePage.getPageCount() %>";
    }
    
    
    function changePage(currentPage) {
    	window.location.href = "<%=path%>/ProductAction?action_flag=search&pageNum="+currentPage;
    }
    
    function selectAll(flag) {
        vat ids = document.getElementsByNames("ids");
        for (var i = 0; i < ids.length; i++) {
        	ids[i].checked = flag;
        }
    }
    
	function getSelectedCount(){
		
		var ids = document.getElementsByName("ids");
		var count = 0;
		for(var i = 0 ; i < ids.length ; i++)
		{
						
			ids[i].checked==true?count++:0;					
		}
		return count;
	
	}
	
    function del() {
		if(getSelectedCount()==0){
			
			alert("至少选择一个删除项！");
			return;
		
		}
		
		var th = document.form1;
		th.action="<%=path%>/servlet/ProductAction?action_flag=del";
		th.submit(); 
    }
    
    function view() {
		if(getSelectedCount()<1){
			
			alert("至少选择一个查看项！");
			return;
		
		}else if(getSelectedCount()>1){
			alert("只能选择一个查看项！");
			return;
		}
		
		var th = document.form1;
		th.action="<%=path%>/servlet/ProductAction?action_flag=view&proid="+getSelectedValue();
		th.submit();		
    }
    
	function getSelectedValue(){
		var ids = document.getElementsByName("ids");
		
		for(var i = 0 ; i < ids.length ; i++)
		{
						
			if(ids[i].checked){
				return ids[i].value;
			}				
		}
		
	}
    
    function logout() {
    	window.location.href="<%=path %>/servlet/LogoutAction?action_flag=logout";
    }

</script>
</body>
</html>