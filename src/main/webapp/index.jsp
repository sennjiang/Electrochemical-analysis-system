<html>
<body>
<h2>Hello World!</h2>
<form action="u" method="post" >
    <input type="text" name="username"/>
    <input type="password" name="password"/>
    <button type="submit">login</button>
</form>

<h3>form表单文件上传</h3>
<form action="upload" method="post" enctype="multipart/form-data">
    <input type="text" name="boundary" placeholder="0205"/>
    <input type="file" name="upload"><br>
    <input type="submit" value="提交"/>
</form><br>
</body>
</html>
