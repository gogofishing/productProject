<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add new product</title>
</head>
<body>
<div align="center">
    <table width="70%" style="margin: auto">
        <tr><td align="center" height="150" valign="bottom">Add new product</td></tr>
        <tr>
            <td>
                <form id="form1" method="post" enctype="multipart/form-data">
                    <table border="1" style="margin: auto;">
                        <tr>
                            <td>Product name</td>
                            <td><input type="text" name="proname" id="proname"></td>
                            <td>Product price</td>
                            <td><input type="text" name="proprice" id="proprice"></td>
                        </tr>
                        <tr>
                            <td>Product address</td>
                            <td colspan="3"><input type="text" name="proaddress" id="proaddress"></td>
                        </tr>
                        <tr>
                            <td>Product image</td>
                            <td colspan="3"><input type="file" name="proimage" id="proimage" size="35"></td>
                        </tr>
                    </table>
                </form>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="center">
                <button type="button" onclick="dosubmit();">Submit</button>
                <button type="button" onclick="javascript:location.href='main.jsp'">Return</button>
            </td>
        </tr>
    </table>
</div>
<script>
    function dosubmit() {
        var th = document.getElementById("form1");
        th.action = "<%=path%>/ProductAction?action_flag=add";
        th.submit();
    }
</script>
</body>
</html>