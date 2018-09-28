<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

 <script>
        function login() {
            var th = document.form1;
            if (th.username.value == "") {
                alert("Username cannot be empty!");
                th.username.focus();
                return;
            }
            if (th.pswd.value == "") {
                alert("Password cannot be empyt!");
                th.pswd.focus();
                return;
            }
           <%--  th.action = "<%=path%>/servlet/LoginAction"; --%>
            th.action = "<%=path%>/LoginAction";
            th.submit();

        }
    </script>
</head>
<body>
<div style="text-align: center">
    <form name="form1" action="" method="post">
        <table style="margin:auto">
            <tr><th colspan="2">Welcome to product system!</th></tr>
            <tr>
                <td>User name:</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="pswd"></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <button type="button" onclick="login()">Login</button>
                    <button type="button" onclick="javasrcipt:location.href = 'register.jsp'">Sign Up</button>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>