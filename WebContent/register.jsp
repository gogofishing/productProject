<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

</head>
<body>
<div style="text-align: center">
    <form name="form1"  method="post">
        <table style="margin:auto">
            <tr><th colspan="3">User register:</th></tr>
            <tr>
                <td>User name:</td>
                <td><input type="text" name="username" required oninvalid="setCustomValidity('Please input username!')" oninput="setCustomValidity('')"></td>
                <td><p id="name_err"></p></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="pswd" id="pass" required oninvalid="setCustomValidity('Please input password!')" oninput="setCustomValidity('')" onblur="check_repass();"></td>
                <td><p id="pass_err"></p></td>
            </tr>
            <tr>
                <td>Repeat Password:</td>
                <td><input type="password" name="repswd" id="passre"  required oninvalid="setCustomValidity('Please repeat password!')" oninput="setCustomValidity('');" onblur="check_repass();"></td>
                <td><p id="repass_err"></p></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <button type="submit"  onclick="oncheck()">Submit</button>
                    <button type="button" onclick="javasrcipt:location.href = 'index.jsp'">Back</button>
                </td>
            </tr>
        </table>
    </form>
</div>
<script>
    var check_ok = false;
    var th = document.form1;

    // function check_name() {
    //     if (th.username.value == "") {
    //         document.getElementById("name_err").innerHTML = "Enter username!";
    //         th.username.focus();
    //         return;
    //     }
    //     else {
    //         document.getElementById("name_err").innerHTML = "";
    //     }
    // }
    // function check_pass() {
    //     if (th.pswd.value == "") {
    //         document.getElementById("pass_err").innerHTML = "Enter password!";
    //         th.username.focus();
    //         return;
    //     }
    //     else {
    //         document.getElementById("pass_err").innerHTML = "";
    //     }
    // }
    function check_repass() {
        var pass = document.getElementById("pass").value;
        var repass = document.getElementById("passre").value;
        if (pass === repass && pass !== "") {
            document.getElementById("repass_err").innerHTML = "Password correct!";
            check_ok = true;
        }
        else {
            document.getElementById("repass_err").innerHTML = "Password different!";
        }
    }

    function oncheck() {
        if (check_ok) {
            th.action = "<%=path%>/RegisterAction";
            return true;
        }
        else {
            return false;
        }
    }

</script>
</body>
</html>