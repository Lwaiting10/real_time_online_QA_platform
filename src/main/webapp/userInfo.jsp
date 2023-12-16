<%@ page import="com.iweb.util.ImageUtil" %>
<%@ page import="com.iweb.bean.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>个人信息</title>
    <style>
        body {
            background-image: url('./img/img.png');
        }

        .subtitle {
            text-align: center;
        }

        .content-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 50px; /* Adjust the top margin as needed */
        }

        #user-avatar1 {
            width: 100px; /* 调整头像的大小 */
            height: 100px; /* 调整头像的大小 */
            border-radius: 50%;
            overflow: hidden;
            background-color: skyblue;
        }

        #user-avatar1 span {
            margin-left: 40px;
            margin-top: 40px;
            width: 100%;
            height: 100%;
            object-fit: cover;
            font-size: 20px; /* 调整头像中文字的大小 */
            color: white;
        }


        .info-box {
            background-color: rgba(255, 255, 255, 0.5);
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            width: 40%; /* Adjust the width as needed */
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .info-box div {
            display: flex;
            justify-content: space-between;
            width: 40%; /* 调整宽度 */
            margin: 10px 0; /* 调整页边距 */
        }

        .info-box span {
            font-size: 18px;
        }

        .error-message-box {
            background-color: #ffcccc;
            padding: 10px;
            border: 1px solid #ff0000;
            margin-bottom: 20px;
            width: 80%; /* 调整宽度 */
        }

        .error-message {
            color: #ff0000;
        }

        .edit-button {
            background-color: #001f3f;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
        }

        .edit-button:hover {
            background-color: #003366;
        }
    </style>
</head>

<body>
<div style="display: flex;text-align: center;margin-left: 50px;margin-top: 30px">
    <a href="home.html" style="color: white;font-size: 20px;text-decoration: none">返回</a>
</div>
<h2 class="subtitle" style="margin-top: 100px">个人信息</h2>
<div class="content-container">
    <div class="info-box">
        <div id="user-avatar1">
            <%
                User user = (User) session.getAttribute("user");
            %>
            <c:if test="${empty user.avatar}">
                <span>无</span>
            </c:if>
            <c:if test="${!(empty user.avatar)}">
                <img src="data:image/png;base64,<%= ImageUtil.encodeImage(user.getAvatar()) %>"
                     alt="User Avatar">
            </c:if>
        </div>
        <div>
            <span>用户名:</span><span>${user.username}</span>
        </div>
        <div>
            <span>邮箱:</span><span>${user.email == null ? "无" : user.email}</span>
        </div>
        <div>
            <span>手机号:</span><span>${user.phone == null ? "无" : user.phone}</span>
        </div>
    </div>
    <!-- 链接以查看问题历史 -->
    <a class="edit-button" href="questionHistory.html" style="margin-top: 10px">查看提问历史</a>
    <!-- 链接以查看回答历史 -->
    <a class="edit-button" href="answerHistory.html" style="margin-top: 10px">查看回答历史</a>
    <a class="edit-button" href="editUserInfo.jsp" style="margin-top: 10px">修改信息</a>
    <a class="edit-button" href="editPassword.html" style="margin-top: 10px">修改密码</a>
</div>
</body>
</html>
