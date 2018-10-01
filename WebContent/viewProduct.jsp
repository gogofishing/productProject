<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String,Object> map = (Map<String,Object>) request.getAttribute("productMap");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ViewProduct</title>
</head>
<body>
<div align="center">
    <table width="60%" style="margin: auto;">
        <tr>
            <td height="100"></td>
        </tr>
        <tr>
            <td>Product information</td>
        </tr>
        <tr>
            <td>
                <table width="99%" border="1">
                    <tr align="center">
                        <td width="20%">Product name</td>
                        <td width="30%"><%=map.get("proname")%></td>
                        <td width="20%">Product price</td>
                        <td width="30%"><%=map.get("proprice")%></td>
                    </tr>
                    <tr align="center">
                        <td>Produce address</td>
                        <td colspan="3"><%=map.get("proaddress")%></td>
                    </tr>
                    <tr align="center">
                        <td>Product image</td>
     <%--                    <td colspan="3"><img src="<%=path%>/upload/<%=map.get("proimage")%>"></td> --%>
                        <td colspan="3"><img src="E:/Java_workspace/productProject/WebContent/upload/<%=map.get("proimage")%>"></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td align="center">
                <button type="button" onclick="javascript:location.href='<%=path %>/main.jsp'">Main page</button>
                <button type="button" onclick="javascript:history.go(-1)">Return</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>