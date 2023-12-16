<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 刘xiong
  Date: 8/12/2023
  Time: 下午5:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>简易商家后台管理系统</title>
    <style>
        body {
            margin: 0;
            padding: 20px;
            font-family: Arial, sans-serif;
        }

        .content-container {
            margin: 20px auto; /* 水平居中 */
            max-width: 1000px; /* 控制最大宽度 */
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            border-bottom: 1px solid #ffffff;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #ffffff49;
            border-radius: 5px;
            box-shadow: 0 0 30px rgba(255, 255, 255, 0.5) inset;
        }

        .form {
            margin: 20px;
            padding: 20px;
            display: flex;
        }

        input[type="text"] {
            width: 80%;
            height: 40px;
            margin: 10px 0;
            box-sizing: border-box;
            color: rgb(0, 0, 0);
            border: 5px solid transparent;
            background: rgba(255, 255, 255, .5);
            border-radius: 5px;
            padding: 5px 20px 0 20px;
            transition: 0.3s;
            font-size: 18px;
            outline: none
        }

        input::placeholder {
            color: #92A7E8;
        }

        input[type="submit"] {
            margin-top: 10px;
            width: 20%;
            height: 40px;
            background-color: #ffffff49;
            border-radius: 25px;
            box-shadow: 0 0 30px rgba(255, 255, 255, 0.5) inset;
            color: #007bff;
            font-size: 18px;
            border: none;
            cursor: pointer;
        }

        .subtitle {
            text-align: center;
        }

        a {
            text-decoration: none;
        }

        a:link {
            color: #007bff;
        }

        a:hover {
            color: #0056b3;
        }

        label {
            margin-left: 20px;
            font-size: 20px;
            font-weight: bold;
        }

    </style>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script>
        // 恢复值的函数
        function restoreValue(inputElement, originalValue) {
            if (inputElement.value.trim() === '') {
                // 如果内容为空，则恢复为原始值
                inputElement.value = originalValue;
            }
        }

        function handleFileSelect() {
            var fileInput = document.getElementById('headSculpture');
            var previewImg = document.getElementById('preview');

            var file = fileInput.files[0];
            if (file) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    previewImg.src = e.target.result;
                };
                reader.readAsDataURL(file);
            } else {
                previewImg.src = '#';
            }
        }
        $(function () {
            $('#submit-btn').click(function () {
                let username = $('#username').val();
                let email = $('#email').val();
                let phone = $('#phone').val();
                let avatar = $('#headSculpture')[0].files[0];  // 获取文件对象

                let formData = new FormData();
                formData.append("username", username);
                formData.append("email", email);
                formData.append("phone", phone);
                formData.append("avatar", avatar);

                let url = "/user?method=editUserInfo";

                $.ajax({
                    url: url,
                    type: 'POST',
                    data: formData,
                    processData: false,  // 告诉 jQuery 不要处理数据
                    contentType: false,  // 告诉 jQuery 不要设置 contentType
                    success: function (result) {
                        let json = JSON.parse(result);
                        alert(json.mess);
                        if (json.ok == true) {
                            window.location.href = "/userInfo.jsp";
                        }
                    }
                });
            })
        });
    </script>
</head>
<body style="background-image: url('./img/img.png')">
<div style="display: flex;text-align: center;margin-left: 50px;margin-top: 10px">
    <a href="userInfo.jsp" style="color: white;font-size: 20px">返回</a>
</div>
<h2 class="subtitle">个人信息修改</h2>
<div class="content-container">
    <div class="form" style="flex-direction: column" enctype="multipart/form-data">
        <label for="headSculpture">上传头像:</label>
        <div style="display: flex;align-items: center">
            <input type="file" id="headSculpture" name="headSculpture" accept="image/*" onchange="handleFileSelect()">
            <img id="preview" src="#" alt="预览" style="
        width: 100px; /* 调整头像的大小 */
        height: 100px; /* 调整头像的大小 */
        border-radius: 50%;
        overflow: hidden;
        background-color: skyblue;">
        </div>
        <label for="username">用户名:</label>
        <input onblur="restoreValue(this, '${user.username}')" type="text" id="username" name="username"
               value="${user.username}">
        <label for="email">邮箱:</label>
        <input onblur="restoreValue(this, '${user.email}')" type="text" id="email" name="email"
               value="${user.email}">
        <label for="phone">手机号:</label>
        <input onblur="restoreValue(this, '${user.phone}')" type="text" id="phone" name="phone"
               value="${user.phone}">
        <input id="submit-btn" style="margin-top: 20px;" type="submit" value="提交">
    </div>
</div>
</body>
</html>

