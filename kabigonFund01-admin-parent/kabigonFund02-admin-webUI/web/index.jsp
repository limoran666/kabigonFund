<%--
  Created by IntelliJ IDEA.
  User: limoran
  Date: 2022/3/12
  Time: 7:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <script type="text/javascript" src="js/jquery/jquery-2.1.1.min.js"></script>
    <script>
      $(function() {
        $("#btn1").click(function () {
          var array=[1,2,3];
          var strReqBody=JSON.stringify(array);


          $.ajax({
            "url":"send/array.html", //请求目标资源地址
            "type":"post",//请求方式
            "data":strReqBody,//请求参数
            "contentType":"application/json;charset=UTF-8",//告诉服务器端本次传的是json格式的数据
            "dataType":"text",//对待服务器返回的参数 把它看做text
            "success":function (response) {//服务器端成功处理请求后的回调函数
              alert(response)
            },
            "error":function (response){//服务器端失败处理请求后的回调函数
              alert(response)
            }


          })
        });
        
      });
 </script>
  </head>

  <a href="${pageContext.request.contextPath}/test/ssm.html">测试ssm整体环境</a><br/>

  <button id="btn1"> Test Request Body</button>
  </body>
</html>
